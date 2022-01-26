package net.rho.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Camera camera;
    private boolean isRunning;
    protected final List<GameObject> gameObjects;

    public Scene() {
        this.isRunning = false;
        this.gameObjects = new ArrayList<>();
    }


    public void init() {

    }


    public void start(){
        for (GameObject gameObject : this.gameObjects) {
            gameObject.start();
        }
        this.isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject){
        gameObjects.add(gameObject);
        if (isRunning) gameObject.start();

    }

    public abstract void update(float dt);


}
