package net.rho.core;

import org.joml.Vector2f;

import java.util.Objects;

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

    public Transform copy(){
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copyTo(final Transform to){
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transform transform = (Transform) o;
        return Objects.equals(position, transform.position) && Objects.equals(scale, transform.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, scale);
    }


    public void increaseX(float inc) {
        this.position.x += inc;
    }

    public void increaseY(float inc) {
        this.position.y += inc;
    }


    public void setXY(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

}