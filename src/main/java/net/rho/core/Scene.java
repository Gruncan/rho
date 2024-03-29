package net.rho.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import net.rho.components.AbstractComponent;
import net.rho.components.ComponentDeserializer;
import net.rho.components.SpriteSheet;
import net.rho.renderer.Renderer;
import net.rho.util.AssetPool;
import net.rho.util.FileWriter;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Camera camera;
    private boolean isRunning;
    protected final List<GameObject> gameObjects;
    protected Renderer renderer;
    private final String name;
    protected GameObject activateGameObject;
    protected boolean levelLoaded = false;


    public Scene(String name) {
        this.isRunning = false;
        this.gameObjects = new ArrayList<>();
        this.name = name;
        this.renderer = new Renderer();
        this.activateGameObject = null;
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

    public void sceneImgui(){
        if(activateGameObject != null){
            ImGui.begin("Inspector");
            activateGameObject.imgui();
            ImGui.end();
        }
        this.imgui();
    }

    // TODO bad practice
    public void imgui() {

    }


    public void saveExit() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(AbstractComponent.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        boolean suc = FileWriter.write(gson.toJson(this.gameObjects), "level.lvl");
    }

    public void load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(AbstractComponent.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        String inFile = FileWriter.read("level.lvl");

        if (inFile != null) {
            GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
            for (GameObject obj : objs) {
                this.addGameObjectToScene(obj);
            }
            this.levelLoaded = true;
        }
    }


    @Override
    public String toString() {
        return this.name;
    }


}


