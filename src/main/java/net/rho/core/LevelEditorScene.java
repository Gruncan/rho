package net.rho.core;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {


    private boolean changingScene = false;
    private float timeToChangeScene = 2f;

    public LevelEditorScene() {
        System.out.println("Inside LevelEditorScene");
    }


    @Override
    public void update(float dt) {

        System.out.printf("FPS: %f%n", (1 / dt));


        if (!this.changingScene && KeyListener.getInstance().isKeyPressed(KeyEvent.VK_SPACE)) {
            this.changingScene = true;
        }


        if (this.changingScene && this.timeToChangeScene > 0) {
            this.timeToChangeScene -= dt;
            float v = dt * 5f;
            Window.getInstance().decreaseRGB(v, v, v);
        } else if (this.changingScene) {
            Window.changeScene(1);
        }

    }
}
