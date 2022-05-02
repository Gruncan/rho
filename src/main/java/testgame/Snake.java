package testgame;

import net.rho.components.SpriteRenderer;
import net.rho.core.GameObject;
import net.rho.core.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Snake {


    private final static int MOVE_SIZE = 15;
    private final static double END_SIZE = 15;
    private final SnakeQueue snakeQueue;
    private int size;
    private int x, y;
    private int endX, endY;

    private boolean directionIsX;
    private int moveX, moveY;


    public Snake(int x, int y) {
        this.snakeQueue = new SnakeQueue(40);
        this.size = 0;
        this.x = x;
        this.y = y;
        this.endX = x;
        this.endY = y;
        this.moveX(-1);
        this.directionIsX = true;

        for (int i = 0; i < 10; i++) {
            this.increaseSize();
        }
    }


    public GameObject increaseSize() {
        GameObject obj1 = new GameObject("Part", new Transform(new Vector2f(this.endX, this.endY), new Vector2f(15, 15)), 3);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(0, 1, 0, 1));
        obj1.addComponent(obj1Sprite);
        this.size++;
        if (this.directionIsX) {
            this.endX += END_SIZE;
        } else {
            this.endY += END_SIZE;
        }
        this.snakeQueue.add(obj1);

        return obj1;
    }


    public void moveX(int dir) {
        int v = (dir > 0 ? 1 : -1) * MOVE_SIZE;
        this.moveX = v;
        this.moveY = 0;
        this.directionIsX = true;
    }

    public void moveY(int dir) {
        int v = (dir > 0 ? 1 : -1) * MOVE_SIZE;
        this.moveX = 0;
        this.moveY = v;
        this.directionIsX = false;
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    public void move() {
        GameObject gameObject = this.snakeQueue.remove();
        Transform transform = this.snakeQueue.peak().getTransform();

        gameObject.transform.setXY(transform.getXPos() + this.moveX, transform.getYPos() + this.moveY);
        this.snakeQueue.insert(gameObject);

        this.x += this.moveX;
        this.y += this.moveY;
        if (this.directionIsX) {
            this.endX -= END_SIZE;
        } else {
            this.endY -= END_SIZE;
        }
    }


    public GameObject[] getGameObjects() {
        return snakeQueue.getArray();
    }


    public SnakeQueue getSnakeQueue() {
        return this.snakeQueue;
    }

    public int getXPos() {
        return this.x;
    }

    public int getYPos() {
        return this.y;
    }

    @Override
    public String toString() {
        return String.format("Snake (%d, %d)", this.x, this.y);
    }

    public boolean isCollided() {
        GameObject head = this.snakeQueue.peak();
        GameObject[] objects = this.snakeQueue.getArray();
        Collision.Rec headRec = new Collision.Rec((int) head.getXPos(), (int) head.getYPos(), 5, 5);
        for (int i = 3; i < objects.length - 1; i++) {
            Collision.Rec rec1 = new Collision.Rec((int) objects[i].getXPos(), (int) objects[i].getYPos(), 5, 5);
            if (Collision.collisionDetection(rec1, headRec)) {
                return true;
            }
        }
        return false;
    }
}
