package net.rho.core;

import org.joml.Vector2f;

public class Transform {


    private final Vector2f position;
    private final Vector2f scale;

    public Transform(){
        this(new Vector2f(), new Vector2f());
    }


    public Transform(Vector2f position){
        this(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale){
        this.position = position;
        this.scale = scale;
    }

    public float getXPos(){
        return this.position.x;
    }

    public float getYPos(){
        return this.position.y;
    }

    public float getXScale(){
        return this.scale.x;
    }

    public float getYScale(){
        return this.scale.y;
    }


}