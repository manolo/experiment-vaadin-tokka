package org.apache.hupa.vaadin.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

@SuppressWarnings({"unchecked" })
public class Tokka implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    static final String tmp = System.getProperty("java.io.tmpdir") + "/tokka.ser";
    public static HashMap<String, Tokka> tokkas;

    static {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tmp));
            tokkas = (HashMap<String, Tokka>)ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
            tokkas = new HashMap<String, Tokka>();
            Tokka t = new Tokka("Shopping list");
            t.addTodo(new Todo("Tomatoes"));
            t.addTodo(new Todo("Bread"));
            t.addTodo(new Todo("Beaf"));
            t.addTodo(new Todo("Lentils"));
            t.addTodo(new Todo("Omnion"));
            t = new Tokka("Work Todo");
            t.addTodo(new Todo("Checkout snapshot"));
            t.addTodo(new Todo("Implement wireframe"));
            t.addTodo(new Todo("Answer survey"));
        }
    }
    
    public static Collection<Tokka> getTokkaDb() {
        return tokkas.values();
    }
    
    public static Tokka findTokkaByName(String name) {
        return tokkas.get(name);
    }
    
    private static void persistTokkaDb() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tmp));
            oos.writeObject(tokkas);
            oos.close();
        } catch (Exception e) {
        }
    }

    public static class Todo implements Serializable {
        private static final long serialVersionUID = 1L;
        
        
        private String name;
        private boolean done;
        
        public Todo(String n) {
            name = n;
        }
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
            persistTokkaDb();
        }
        public boolean isDone() {
            return done;
        }
        public void setDone(boolean done) {
            this.done = done;
            persistTokkaDb();
        }
        public boolean equals(Object obj) {
            return obj instanceof Todo && ((Todo)obj).name.equals(name);
        }
    }
    
    private String name;
    private HashMap<String, Todo> todos = new HashMap<String, Tokka.Todo>();
    
    public Collection<Todo> getTodos() {
        return todos.values();
    }
    
    public Tokka addTodo(Todo t) {
        todos.put(t.getName(), t);
        persistTokkaDb();
        return this;
    }

    public Tokka(String n) {
        name = n;
        tokkas.put(n, this);
        persistTokkaDb();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getDone() {
        int n = 0;
        for (Todo t : getTodos())
            if (t.isDone())
                n++;
        return n;
    }

    public int getTotal() {
        return getTodos().size();
    }
    
    @Override
    public String toString() {
        String r = "\nTask: " + name;
        for (Todo t : getTodos()) {
            r += "\n   Todo: " +  t.getName() + " done: " + t.isDone();
        }
        return r;
     }    
}
