package testgame;

import net.rho.components.SpriteRenderer;
import net.rho.components.SpriteSheet;
import net.rho.core.Camera;
import net.rho.core.GameObject;
import net.rho.core.Scene;
import net.rho.core.Transform;
import net.rho.util.AssetPool;
import org.joml.Vector2f;

public class GameScene extends Scene {



    private GameObject obj1;
    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft= 0f;
    private SpriteSheet spriteSheet;


    public GameScene() {
        super("GameScene");

    }


    @Override
    public void init() {
        loadResources();


        this.camera = new Camera(new Vector2f(-250, 0));


        this.spriteSheet = AssetPool.getSpriteSheet("assets/images/spritesheet.png");


        this.obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        this.obj1.addComponent(new SpriteRenderer(this.spriteSheet.getSprite(0)));
        this.addGameObjectToScene(this.obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(this.spriteSheet.getSprite(5)));
        this.addGameObjectToScene(obj2);





    }


    @Override
    public void update(float dt) {
        this.spriteFlipTimeLeft -= dt;

        if (this.spriteFlipTimeLeft <= 0){
            this.spriteFlipTimeLeft = this.spriteFlipTime;
            this.spriteIndex++;
            if (this.spriteIndex > 4){
                this.spriteIndex = 0;
            }
            this.obj1.getComponent(SpriteRenderer.class).setSprite(this.spriteSheet.getSprite(this.spriteIndex));
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();


    }
}
