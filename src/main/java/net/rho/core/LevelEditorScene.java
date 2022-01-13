package net.rho.core;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {


    private final String vertexShaderSrc =
            "#version 330 core\n" +
                    "\n" +
                    "layout (location=0) in vec3 aPos;\n" +
                    "layout (location=1) in vec4 aColor;\n" +
                    "\n" +
                    "out vec4 fColor;\n" +
                    "\n" +
                    "void main(){\n" +
                    "    fColor = aColor;\n" +
                    "    gl_Position = vec4(aPos, 1.0);\n" +
                    "}";

    private final String fragmentShaderSrc =
            "#version 330 core\n" +
                    "\n" +
                    "\n" +
                    "in vec4 fColor;\n" +
                    "\n" +
                    "out vec4 color;\n" +
                    "\n" +
                    "\n" +
                    "void main(){\n" +
                    "    color = fColor;\n" +
                    "}";


    private final float[] vertexArray = {
            // position                 colour (rgb)
            0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, // Bottom right
            -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, // Top left
            0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, // Top right
            -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, // Bottom left
    };

    // Counterclockwise order
    private final int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 1, 3, // Bottom left triangle
    };


    private int vertexID, fragmentID, shaderProgram;
    private int vaoID, vboID, eboID;


    public LevelEditorScene() {

    }


    @Override
    public void init() {
        // Compile and link the shaders

        // Load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        // Pass shader source to GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        //Check for errors in compilation
        int suc = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (suc == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tVertex shader complication failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            // break out
            assert false : " ";
        }


        // load and compile fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // Pass shader source to GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        //Check for errors in compilation
        suc = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (suc == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tFragment shader complication failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            // break out
            assert false : " ";
        }


        // Link shaders and check for errors

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check errors
        suc = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (suc == GL_FALSE) {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tLinking shaders failed.");
            System.out.println(glGetProgramInfoLog(fragmentID, len));
            // break out
            assert false : " ";
        }

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
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);


    }

    @Override
    public void update(float dt) {
        // Bind shader
        glUseProgram(shaderProgram);
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

        glUseProgram(0);


    }
}
