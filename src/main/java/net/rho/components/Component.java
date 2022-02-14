package net.rho.components;

import net.rho.core.GameObject;

public abstract class Component {

    protected transient GameObject gameObject = null;

    // TODO bad practice methods should be defined.
    public void update(float dt) {

    }

    public void start() {

    }

    public GameObject getGameObject() {
        return this.gameObject;
    }


    public void imgui() {

    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }


}
