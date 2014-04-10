package org.apache.hupa.vaadin.ui;


import com.vaadin.addon.touchkit.ui.EmailField;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MainView extends NavigationManager {

    public MainView() {
        
        
//        final NavigationView v = new NavigationView("AAAA");
//        final VerticalLayout vv = new VerticalLayout();
//        setCurrentComponent(v);
        
//        final Button b = new Button("click");
//        setCurrentComponent(b);
//////        vv.addComponent(b);
//        b.addClickListener(new ClickListener() {
//            public void buttonClick(ClickEvent event) {
//                System.out.println("AAAA");
//                EmailField f = new EmailField("Email");
//              setCurrentComponent(f);
//
////                vv.addComponent(f);
//            }
//        });
////        v.setContent(vv);
        
        setCurrentComponent(new TokkaView());
//        setCurrentComponent(new TokkaMain());
    }
}
