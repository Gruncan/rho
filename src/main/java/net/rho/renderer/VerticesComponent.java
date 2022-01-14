package net.rho.renderer;

import java.util.ArrayList;
import java.util.List;

public class VerticesComponent {

    private final List<Vertex> vertices;


    public VerticesComponent() {
        this.vertices = new ArrayList<>();
    }


    public VerticesComponent addVertex(Vertex vertex) {
        this.vertices.add(vertex);
        return this;
    }


    public VerticesComponent addVertex(float xPos, float yPos, float zPos, Vertex.Colour colour) {
        return this.addVertex(new Vertex(xPos, yPos, zPos, colour));
    }

    public VerticesComponent addVertex(float xPos, float yPos, float zPos, float r, float g, float b, float a) {
        return this.addVertex(xPos, yPos, zPos, new Vertex.Colour(r, g, b, a));
    }

    public Vertex getVertex(int i) {
        return this.vertices.get(i);
    }


    public int getBufferSize() {
        return this.vertices.size() * 7;
    }


    public float[] getArray() {
        final float[] arr = new float[getBufferSize()];

        int pos = 0;
        for (Vertex vertex : this.vertices) {
            for (float v : vertex.getArray()) {
                arr[pos] = v;
                pos += 1;
            }
        }
        return arr;
    }


}
