package net.rho.core;

import java.util.HashMap;
import java.util.Map;

public class GameObject {

    private final String name;
    private final Map<Class<? extends Component>, Component> components;
    private final Transform transform;

    public GameObject(String name){
        this(name, new Transform());
    }

    public GameObject(String name, Transform transform){
        this.name = name;
        this.components = new HashMap<>();
        this.transform = transform;
    }


    public <T extends Component> T getComponent(Class<T> componentClass){
        return componentClass.cast(this.components.get(componentClass));
    }

    public <T extends Component> void removeComponent(Class<T> componentClass){
        this.components.remove(componentClass);
    }


    public void addComponent(Component c){
        this.components.put(c.getClass(), c);
        c.gameObject = this;
    }

    public void update(float dt){
        for (Component component : this.components.values()) {
            component.update(dt);
        }
    }
    public void start(){
        for (Component component : this.components.values()) {
            component.start();
        }
    }


    public float getXPos(){
        return this.transform.getXPos();
    }

    public float getYPos(){
        return this.transform.getYPos();
    }

    public float getXScale(){
        return this.transform.getXScale();
    }

    public float getYScale(){
        return this.transform.getYScale();
    }



}
