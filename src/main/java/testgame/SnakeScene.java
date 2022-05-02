package testgame;

import net.rho.components.SpriteRenderer;
import net.rho.core.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class SnakeScene extends Scene {


    private final Collision.Rec map;
    private Snake snake;
    private GameObject apple;
    private float timer;
    private float speed;
    private boolean isOver;
    private int score;

    public SnakeScene() {
        super("Snake game scene");
        this.timer = 0;
        this.speed = 0.05f;
        this.isOver = false;
        this.map = new Collision.Rec(0, 0, 1300, 700);
    }

    @Override
    public void init() {
        super.camera = new Camera(new Vector2f(0, 0));


        for (int i = 0; i < 1300; i += 15) {
            GameObject gameObject = new GameObject("Line", new Transform(new Vector2f(i, 0), new Vector2f(1.17f, 700)), 2);
            SpriteRenderer spr = new SpriteRenderer();
            spr.setColor(new Vector4f(0.1f, 0.1f, 0.1f, 0.5f));
            gameObject.addComponent(spr);
            super.addGameObjectToScene(gameObject);
        }

        for (int i = 0; i < 700; i += 15) {
            GameObject gameObject = new GameObject("Line", new Transform(new Vector2f(0, i), new Vector2f(1300, 2f)), 2);
            SpriteRenderer spr = new SpriteRenderer();
            spr.setColor(new Vector4f(0.1f, 0.1f, 0.1f, 0.5f));
            gameObject.addComponent(spr);
            super.addGameObjectToScene(gameObject);
        }


        this.snake = new Snake(390, 390);
        this.score = 0;
        this.apple = new GameObject("Apple", Apple.getTransform(), 3);
        this.apple.addComponent(Apple.getSpriteRenderer());
        super.addGameObjectToScene(this.apple);

        for (GameObject gameObject : this.snake.getGameObjects())
            super.addGameObjectToScene(gameObject);


    }

    @Override
    public void update(float dt) {

        System.out.println(snake);
        this.timer += dt;
        if (this.timer > this.speed) {
            timer = 0;

            if (this.snake.isCollided()) {
                this.isOver = true;
                System.out.println("Game is over");
                return;
            }


            if (!this.isOver) {
                Collision.Rec apple = new Collision.Rec((int) this.apple.getXPos(), (int) this.apple.getYPos(), 15, 15);
                Collision.Rec head = new Collision.Rec(this.snake.getXPos(), this.snake.getYPos(), 15, 15);

                if (!Collision.collisionDetection(head, map)) {
                    this.isOver = true;
                    System.out.println("Game Over");
                }


                if (Collision.collisionDetection(apple, head)) {
                    this.score++;
                    GameObject gameObject = this.snake.increaseSize();
                    super.addGameObjectToScene(gameObject);
                    this.apple.transform = Apple.getTransform();
                    this.speed -= 0.00025f;
                }


                if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_RIGHT) && this.snake.getMoveX() >= 0) {
                    this.snake.moveX(1);
                } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_LEFT) && this.snake.getMoveX() <= 0) {
                    this.snake.moveX(-1);
                } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_UP) && this.snake.getMoveY() >= 0) {
                    this.snake.moveY(1);
                } else if (KeyListener.getInstance().isKeyPressed(GLFW_KEY_DOWN) && this.snake.getMoveY() <= 0) {
                    this.snake.moveY(-1);
                }
                this.snake.move();
            }

            for (GameObject gameObject : super.gameObjects) {
                gameObject.update(dt);
            }

        }
        super.renderer.render();
    }


}
