package testscenes;

import net.rho.core.Window;

public class Main {


    public static void main(String[] args) {
        Window window = Window.getInstance();
        window.setWindow("GameWindow", 1080, 720);


        window.addScene(0, new DrawingSceneTest());
        window.addScene(1, new GameTestScene("Test Scene"));

        window.run();
    }

}
