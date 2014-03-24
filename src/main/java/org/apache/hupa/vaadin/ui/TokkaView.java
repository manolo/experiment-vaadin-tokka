package org.apache.hupa.vaadin.ui;

import org.apache.hupa.vaadin.model.Tokka;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class TokkaView extends NavigationView {
    
    
    public TokkaView() {
        injectStyles();
        setCaption("Tokka");
        Button edit = new Button("Edit", new ClickListener() {
            public void buttonClick(ClickEvent event) {
                getNavigationManager().navigateTo(new TokkaEditView());                
            }
        });
        setRightComponent(edit);

        refreshContent();
    }
    
    static boolean injected = false;
    
    private void injectStyles() {
        if (!injected) {
            Page.getCurrent().getStyles().add(".tokka-row {margin-bottom: 10px} .tokka-done{background:grey; color: white; text-align: center; border-radius: 50px}");
            injected = true;
        }
    }
    
    @Override
    protected void onBecomingVisible() {
        super.onBecomingVisible();
        refreshContent();
    }
    
    private void refreshContent() {
        VerticalComponentGroup content = new VerticalComponentGroup();
        for (final Tokka t: Tokka.getTokkaDb()) {
            HorizontalLayout h = createRow(t.getName(), t.getDone(), t.getTotal());
            h.addLayoutClickListener(new LayoutClickListener() {
                public void layoutClick(LayoutClickEvent event) {
                    getNavigationManager().navigateTo(new TokkaTodoView(t));                
                }
            });
            content.addComponent(h);
        }
        setContent(content);
    }

    private HorizontalLayout createRow(String name, int done, int total) {
        Label lName = new Label();
        lName.setValue(name);
        lName.setSizeFull();
        lName.addStyleName("tokka-row");
        
        Label lDone = new Label();
        lDone.setWidth("3em");
        lDone.setHeight("100%");
        lDone.setValue(done + "/" + total);
        lDone.addStyleName("tokka-row tokka-done");

        Label lEmpty = new Label();
        lEmpty.setWidth("1.5em");
        lEmpty.setHeight("100%");
        
        HorizontalLayout h = new HorizontalLayout();
        h.setSizeFull();
        h.addComponent(lName);
        h.addComponent(lDone);
        h.addComponent(lEmpty);
        h.setComponentAlignment(lName, Alignment.MIDDLE_LEFT);
        h.setComponentAlignment(lDone, Alignment.MIDDLE_CENTER);
        h.setExpandRatio(lName, 1.0f);
        h.addStyleName("v-touchkit-navbutton");

        return h;
    };
}
