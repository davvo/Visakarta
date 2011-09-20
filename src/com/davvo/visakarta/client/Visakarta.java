package com.davvo.visakarta.client;

import com.davvo.visakarta.client.presenter.MapPresenter;

import com.davvo.visakarta.client.presenter.MapPropertiesPresenter;
import com.davvo.visakarta.client.presenter.MarkerDetailsPresenter;
import com.davvo.visakarta.client.presenter.MarkersPresenter;
import com.davvo.visakarta.client.presenter.SaveMapPresenter;
import com.davvo.visakarta.client.presenter.ToolBarPresenter;
import com.davvo.visakarta.client.view.MapPropertiesView;
import com.davvo.visakarta.client.view.MapView;
import com.davvo.visakarta.client.view.MarkerDetailsView;
import com.davvo.visakarta.client.view.MarkersView;
import com.davvo.visakarta.client.view.SaveMapView;
import com.davvo.visakarta.client.view.ToolBarView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Visakarta implements EntryPoint {

    private final EventBus eventBus = new SimpleEventBus();
    private final MapServiceAsync rpcService = GWT.create(MapService.class);
    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        new MapPresenter(eventBus, new MapView(RootLayoutPanel.get())).go();
        new MapPropertiesPresenter(eventBus, new MapPropertiesView()).go();
        new MarkersPresenter(eventBus, new MarkersView()).go();
        new MarkerDetailsPresenter(eventBus, new MarkerDetailsView()).go();        
        new SaveMapPresenter(eventBus, rpcService, new SaveMapView()).go();
        new ToolBarPresenter(eventBus, new ToolBarView()).go();

    }
            
}
