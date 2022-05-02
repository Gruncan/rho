package testgame;

import net.rho.core.Window;

public class Main {


    public static void main(String[] args) {
        Window window = Window.getInstance();
        window.setWindow("Snake Game", 1920, 1080);


        window.addScene(0, new SnakeScene());

        window.run();
    }

}
