package net.rho.components;


import net.rho.core.Component;
import net.rho.core.Transform;
import net.rho.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private final Vector4f color;
    private Sprite sprite;

    private Transform lastTransform;
    private boolean isDirty = true;


    public SpriteRenderer(Vector4f color){
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
    }



    @Override
    public void start() {
        this.lastTransform = super.gameObject.getTransform();
    }

    @Override
    public void update(float dt) {
        Transform transform = super.getGameObject().getTransform();
        if (!this.lastTransform.equals(transform)){
            transform.copyTo(this.lastTransform);
            this.isDirty = true;
        }
    }


    public Vector4f getColor(){
        return this.color;
    }


    public Texture getTexture() {
        return this.sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return this.sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f color){
        if (!this.color.equals(color)){
            this.color.set(color);
            this.isDirty = true;
        }
    }

    public boolean isDirty(){
        return this.isDirty;
    }

    public void setClean(){
        this.isDirty = false;
    }


}
