package testgame;

import net.rho.components.SpriteRenderer;
import net.rho.core.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.Random;

public class Apple {


    public static Transform getTransform() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(((1250 - 100) / 15) * 15);
        } while (x % 15 != 0);

        do {
            y = random.nextInt(((650 - 100) / 15) * 15);
        } while (y % 15 != 0);
        System.out.printf("(%s, %s)%n", x, y);
        return new Transform(new Vector2f(x, y), new Vector2f(15, 15));
    }

    public static SpriteRenderer getSpriteRenderer() {
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(1, 0, 0, 1));
        return obj1Sprite;
    }


}
