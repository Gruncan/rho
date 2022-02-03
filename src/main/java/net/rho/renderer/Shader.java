package net.rho.renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {


    private int shaderProgramID;
    private boolean beingUsed = false;

    private final String vertexSource;
    private final String fragmentSource;
    private final String filepath;

    public Shader(String fragmentSource, String vertexSource, String filepath) {
        this.filepath = filepath;
        this.vertexSource = vertexSource;
        this.fragmentSource = fragmentSource;

    }


    public void compile() {
        int vertexID, fragmentID;
        // Load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        // Pass shader source to GPU
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        //Check for errors in compilation
        int suc = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (suc == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.printf("ERROR: '%s'\n\tVertex shader complication failed.%n", this.filepath);
            System.out.println(glGetShaderInfoLog(vertexID, len));
            // break out
            assert false : " ";
        }


        // load and compile fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // Pass shader source to GPU
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        //Check for errors in compilation
        suc = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (suc == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.printf("ERROR: '%s'\n\tFragment shader complication failed.%n", this.filepath);
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            // break out
            assert false : " ";
        }

        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        // Check errors
        suc = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (suc == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.printf("ERROR: '%s'\n\tLinking shaders failed.%n", this.filepath);
            System.out.println(glGetProgramInfoLog(fragmentID, len));
            // break out
            assert false : " ";
        }

    }

    public void use() {
        if (!this.beingUsed) {
            // Bind shader program
            glUseProgram(shaderProgramID);
            this.beingUsed = true;
        }
    }

    public void detach() {
        glUseProgram(0);
        this.beingUsed = false;
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadMat3f(String varName, Matrix3f mat3) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matBuffer);
        glUniformMatrix3fv(varLocation, false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vec) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
    }

    public void uploadVec3f(String varName, Vector3f vec) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform3f(varLocation, vec.x, vec.y, vec.z);
    }

    public void uploadVec2f(String varName, Vector2f vec) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform2f(varLocation, vec.x, vec.y);
    }

    public void uploadFloat(String varName, float val) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1f(varLocation, val);
    }

    public void uploadInt(String varName, int val) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation, val);
    }

    public void uploadTexture(String varName, int slot){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation, slot);
    }

    public void uploadIntArray(String varName, int[] array) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1iv(varLocation, array);
    }

}
