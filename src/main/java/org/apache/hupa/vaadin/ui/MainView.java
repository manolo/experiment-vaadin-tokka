package org.apache.hupa.vaadin.ui;


import com.vaadin.addon.touchkit.ui.NavigationManager;

@SuppressWarnings("serial")
public class MainView extends NavigationManager {

    public MainView() {
//        setCurrentComponent(new TokkaView());
        setCurrentComponent(new TokkaMain());
    }
}
