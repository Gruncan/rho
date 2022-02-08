package net.rho.renderer.components;

import org.joml.Vector3f;

public class Vertex {



    private final Vector3f pos;
    private final ColorF color;
    private final int x;
    private final int y;

    // position                  colour (rgb)         // UV coordinates
    //100.5f, -0.5f, 0.0f,   1.0f, 1.0f, 0.0f, 1.0f,      1, 1,              // Bottom right

    public Vertex(Vector3f pos, ColorF color, int x, int y){
        this.pos = pos;
        this.color = color;
        this.x = x;
        this.y = y;
    }
    public Vertex(float x, float y, float z, float r, float g, float b, float a, int ux, int uy){
        this(new Vector3f(x, y, z), new ColorF(r, g, b, a), ux, uy);
    }


    public float[] getArray(){
        return new float[]{this.pos.x, this.pos.y, this.pos.z, this.color.r(), this.color.g(), this.color.b(), this.color.a(), this.x, this.y};
    }

    public int getSize(){
        // x, y, z, r, g, b, a, ux, uy = 9
        return 9;
    }


    public int getVectorSize(){
        return 3;
    }

    public int getColorSize(){
        return 4;
    }
    public int getUVSize(){
        return 2;
    }







}
