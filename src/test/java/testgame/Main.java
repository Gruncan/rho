package testgame;

import net.rho.core.Window;

public class Main {


    public static void main(String[] args) {
        Window window = Window.getInstance();
        window.setWindow("GameWindow",1920 ,1080);


        window.addScene(0, new GameScene());
        window.addScene(1, new GameTestScene("Test Scene"));

        window.run();



    }


}
