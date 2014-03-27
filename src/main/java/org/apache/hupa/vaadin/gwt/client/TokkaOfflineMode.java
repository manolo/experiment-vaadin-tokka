package org.apache.hupa.vaadin.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.vaadin.addon.touchkit.gwt.client.offlinemode.DefaultOfflineMode;

public class TokkaOfflineMode extends DefaultOfflineMode {
    protected void buildDefaultContent() {
        Window.alert("offline");
    }

}
