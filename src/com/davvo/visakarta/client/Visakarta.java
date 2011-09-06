package com.davvo.visakarta.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.maps.client.Maps;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Visakarta implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        /*
         * Asynchronously loads the Maps API.
         *
         * The first parameter should be a valid Maps API Key to deploy this
         * application on a public server, but a blank key will work for an
         * application served from localhost.
        */
        //visakartax.appspot.com ABQIAAAAtE-iz7xe0FQqTquUcmRbARTor9EtsGlcEecBjx5knaQG5AyvJhS8wd2TSZ_pUOyIFWgWYvbt5nPX-A
        //120.0.0.1 ABQIAAAAtE-iz7xe0FQqTquUcmRbARTb-vLQlFZmc2N8bgWI8YDPp5FEVBTkEv3ldnxgoeD4HyxOm6y383Ut4g
        Maps.loadMapsApi("ABQIAAAAtE-iz7xe0FQqTquUcmRbARTor9EtsGlcEecBjx5knaQG5AyvJhS8wd2TSZ_pUOyIFWgWYvbt5nPX-A", "2", false, new Runnable() {
           public void run() {
               AppController appViewer = new AppController();
               appViewer.go(RootLayoutPanel.get());
           }
         });
        
    }
            
}
