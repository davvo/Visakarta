package com.davvo.visakarta.client.presenter;

import com.davvo.visakarta.client.MapServiceAsync;
import com.davvo.visakarta.client.event.SaveMapEvent;
import com.davvo.visakarta.client.event.SaveMapHandler;
import com.davvo.visakarta.shared.Map;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;

public class SaveMapPresenter {

    private EventBus eventBus;
    private MapServiceAsync rpcService;
    private final Display view;
    
    private Timer checkURLTimer;
    private Timer checkEmailTimer;
    
    private RegExp emailPattern = RegExp.compile("^.+@.+\\.[a-zA-Z]{2,4}$");
    
    public interface Display {
        HasValue<String> getMapTitle();
        HasValue<String> getEmail();
        HasValue<String> getMapURL();
        HasClickHandlers getSaveButton();
        HasClickHandlers getCancelButton();
        void setEmailOK(boolean ok);
        void setMapURLOK(boolean ok);
        void showMe();
        void hideMe();
    }

    public SaveMapPresenter(EventBus eventBus, MapServiceAsync rpcService, Display view) {
        this.eventBus = eventBus;
        this.rpcService = rpcService;
        this.view = view;
        
        checkURLTimer = new Timer() {
            
            @Override
            public void run() {
                SaveMapPresenter.this.checkMapURL();
            }
        };
        
        checkEmailTimer = new Timer() {

            @Override
            public void run() {
                SaveMapPresenter.this.checkEmail();
            }            
        };
        
        bind();
    }
    
    private void checkMapURL() {
        final String url = view.getMapURL().getValue().trim();
        
        if (url.length() == 0) {
            
            view.setMapURLOK(false);
            
        } else {
        
            rpcService.exists(url, new AsyncCallback<Boolean>() {
                
                @Override
                public void onSuccess(Boolean result) {
                    view.setMapURLOK(!result);
                }
                
                @Override
                public void onFailure(Throwable caught) {
                }
                
            });
        }
    }
    
    private void checkEmail() {
        String email = view.getEmail().getValue().trim();
        view.setEmailOK(email.length() == 0 || emailPattern.test(email));
    }
    
    private void getMap() {
        rpcService.load(Map.getInstance().getId(), new AsyncCallback<Map>() {
            
            @Override
            public void onSuccess(Map result) {
                System.out.println(result.getMarkers().size());                
            }
            
            @Override
            public void onFailure(Throwable caught) {
                System.err.println("blŠŠ");
                
            }
        });
    }
    
    private void bind() {
        eventBus.addHandler(SaveMapEvent.TYPE, new SaveMapHandler() {
            
            @Override
            public void onSaveMap(SaveMapEvent event) {
                view.showMe();
            }
        });
        
        view.getSaveButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                populateMap();
                rpcService.save(Map.getInstance(), new AsyncCallback<String>() {
                    
                    @Override
                    public void onSuccess(String result) {
                        Window.alert("Successfully saved map :-)");
                        
                        SaveMapPresenter.this.getMap();
                        
                    }
                    
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Unable to save map :-(");
                    }
                });
            }
        });
        
        view.getCancelButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                view.hideMe();
            }
        });
        
        view.getMapURL().addValueChangeHandler(new ValueChangeHandler<String>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                checkURLTimer.cancel();
                checkURLTimer.schedule(300);
            }
        });
        
        view.getEmail().addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                checkEmailTimer.cancel();
                checkEmailTimer.schedule(300);
            }            
        });
    }
    
    private void populateMap() {
        Map.getInstance().setTitle(view.getMapTitle().getValue());
        Map.getInstance().setId(view.getMapURL().getValue());
    }
}
