package testgame;

import net.rho.components.SpriteRenderer;
import net.rho.components.SpriteSheet;
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
        loadResources();


        this.camera = new Camera(new Vector2f(-250, 0));


        SpriteSheet spriteSheet = AssetPool.getSpriteSheet("assets/images/spritesheet.png");

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(spriteSheet.getSprite(0)));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(spriteSheet.getSprite(5)));
        this.addGameObjectToScene(obj2);





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
        }else if(KeyListener.getInstance().isKeyPressed(GLFW_KEY_E)){
            Window.getInstance().changeScene(1);
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();


    }
}
