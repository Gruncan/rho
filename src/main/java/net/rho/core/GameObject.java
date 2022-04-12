package net.rho.core;

import net.rho.components.AbstractComponent;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    public Transform transform;
    private String name;
    private List<AbstractComponent> components;
    private final int zIndex;

    public GameObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = new Transform();
        this.zIndex = 0;
    }

    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        this.zIndex = zIndex;
        this.components = new ArrayList<>();
        this.transform = transform;
    }

    public <T extends AbstractComponent> T getComponent(Class<T> AbstractComponentClass) {
        for (AbstractComponent c : this.components) {
            if (AbstractComponentClass.isAssignableFrom(c.getClass())) {
                try {
                    return AbstractComponentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting AbstractComponent.";
                }
            }
        }

        return null;
    }

    public <T extends AbstractComponent> void removeComponent(Class<T> AbstractComponentClass) {
        for (int i = 0; i < this.components.size(); i++) {
            AbstractComponent c = this.components.get(i);
            if (AbstractComponentClass.isAssignableFrom(c.getClass())) {
                this.components.remove(i);
                return;
            }
        }
    }

    public void addComponent(AbstractComponent c) {
        this.components.add(c);
        c.setGameObject(this);
    }

    public void update(float dt) {
        for (AbstractComponent component : this.components) {
            component.update(dt);
        }
    }

    public void start() {
        for (AbstractComponent component : this.components) {
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

    public void imgui(){
        for (AbstractComponent c : this.components) {
            c.imgui();
        }
    }


}
