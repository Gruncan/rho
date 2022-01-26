package net.rho.core;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private final String name;
    private final List<Component> components;

    public GameObject(String name){
        this.name = name;
        this.components = new ArrayList<>();
    }

    public <T extends Component> T getComponent(Class<T> componentClass){
        for (Component c : components){
            if (componentClass.isAssignableFrom(c.getClass())){
                try {
                    return componentClass.cast(c);
                }catch (ClassCastException e){
                    e.printStackTrace();
                    assert false : "Error : Casting component.";
                }
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass){
        for (int i=0; i< components.size(); i++) {
            if (componentClass.isAssignableFrom(components.get(i).getClass())){
                components.remove(i);
                return;
            }
        }
    }


    public void addComponent(Component c){
        this.components.add(c);
        c.gameObject = this;
    }

    public void update(float dt){
        for (Component component : components) {
            component.update(dt);
        }
    }
    public void start(){
        for (Component component : components) {
            component.start();
        }
    }



}
