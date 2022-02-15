package net.rho.components;

import net.rho.core.GameObject;

public abstract class AbstractComponent implements IComponent {

    protected transient GameObject gameObject = null;

    // TODO bad practice methods should be defined.
    @Override
    public void update(float dt) {

    }

    @Override
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

