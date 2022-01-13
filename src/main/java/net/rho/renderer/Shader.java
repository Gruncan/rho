package net.rho.renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {


    private int shaderProgramID;

    private String vertexSource;
    private String fragmentSource;
    private String filepath;

    public Shader(String filepath) {
        this.filepath = filepath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, eol).trim();

            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException(String.format("Unexpected token '%s'", firstPattern));
            }

            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException(String.format("Unexpected token '%s'", secondPattern));
            }


        } catch (IOException e) {
            e.printStackTrace();
            assert false : String.format("Error: Could not open file for shader: '%s'", this.filepath);
        }

        System.out.println(vertexSource);
        System.out.println(fragmentSource);

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
        glUseProgram(shaderProgramID);
    }

    public void detach() {
        glUseProgram(0);
    }


}
