package net.rho.core;

import net.rho.renderer.ElementsComponent;
import net.rho.renderer.Shader;
import net.rho.renderer.VerticesComponent;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorSceneNew extends Scene {


    private final VerticesComponent verticesComponent;
    private final ElementsComponent elementsComponent;

    // Counterclockwise order
//    private final int[] elementArray = {
//            2, 1, 0, // Top right triangle
//            0, 1, 3, // Bottom left triangle
//    };


//    private final float[] vertexArray = {
//            // position                  colour (rgb)
//            0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, // Bottom right
//            -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, // Top left
//            0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, // Top right
//            -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, // Bottom left
//    };


    private int vaoID, vboID, eboID;
    private Shader defaultShader;

    public LevelEditorSceneNew() {
        this.verticesComponent = new VerticesComponent();
        this.verticesComponent.addVertex(0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f);
        this.verticesComponent.addVertex(-0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f);
        this.verticesComponent.addVertex(0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f);
        this.verticesComponent.addVertex(-0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f);

        this.elementsComponent = new ElementsComponent();

        this.elementsComponent.addElement(2, 1, 0, this.verticesComponent);
        this.elementsComponent.addElement(this.verticesComponent.getVertex(0), this.verticesComponent.getVertex(1), this.verticesComponent.getVertex(3));


    }


    @Override
    public void init() {
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();

        // Creating VAO, VBO, and EBO buffer objects
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(this.verticesComponent.getBufferSize());
        vertexBuffer.put(this.verticesComponent.getArray()).flip();

        // Create VBO upload vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(this.elementsComponent.getBufferSize());
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);


    }

    @Override
    public void update(float dt) {

        defaultShader.use();
        // Bind VAO
        glBindVertexArray(vaoID);
        //Enable vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);


        //Unbind
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultShader.detach();


    }
}
