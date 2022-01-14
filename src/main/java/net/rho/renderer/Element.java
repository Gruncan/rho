package net.rho.renderer;

public class Element {


    private final Vertex[] triangle = new Vertex[3];


    public Element(Vertex v1, Vertex v2, Vertex v3) {
        this.triangle[0] = v1;
        this.triangle[1] = v2;
        this.triangle[2] = v3;

    }


}
