package net.rho.core;

public abstract class Component {

    protected GameObject gameObject = null;

    // TODO bad practice methods should be defined.
    public void update(float dt){

    }

    public void start(){

    }

    public GameObject getGameObject(){
        return this.gameObject;
    }


    public void imgui(){

    }

}
