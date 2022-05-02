package testgame;

import net.rho.core.Camera;
import net.rho.core.Scene;
import org.joml.Vector2f;

public class SnakeScene extends Scene {


    public SnakeScene(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.camera = new Camera(new Vector2f(-250, 0));

    }

    @Override
    public void update(float dt) {

    }
}
