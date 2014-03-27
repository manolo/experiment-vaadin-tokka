package org.apache.hupa.vaadin.ui;

import org.apache.hupa.vaadin.gwt.client.ui.TokkaMainClientRpc;
import org.apache.hupa.vaadin.gwt.client.ui.TokkaMainServerRpc;
import org.apache.hupa.vaadin.gwt.client.ui.TokkaMainState;

import com.vaadin.shared.MouseEventDetails;

public class TokkaMain extends com.vaadin.ui.AbstractComponent {

    private TokkaMainServerRpc rpc = new TokkaMainServerRpc() {
        private int clickCount = 0;

        public void clicked(MouseEventDetails mouseDetails) {
            // nag every 5:th click using RPC
            if (++clickCount % 5 == 0) {
                getRpcProxy(TokkaMainClientRpc.class).alert(
                        "Ok, that's enough!");
            }
            // update shared state
            getState().text = "You have clicked " + clickCount + " times";
        }
    };

    public TokkaMain() {
        registerRpc(rpc);
    }

    @Override
    public TokkaMainState getState() {
        return (TokkaMainState) super.getState();
    }
}
