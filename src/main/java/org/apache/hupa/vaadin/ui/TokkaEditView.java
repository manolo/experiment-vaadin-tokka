package org.apache.hupa.vaadin.ui;


import org.apache.hupa.vaadin.model.Tokka;

import com.vaadin.addon.touchkit.extensions.TouchKitIcon;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class TokkaEditView extends NavigationView implements Component {

    final TextField addBox;
     Popover confirm;
     Tokka current;

    public TokkaEditView() {
        injectStyles();
        createConfirm();
        
        addBox = new TextField();
        addBox.setInputPrompt("New Item ...");
        TouchKitIcon.plus.addTo(addBox);
        addBox.setImmediate(true);
        addBox.addShortcutListener(
            new ShortcutListener("New", ShortcutAction.KeyCode.ENTER, null) {
                public void handleAction(Object sender, Object target) {
                    doNew();
                }
        });
        addBox.addBlurListener(new BlurListener() {
            public void blur(BlurEvent event) {
                doNew();
            }
        });
        
        setCaption("Tokka");
        refreshContent();
    }
    
    static boolean injected = false;
    private void injectStyles() {
        if (!injected) {
            Page.getCurrent().getStyles().add(".tokka-delete::after{content: ''}div.tokka-delete{color:black !important; text-align: left !important;font-size: 16px !important}.v-button-caption{padding-left: 10px !important}.tokka-confirm{font-size:20px}");
            injected = true;
        }
    }

    private void doNew() {
        String v = addBox.getValue().trim();
        if (!v.isEmpty()) {
            new Tokka(v);
            addBox.setValue("");
            refreshContent();
        }
    }
    
    private void refreshContent() {
        VerticalComponentGroup content = new VerticalComponentGroup();
        content.addComponent(addBox);
        for (final Tokka t: Tokka.getTokkaDb()) {
            final Button b = new Button(t.getName());
            b.setStyleName("v-touchkit-navbutton tokka-delete");
            b.setSizeFull();
            TouchKitIcon.remove.addTo(b);
            content.addComponent(b);
            b.addClickListener(new ClickListener() {
                public void buttonClick(ClickEvent event) {
                    current = t;
//                    b.getUI().addWindow(confirm);
                    confirm.showRelativeTo(b);
                }
            });
        }
        setContent(content);        
    }
    
    private void createConfirm() {
        VerticalLayout v = new VerticalLayout();
        v.setSizeFull();
        v.setSpacing(true);
        v.setMargin(true);
        
        Label l = new Label("Do you want to delete this item?");
        v.addComponent(l);
        l.setStyleName("tokka-comfirm");
        v.setComponentAlignment(l, Alignment.MIDDLE_CENTER);
        
        v.setExpandRatio(l, 1f);
        
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        h.setHeight("50px");
        h.setSpacing(true);
        v.addComponent(h);
        
        Button cancel = new Button("Cancel");
        Button ok = new Button("OK");
        
        h.addComponent(cancel);
        h.addComponent(ok);
        
        confirm = new Popover(v);
        confirm.setContent(v);
        confirm.setHeight("200px");
        confirm.setWidth("200px");
        confirm.getState().setFullscreen(true);
        
        cancel.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
//                getUI().removeWindow(confirm);
                confirm.removeFromParent();
            }
        });
        
        ok.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
//                getUI().removeWindow(confirm);
                confirm.removeFromParent();
                Tokka.getTokkaDb().remove(current);
                refreshContent();                
            }
        });
    }
}
