package com.davvo.visakarta.client;

import com.davvo.visakarta.client.MapService;
import com.davvo.visakarta.client.MapServiceAsync;
import com.davvo.visakarta.client.presenter.SaveMapPresenter;
import com.davvo.visakarta.client.view.SaveMapView;
import com.davvo.visakarta.shared.Map;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MapServiceTest extends GWTTestCase {

    EventBus eventBus;
    
    MapServiceAsync rpcService;
    SaveMapPresenter saveMapPresenter;
    SaveMapPresenter.Display display;
    
    @Override
    public String getModuleName() {
        return "com.davvo.visakarta.Visakarta";
    }
    
    public void gwtSetUp() {
        eventBus = new SimpleEventBus();
        rpcService = GWT.create(MapService.class);
        display = new SaveMapView();
        saveMapPresenter = new SaveMapPresenter(eventBus, rpcService, display);
    }
    
    public void testSaveMap() {
        Map map = Map.getInstance();
        
        rpcService.save(map, new AsyncCallback<String>() {
            
            @Override
            public void onSuccess(String result) {
                finishTest();
            }
            
            @Override
            public void onFailure(Throwable caught) {
                System.out.println("Failed: " + caught.getMessage());
            }
        });
        
        delayTestFinish(5000);
        
    }

    
}
