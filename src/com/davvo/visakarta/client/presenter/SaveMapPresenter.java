package com.davvo.visakarta.client.presenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.davvo.visakarta.client.MapServiceAsync;
import com.davvo.visakarta.client.event.SaveMapEvent;
import com.davvo.visakarta.client.event.SaveMapHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;

public class SaveMapPresenter {

    private EventBus eventBus;
    private MapServiceAsync rpcService;
    private final Display view;
    
    private Timer checkURLTimer;
    private Timer checkEmailTimer;
    
    private Pattern emailPattern = Pattern.compile("^.+@.+\\.[a-zA-Z]{2,4}$");
    
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
        Matcher m = emailPattern.matcher(email);
        view.setEmailOK(email.length() == 0 || m.matches());
    }
    
    private void bind() {
        eventBus.addHandler(SaveMapEvent.TYPE, new SaveMapHandler() {
            
            @Override
            public void onSaveMap(SaveMapEvent event) {
                view.showMe();
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
    
    
}
