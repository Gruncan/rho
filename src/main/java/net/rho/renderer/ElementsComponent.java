package net.rho.renderer;

import java.util.ArrayList;
import java.util.List;

public class ElementsComponent {


    private final List<Element> elements;


    public ElementsComponent() {
        this.elements = new ArrayList<>();
    }


    public void addElement(int i1, int i2, int i3, VerticesComponent verticesComponent) {
        this.elements.add(new Element(verticesComponent.getVertex(i1), verticesComponent.getVertex(i2), verticesComponent.getVertex(i3)));
    }

    public int getBufferSize() {
        return this.elements.size() * 3;
    }


    private record VertexIndex(int i1, int i2, int i3) {

    }


}
