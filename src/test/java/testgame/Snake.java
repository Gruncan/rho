package testgame;

import net.rho.components.SpriteRenderer;
import net.rho.core.GameObject;
import net.rho.core.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Snake {


    private final static int MOVE_SIZE = 5;
    private final List<GameObject> objects;
    private int size;
    private int x, y;
    private int endX, endY;


    public Snake(int x, int y) {
        this.objects = new ArrayList<>();
        this.size = 0;
        this.x = x;
        this.y = y;
        this.endX = x;
        this.endY = y;

        this.increaseSize();
        this.increaseSize();
        this.increaseSize();

    }


    public void increaseSize() {
        GameObject obj1 = new GameObject("Part", new Transform(new Vector2f(this.endX, this.endY), new Vector2f(3, 3)), 1);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(0, 1, 0, 1));
        obj1.addComponent(obj1Sprite);
        this.size++;
    }


    public void moveX(int dir) {
        int v = (dir > 0 ? 1 : -1) * MOVE_SIZE;
        this.move(v, 0);
    }

    public void moveY(int dir) {
        int v = (dir > 0 ? 1 : -1) * MOVE_SIZE;
        this.move(0, v);
    }

    public void move(int xDir, int yDir) {
        GameObject gameObject = this.objects.remove(this.size - 1);
        Transform transform = this.objects.get(0).getTransform();
        gameObject.transform.setXY(transform.getXPos() + xDir, transform.getYPos() + yDir);
        this.objects.add(0, gameObject);
    }


}
