package testscenes;

import net.rho.core.GameObject;
import net.rho.core.Scene;

public class GameTestScene extends Scene {


    public GameTestScene(String name) {
        super(name);
    }

    @Override
    public void init() {


    }

    @Override
    public void update(float dt) {
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }



        this.renderer.render();
    }
}
