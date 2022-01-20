package net.rho.core;

import net.rho.renderer.Shader;
import net.rho.renderer.Texture;
import net.rho.renderer.components.ShaderHelper;
import net.rho.renderer.components.Vertex;
import net.rho.renderer.components.VertexArray;
import net.rho.util.Time;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {


//
//    private final float[] vertexArray = {
//            // position                  colour (rgb)         // UV coordinates
//            100.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,      1, 1,              // Bottom right
//            -0.5f, 100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,      0, 0,              // Top left
//            100.5f, 100.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,     1, 0,              // Top right
//            -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,       0, 1,              // Bottom left
//    };

    // Counterclockwise order
    private final int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 1, 3, // Bottom left triangle
    };


    private int vaoID, vboID, eboID;
    private Shader defaultShader;
    private Texture testTexture;
    private final VertexArray vertexArray;

    public LevelEditorScene() {
        this.vertexArray = new VertexArray();
        vertexArray.addVertex(new Vertex(100.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1, 1));
        vertexArray.addVertex(new Vertex(-0.5f, 100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0, 0));
        vertexArray.addVertex(new Vertex(100.5f, 100.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,1, 0));
        vertexArray.addVertex(new Vertex(-0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,0, 1));
    }


    @Override
    public void init() {
        super.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        this.testTexture = new Texture("assets/images/testImage.png");

        // Creating VAO, VBO, and EBO buffer objects
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.totalLength());
        vertexBuffer.put(vertexArray.getArray()).flip();

        // Create VBO upload vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        ShaderHelper.loadVertexes(vertexArray);

    }

    @Override
    public void update(float dt) {
//        camera.position.x -= dt * 50f;
//        camera.position.y -= dt * 20f;

        defaultShader.use();

        // Upload texture to shader
        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        testTexture.bind();


        defaultShader.uploadMat4f("uProjection", super.camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", super.camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());
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
