package com.davvo.visakarta.client.presenter;

import com.davvo.visakarta.client.MapServiceAsync;

import com.davvo.visakarta.client.event.SaveMapEvent;
import com.davvo.visakarta.client.event.SaveMapHandler;
import com.davvo.visakarta.client.view.MapSavedDialog;
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

public class SaveMapPresenter implements Presenter {

    private EventBus eventBus;
    private MapServiceAsync rpcService;
    private final Display view;
    
    private Timer checkURLTimer;
    private Timer checkEmailTimer;
    
    private RegExp emailPattern = RegExp.compile("^.+@.+\\.[a-zA-Z]{2,4}$");
    private RegExp urlPattern = RegExp.compile("^[\\w\\s]+$");
    
    public interface Display {
        HasValue<String> getMapTitle();
        HasValue<String> getEmail();
        HasValue<String> getMapURL();
        HasClickHandlers getSaveButton();
        HasClickHandlers getCancelButton();
        void setEmailOK(boolean ok);
        void setMapURLOK(boolean ok);
        void show();
        void hide();
        void center();
    }

    public SaveMapPresenter(EventBus eventBus, MapServiceAsync rpcService, Display view) {
        this.eventBus = eventBus;
        this.rpcService = rpcService;
        this.view = view;
    }
    
    @Override
    public void go() {
        bind();
        checkMapURL();
    }
        
    private void checkMapURL() {
        final String url = view.getMapURL().getValue().trim();
        
        if (url.length() == 0 || !urlPattern.test(url)) {
            
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
    
    private void bind() {
        eventBus.addHandler(SaveMapEvent.TYPE, new SaveMapHandler() {
            
            @Override
            public void onSaveMap(SaveMapEvent event) {
                if (event.getSource() != SaveMapPresenter.this) {
                    if (event.isVisible()) {
                        view.center();
                    } else {
                        view.hide();
                    }
                }
            }
        });
        
        view.getSaveButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                populateMap();
                rpcService.save(Map.getInstance(), new AsyncCallback<String>() {
                    
                    @Override
                    public void onSuccess(String result) {
                        (new MapSavedDialog()).center();
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
                view.hide();
                eventBus.fireEventFromSource(new SaveMapEvent(false), SaveMapPresenter.this);
            }
        });
        
        view.getMapURL().addValueChangeHandler(new ValueChangeHandler<String>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (checkURLTimer == null) {
                    checkURLTimer = new Timer() {
                        
                        @Override
                        public void run() {
                            SaveMapPresenter.this.checkMapURL();
                        }
                    };
                }
                checkURLTimer.cancel();
                checkURLTimer.schedule(300);
            }
        });
        
        view.getEmail().addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (checkEmailTimer == null) {
                    checkEmailTimer = new Timer() {

                        @Override
                        public void run() {
                            SaveMapPresenter.this.checkEmail();
                        }            
                    };
                }
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
