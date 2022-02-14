package net.rho.components;

import net.rho.renderer.Texture;
import org.joml.Vector2f;


// could extend texture
public class Sprite {


    private Texture texture = null;
    private Vector2f[] texCoords = new Vector2f[]{
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1)};


    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }

}
