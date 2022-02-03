package net.rho.core;

import net.rho.components.SpriteRenderer;
import net.rho.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {



    public LevelEditorScene() {

    }


    @Override
    public void init() {

        this.camera = new Camera(new Vector2f());

        int xOffSet = 10;
        int yOffSet = 10;

        float totalWidth = (600 - xOffSet * 2);
        float totalHeight = (300 - yOffSet *2);
        float sizeX = totalWidth / 100f;
        float sizeY = totalHeight / 100f;


        for (int x=0; x < 100; x++){
            for (int y = 0; y < 100; y++) {
                float xPos = xOffSet + (x * sizeX);
                float yPos = yOffSet + (y * sizeY);

                GameObject gameObject = new GameObject("Obj"+x+""+y, new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
                gameObject.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalWidth, 1, 1)));
                this.addGameObjectToScene(gameObject);
            }
        }
        loadResources();

    }


    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {
        for (GameObject gameObject : super.gameObjects) {
            gameObject.update(dt);
        }
        this.renderer.render();


    }
}
