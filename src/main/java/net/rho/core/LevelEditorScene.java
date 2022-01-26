package net.rho.core;

import net.rho.components.FontRenderer;
import net.rho.components.SpriteRenderer;
import net.rho.renderer.Shader;
import net.rho.renderer.Texture;
import net.rho.util.Time;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {



    private final float[] vertexArray = {
            // position                  colour (rgb)
            100.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,      1, 1,              // Bottom right
            -0.5f, 100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,      0, 0,              // Top left
            100.5f, 100.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,     1, 0,              // Top right
            -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,       0, 1,              // Bottom left
    };

    // Counterclockwise order
    private final int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 1, 3, // Bottom left triangle
    };

    private boolean firstTime = true;

    private int vaoID, vboID, eboID;
    private Shader defaultShader;
    private Texture testTexture;
    GameObject testObj;

    public LevelEditorScene() {

    }


    @Override
    public void init() {
        System.out.println("Creating 'Test object'");
        this.testObj = new GameObject("Test object");
        this.testObj.addComponent(new SpriteRenderer());
        this.testObj.addComponent(new FontRenderer());
        this.addGameObjectToScene(this.testObj);


        super.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        this.testTexture = new Texture("assets/images/testImage.png");

        // Creating VAO, VBO, and EBO buffer objects
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

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

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2,uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);

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

        if (this.firstTime) {
            System.out.println("Creating game object");
            GameObject gameObject = new GameObject("Game Test 2");
            gameObject.addComponent(new SpriteRenderer());
            this.addGameObjectToScene(gameObject);
            this.firstTime = false;
        }

        for (GameObject gameObject : super.gameObjects) {
            gameObject.update(dt);
        }


    }
}
