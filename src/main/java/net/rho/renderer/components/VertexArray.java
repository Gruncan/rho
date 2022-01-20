package net.rho.renderer.components;

import java.util.ArrayList;
import java.util.List;

public class VertexArray {



    private final List<Vertex> vertices;

    public VertexArray(){
        vertices = new ArrayList<>();
    }


    public void addVertex(Vertex v){
        this.vertices.add(v);
    }



    public int totalLength(){
        //return vertices.stream().mapToInt(Vertex::getSize).sum();
        // vertex size will be constant throughout
        return vertices.size() * vertices.get(0).getSize();
    }


    public int getVectorSize(){
        return this.vertices.get(0).getVectorSize();
    }

    public int getColorSize(){
        return this.vertices.get(0).getColorSize();
    }
    public int getUVSize(){
        return this.vertices.get(0).getUVSize();
    }



    public float[] getArray(){
        float[] arr = new float[this.totalLength()];
        int inc = this.vertices.get(0).getSize();
        int i = 0;
        for(Vertex vertex : this.vertices){
            System.arraycopy(vertex.getArray(), 0, arr, i, inc);
            i += inc;
        }
        return arr;
    }







}
