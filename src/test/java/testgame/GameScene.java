package testgame;

import net.rho.components.SpriteRenderer;
import net.rho.core.*;
import net.rho.util.AssetPool;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {

    public GameScene() {
        super("GameScene");

    }


    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());

//        int xOffSet = 10;
//        int yOffSet = 10;
//
//        float totalWidth = (600 - xOffSet * 2);
//        float totalHeight = (300 - yOffSet * 2);
//        float sizeX = totalWidth / 100f;
//        float sizeY = totalHeight / 100f;
//
//
//        for (int x = 0; x < 100; x++) {
//            for (int y = 0; y < 100; y++) {
//                float xPos = xOffSet + (x * sizeX);
//                float yPos = yOffSet + (y * sizeY);
//
//                GameObject gameObject = new GameObject("Obj" + x + "" + y, new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
//                gameObject.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalWidth, 0.2f, 1)));
//                this.addGameObjectToScene(gameObject);
//            }
//        }

        this.camera = new Camera(new Vector2f(-250, 0));

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage.png")));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage2.png")));
        this.addGameObjectToScene(obj2);




        loadResources();

    }


    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
    }


    @Override
    public void update(float dt) {
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.position.x += 100f * dt;
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_LEFT)) {
            camera.position.x -= 100f * dt;
        }
        if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_UP)) {
            camera.position.y += 100f * dt;
        } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_DOWN)) {
            camera.position.y -= 100f * dt;
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();


    }
}
