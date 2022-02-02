package net.rho.core;

import net.rho.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Camera camera;
    private boolean isRunning;
    protected final List<GameObject> gameObjects;
    protected Renderer renderer = new Renderer();

    public Scene() {
        this.isRunning = false;
        this.gameObjects = new ArrayList<>();
    }


    public void init() {

    }


    public void start(){
        for (GameObject gameObject : this.gameObjects) {
            gameObject.start();
            this.renderer.add(gameObject);
        }
        this.isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject){
        gameObjects.add(gameObject);
        if (isRunning){
            gameObject.start();
            this.renderer.add(gameObject);
        }


    }

    public abstract void update(float dt);

    public Camera camera(){
        return this.camera;
    }


}
