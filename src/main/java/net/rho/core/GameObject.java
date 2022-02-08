package net.rho.core;

import java.util.HashMap;
import java.util.Map;

public class GameObject {

    private final String name;
    private final Map<Class<? extends Component>, Component> components;
    private final Transform transform;
    private final int zIndex;

    public GameObject(String name){
        this(name, new Transform(), 0);
    }

    public GameObject(String name, Transform transform, int zIndex){
        this.name = name;
        this.components = new HashMap<>();
        this.transform = transform;
        this.zIndex = zIndex;
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

    public Transform getTransform(){
        return this.transform.copy();
    }


    public void moveX(float inc){
        this.transform.increaseX(inc);
    }

    public int getZIndex(){
        return this.zIndex;
    }


}
