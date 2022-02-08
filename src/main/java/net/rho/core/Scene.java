package net.rho.core;

import net.rho.components.SpriteSheet;
import net.rho.renderer.Renderer;
import net.rho.util.AssetPool;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Camera camera;
    private boolean isRunning;
    protected final List<GameObject> gameObjects;
    protected Renderer renderer = new Renderer();
    private final String name;

    public Scene(String name) {
        this.isRunning = false;
        this.gameObjects = new ArrayList<>();
        this.name = name;

    }


    public abstract void init();


    public void start(){
        for (GameObject gameObject : this.gameObjects) {
            gameObject.start();
            this.renderer.add(gameObject);
        }
        this.isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject){
        gameObjects.add(gameObject);
        if (isRunning) gameObject.start();
        if (isRunning){
            gameObject.start();
            this.renderer.add(gameObject);
        }


    }

    public abstract void update(float dt);

    public Camera camera(){
        return this.camera;
    }

    protected void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("assets/images/spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
    }

}


