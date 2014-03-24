package org.apache.hupa.vaadin.mobile;


import org.apache.hupa.vaadin.ui.MainView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * The UI's "main" class
 */
@SuppressWarnings("serial")
@Widgetset("org.apache.hupa.vaadin.gwt.AppWidgetSet")
@Theme("touchkit")
public class MobileUI extends UI {
    @Override
    protected void init(VaadinRequest request) {
        setContent(new MainView());
    }
}
