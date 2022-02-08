package net.rho.components;

import net.rho.renderer.Texture;
import org.joml.Vector2f;

public class Sprite {


    private final Texture texture;
    private final Vector2f[] texCoords;

    public Sprite(Texture texture){
        this(texture, new Vector2f[]{
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)});

    }

    public Sprite(Texture texture, Vector2f[] texCoords){
        this.texture = texture;
        this.texCoords = texCoords;
    }


    public Texture getTexture(){
        return this.texture;
    }

    public Vector2f[] getTexCoords(){
        return this.texCoords;
    }

}