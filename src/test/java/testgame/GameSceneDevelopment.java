package testgame;

import imgui.ImGui;
import net.rho.components.RigidBody;
import net.rho.components.SpriteRenderer;
import net.rho.components.SpriteSheet;
import net.rho.core.Camera;
import net.rho.core.GameObject;
import net.rho.core.Scene;
import net.rho.core.Transform;
import net.rho.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class GameSceneDevelopment extends Scene {


    private GameObject obj1;
    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0f;
    private SpriteSheet spriteSheet;


    public GameSceneDevelopment() {
        super("GameSceneDevelopment");

    }


    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));

        super.load();

        this.spriteSheet = AssetPool.getSpriteSheet("assets/images/spritesheet.png");
        if (super.levelLoaded) {
            this.activateGameObject = gameObjects.get(1);
            this.obj1 = super.gameObjects.get(0);
            return;
        }


        this.obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), 1);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setSprite(this.spriteSheet.getSprite(0));
        this.obj1.addComponent(obj1Sprite);
        this.addGameObjectToScene(this.obj1);


        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 3);
        SpriteRenderer obj2Sprite = new SpriteRenderer();
        obj2Sprite.setColor(new Vector4f(1, 0, 0, 1));
        obj2.addComponent(obj2Sprite);
        obj2.addComponent(new RigidBody());
        this.addGameObjectToScene(obj2);
        this.activateGameObject = obj2;


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


    public void imgui(){
        ImGui.begin("Test Window");
        ImGui.text("Some random text");
        ImGui.end();
    }
}
