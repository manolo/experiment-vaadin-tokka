package org.apache.hupa.vaadin.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.client.ApplicationConfiguration;
import com.vaadin.client.ValueMap;
import com.vaadin.client.ui.button.ButtonConnector;

public class TokkaEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        new Timer() {
            public void run() {
                try {
//                    start();
//                    button();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.schedule(3000);;

    }
    
    private void start() {
        Element e = ValueMap.createObject().cast();
        e.setPropertyInt("org.apache.hupa.vaadin.mobile.MobileUI", 0);
        e.setPropertyInt("com.vaadin.server.AbstractClientConnector", 6);
        e.setPropertyInt("com.vaadin.addon.touchkit.ui.NavigationView", 1);
        e.setPropertyInt("com.vaadin.ui.AbstractComponent", 7);
        e.setPropertyInt("com.vaadin.ui.AbstractOrderedLayout", 8);
        e.setPropertyInt("com.vaadin.ui.AbstractLayout", 9);
        e.setPropertyInt("org.apache.hupa.vaadin.ui.MainView", 5);
        e.setPropertyInt("com.vaadin.ui.AbstractSingleComponentContainer", 10);
        e.setPropertyInt("com.vaadin.addon.touchkit.ui.NavigationBar", 4);
        e.setPropertyInt("com.vaadin.ui.AbstractComponentContainer", 11);
        e.setPropertyInt("com.vaadin.addon.touchkit.ui.NavigationManager", 12);
        e.setPropertyInt("com.vaadin.ui.UI", 13);
        e.setPropertyInt("com.vaadin.ui.Button", 3);
        e.setPropertyInt("com.vaadin.ui.VerticalLayout", 2);
//        k com.vaadin.server.AbstractClientConnector v 2
//        k com.vaadin.ui.AbstractSingleComponentContainer v 3
//        k com.vaadin.ui.AbstractComponentContainer v 4
//        k org.apache.hupa.vaadin.ui.MainView v 1
//        k org.apache.hupa.vaadin.mobile.MobileUI v 0
//        k com.vaadin.ui.AbstractComponent v 5
//        k com.vaadin.ui.UI v 6
//        k com.vaadin.addon.touchkit.ui.NavigationManager v 7        
        
//        ApplicationConfiguration.applicationConfiguration.addComponentMappings(
//                e.<ValueMap>cast(),
//                ApplicationConfiguration.applicationConnection.widgetSet);
        

    }
    
    private void button() {
//        NavigationManager nm = (NavigationManager) ApplicationConfiguration.applicationConnection.getConnector("0", 12);
//        ButtonConnector bc = (ButtonConnector) ApplicationConfiguration.applicationConnection.getConnector("0", 3);
//        System.out.println(bc);
    }

}
