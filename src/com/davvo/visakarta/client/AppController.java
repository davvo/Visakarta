package com.davvo.visakarta.client;

import java.awt.FlowLayout;

import com.davvo.visakarta.client.presenter.MapPresenter;
import com.davvo.visakarta.client.presenter.MapPropertiesPresenter;
import com.davvo.visakarta.client.presenter.MarkerDetailsPresenter;
import com.davvo.visakarta.client.presenter.MarkersPresenter;
import com.davvo.visakarta.client.presenter.Presenter;
import com.davvo.visakarta.client.presenter.ToolBarPresenter;
import com.davvo.visakarta.client.view.MapPropertiesView;
import com.davvo.visakarta.client.view.MapView;
import com.davvo.visakarta.client.view.MarkerDetailsView;
import com.davvo.visakarta.client.view.MarkersView;
import com.davvo.visakarta.client.view.ToolBarView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;

public class AppController implements Presenter {

    private final EventBus eventBus = new SimpleEventBus();
    private final MapServiceAsync rpcService = GWT.create(MapService.class);
    private HasWidgets container;

    public AppController() {
        bind();
    }

    @Override
    public void go(HasWidgets container) {
        this.container = container;
        
        final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
        
        final Panel toolBarPanel = new FlowPanel();
        dock.addSouth(toolBarPanel, 50);
        
        final Panel mapPanel = new FlowPanel();
        dock.add(mapPanel);
                
        container.add(dock);
        
        Presenter toolBarPresenter = new ToolBarPresenter(eventBus, new ToolBarView());
        toolBarPresenter.go(toolBarPanel);
        
        Presenter mapPresenter = new MapPresenter(eventBus, new MapView());
        mapPresenter.go(mapPanel);
        
        MapPropertiesPresenter mapProperties = new MapPropertiesPresenter(eventBus, new MapPropertiesView());
        
        MarkersPresenter markersPresenter = new MarkersPresenter(eventBus, new MarkersView());
        
        MarkerDetailsPresenter markerDetails = new MarkerDetailsPresenter(eventBus, new MarkerDetailsView());
        
    }
    
    private void bind() {
        
    }

}
