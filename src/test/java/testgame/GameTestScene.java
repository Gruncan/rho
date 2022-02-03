package testgame;

import net.rho.components.SpriteRenderer;
import net.rho.core.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class GameTestScene extends Scene {


    public GameTestScene(String name) {
        super(name);
    }

    @Override
    public void init() {

        this.camera = new Camera(new Vector2f(-250, 0));


        int xOffSet = 10;
        int yOffSet = 10;

        float totalWidth = (600 - xOffSet * 2);
        float totalHeight = (300 - yOffSet * 2);
        float sizeX = totalWidth / 100f;
        float sizeY = totalHeight / 100f;


        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                float xPos = xOffSet + (x * sizeX);
                float yPos = yOffSet + (y * sizeY);

                GameObject gameObject = new GameObject("Obj" + x + "" + y, new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
                gameObject.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalWidth, 0.2f, 1)));
                this.addGameObjectToScene(gameObject);
            }
        }
    }

    @Override
    public void update(float dt) {
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        if(KeyListener.getInstance().isKeyPressed(GLFW_KEY_R)){
            Window.getInstance().changeScene(0);
        }


        this.renderer.render();
    }
}
