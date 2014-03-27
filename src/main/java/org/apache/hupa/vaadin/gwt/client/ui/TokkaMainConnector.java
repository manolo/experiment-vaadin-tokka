package org.apache.hupa.vaadin.gwt.client.ui;

import java.util.Arrays;
import java.util.Collections;

import org.apache.hupa.vaadin.gwt.client.model.Tokka;
import org.apache.hupa.vaadin.ui.TokkaMain;

import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.addon.touchkit.gwt.client.vcom.VerticalComponentGroupConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationBarConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationViewConnector;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.communication.StateChangeEvent.StateChangeHandler;
import com.vaadin.client.ui.AbstractComponentContainerConnector;
import com.vaadin.client.ui.AbstractHasComponentsConnector;
import com.vaadin.client.ui.button.ButtonConnector;
import com.vaadin.client.ui.label.LabelConnector;
import com.vaadin.client.ui.orderedlayout.HorizontalLayoutConnector;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.label.LabelState;
import com.vaadin.shared.ui.orderedlayout.AbstractOrderedLayoutState;
import com.vaadin.shared.ui.orderedlayout.AbstractOrderedLayoutState.ChildComponentData;
import com.vaadin.shared.ui.orderedlayout.HorizontalLayoutState;
import com.vaadin.ui.Alignment;

@Connect(TokkaMain.class)
public class TokkaMainConnector extends AbstractComponentContainerConnector {

    TokkaMainServerRpc rpc = RpcProxy.create(TokkaMainServerRpc.class, this);
    
    ApplicationConnection appConnection;
    NavigationViewConnector navigationViewConnector;
    NavigationBarConnector navigationBarConnector;
    int id = 0;

    public TokkaMainConnector() {
        new RuntimeException();
        registerRpc(TokkaMainClientRpc.class, new TokkaMainClientRpc() {
            public void alert(String message) {
                Window.alert(message);
            }
        });
        appConnection = new ApplicationConnection();
    }
    
    @Override
    protected Widget createWidget() {
        injectStyles();
        navigationViewConnector = init(new NavigationViewConnector());
        navigationBarConnector = init(new NavigationBarConnector());
        
        navigationViewConnector.getState().caption = "Tokka";
        
        
        ButtonConnector buttonConnector = init(new ButtonConnector());
        buttonConnector.getState().caption = "Edit";
        refresh(buttonConnector);
        
        navigationBarConnector.getState().setRightComponent(buttonConnector);
        
        refreshContent();
        
        return navigationViewConnector.getWidget();
    }

    
    private <T extends ComponentConnector> T init(T c) {
        c.doInit("" + id++, appConnection);
        c.setParent(this);
        return c;
    }

    @Override
    public Widget getWidget() {
        return  super.getWidget();
    }

    @Override
    public void updateCaption(ComponentConnector connector) {
    }


    @Override
    public void onConnectorHierarchyChange(
            ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
    }
    
    private void refresh(StateChangeHandler... handlers) {
        StateChangeEvent e = new StateChangeEvent(this, new JSONObject(), true);
        for (StateChangeHandler h : handlers) {
            h.onStateChanged(e);
        }
    }
    
    private void addComponent(AbstractHasComponentsConnector c, ComponentConnector e) {
        addComponents(c, e);
    }
    
    @SuppressWarnings("unchecked")
    private void addComponents(AbstractHasComponentsConnector c, ComponentConnector... e) {
        c.setChildComponents(Arrays.asList(e));
        c.onConnectorHierarchyChange(new ConnectorHierarchyChangeEvent() {{setOldChildren(Collections.EMPTY_LIST);}});
        refresh(c);
        for (ComponentConnector cc : e) {
            if (c.getState() instanceof AbstractOrderedLayoutState) {
                ((AbstractOrderedLayoutState)c.getState()).childData.put(cc, new ChildComponentData());
            }
            refresh((StateChangeHandler)cc);
        }
    }

    
    
    private void refreshContent() {
        VerticalComponentGroupConnector content = init(new VerticalComponentGroupConnector());

        for (final Tokka t: Tokka.getTokkaDb()) {
            HorizontalLayoutConnector h = createRow(t.getName(), t.getDone(), t.getTotal());
            addComponent(content, h);
//            h.addLayoutClickListener(new LayoutClickListener() {
//                public void layoutClick(LayoutClickEvent event) {
//                    getNavigationManager().navigateTo(new TokkaTodoView(t));                
//                }
//            });
        }
        addComponents(navigationViewConnector, navigationBarConnector, content);
    }

    private HorizontalLayoutConnector createRow(String name, int done, int total) {

        LabelConnector lName = init(new LabelConnector());
        LabelState s = lName.getState();
        s.text = name;
        s.styles = Arrays.asList("tokka-row");
        s.height = s.width = "100%";
        s.width = "10em";

        LabelConnector lDone = init(new LabelConnector());
        s = lDone.getState();
        s.text = done + "/" + total;
        s.styles = Arrays.asList("tokka-row", "tokka-done");
        s.width = "3em";
        s.height = "100%";

        LabelConnector lEpmty = init(new LabelConnector());
        s = lEpmty.getState();
        s.width = "1.5em";
        s.height = "100%";

        HorizontalLayoutConnector h = init(new HorizontalLayoutConnector());
        HorizontalLayoutState hs = h.getState();
        hs.height = "100%";
        hs.width = "100%";

        addComponents(h, lName, lDone, lEpmty);
        h.getState().childData.get(lName).alignmentBitmask = Bits.ALIGNMENT_LEFT;
        h.getState().childData.get(lDone).alignmentBitmask = Bits.ALIGNMENT_HORIZONTAL_CENTER;
        h.getState().childData.get(lName).expandRatio = 1f;
        h.getState().styles = Arrays.asList("v-touchkit-navbutton");
        refresh(h, lName, lDone, lEpmty);

        return h;
    };
    
    static boolean injected = false;
    private void injectStyles() {
        if (!injected) {
            StyleInjector.inject(".tokka-row {margin-bottom: 10px} .tokka-done{background:grey; color: white; text-align: center; border-radius: 50px}");
            injected = true;
        }
    }    

}
