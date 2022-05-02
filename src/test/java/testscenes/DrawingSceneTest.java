package testscenes;

import net.rho.components.SpriteRenderer;
import net.rho.core.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class DrawingSceneTest extends Scene {

    private final double x = 100;
    private final double y = 100;
    private int count = 0;
    private int yOffSet = 0;
    private float drawTime = 0.2f;


    public DrawingSceneTest() {
        super("Drawing scene");
    }

    @Override
    public void init() {

        super.camera = new Camera(new Vector2f(-250, 0));


    }

    private void drawPoint(float x, float y) {
        GameObject obj1 = new GameObject("Dot", new Transform(new Vector2f(x, y), new Vector2f(6, 3)), 1);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(0, 0, 0, 1));
        obj1.addComponent(obj1Sprite);
        super.addGameObjectToScene(obj1);
    }


    @Override
    public void update(float dt) {
        this.drawTime -= dt;

        if (this.drawTime <= 0) {
            this.drawTime = 0f;
            if (this.count > 700) {
                this.yOffSet += 50;
                this.count = 0;
            }
            if (this.yOffSet > Window.getInstance().getHeight()) {
                return;
            }
            double j = 20 * (1 + Math.cos((2 * Math.PI * this.count / 100)));

            this.drawPoint((float) (this.count + x), (float) (j + y + this.yOffSet));
            this.count++;

        }


        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        super.renderer.render();
    }
}
