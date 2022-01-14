package net.rho.renderer;

public class Vertex {


    private final float xPos;
    private final float yPos;
    private final float zPos;

    private final Colour colour;


    public Vertex(float xPos, float yPos, float zPos, Colour colour) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.colour = colour;
    }

    public Vertex(float xPos, float yPos, float zPos, float r, float g, float b, float a) {
        this(xPos, yPos, zPos, new Colour(r, g, b, a));
    }


    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public float getZ() {
        return zPos;
    }

    public float getR() {
        return this.colour.r;
    }

    public float getG() {
        return this.colour.g;
    }

    public float getB() {
        return this.colour.b;
    }

    public float getA() {
        return this.colour.a;
    }


    public float[] getArray() {
        return new float[]{this.xPos, this.yPos, this.zPos, this.colour.r, this.colour.g, this.colour.b, this.colour.a};
    }


    public record Colour(float r, float g, float b, float a) {

    }

}
