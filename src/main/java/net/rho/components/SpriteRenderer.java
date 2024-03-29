package net.rho.components;


import imgui.ImGui;
import net.rho.core.Transform;
import net.rho.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends AbstractComponent {

    private final Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = true;


    public SpriteRenderer() {

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


    public void imgui() {
        float[] imColor = {color.x, color.y, color.z, color.w};
        if (ImGui.colorPicker4("Color picker", imColor)){
            this.color.set(imColor[0],imColor[1], imColor[2], imColor[3]);
            this.isDirty = true;
        }
    }
}
