package org.apache.hupa.vaadin.ui;

import org.apache.hupa.vaadin.model.Tokka;
import org.apache.hupa.vaadin.model.Tokka.Todo;

import com.vaadin.addon.touchkit.extensions.TouchKitIcon;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class TokkaTodoView extends NavigationView {
    
    public static enum Filter{all, todo, done}  
    
    final TextField addBox;

    public TokkaTodoView(final Tokka tokka) {
        setCaption(tokka.getName());
        setSizeFull();
        
        addBox = new TextField();
        addBox.setInputPrompt("New Item ...");
        TouchKitIcon.plus.addTo(addBox);
        addBox.setImmediate(true);
        addBox.addShortcutListener(
            new ShortcutListener("New", ShortcutAction.KeyCode.ENTER, null) {
                public void handleAction(Object sender, Object target) {
                    doNew(tokka);
                }

        });
        addBox.addBlurListener(new BlurListener() {
            public void blur(BlurEvent event) {
                doNew(tokka);
            }
        });
        
        Toolbar toolbar = new Toolbar();
        toolbar.setSizeFull();
        setToolbar(toolbar);
        
        Button bAll = new Button(Filter.all.name());
        toolbar.addComponent(bAll);
        TouchKitIcon.book.addTo(bAll);
        bAll.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                doFilter(tokka, Filter.all);
            }
        });
        bAll.click();
        
        Button bTodo = new Button(Filter.todo.name());
        toolbar.addComponent(bTodo);
        TouchKitIcon.ambulance.addTo(bTodo);
        bTodo.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                doFilter(tokka, Filter.todo);
            }
        });
        
        Button bDone = new Button(Filter.done.name());
        toolbar.addComponent(bDone);
        TouchKitIcon.download.addTo(bDone);
        bDone.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                doFilter(tokka, Filter.done);
            }
        });
        
    }
    
    private void doNew(final Tokka tokka) {
        String v = addBox.getValue().trim();
        if (!v.isEmpty()) {
            tokka.addTodo(new Todo(addBox.getValue()));
            doFilter(tokka, Filter.all);
            addBox.setValue("");
        }
    }
    
    private void doFilter(Tokka tokka, Filter f) {
        VerticalComponentGroup content = new VerticalComponentGroup();
        content.addComponent(addBox);
        for (final Todo t : tokka.getTodos()) {
            if (f == Filter.done && !t.isDone()) continue;
            if (f == Filter.todo && t.isDone()) continue;
            final Switch s = new Switch(t.getName());
            s.setImmediate(true);
            s.setValue(t.isDone());
            s.addValueChangeListener(new ValueChangeListener() {
                public void valueChange(ValueChangeEvent event) {
                    t.setDone(s.getValue());
                }
            });
            content.addComponent(s);
        }
        setContent(content);
    }
    
}
