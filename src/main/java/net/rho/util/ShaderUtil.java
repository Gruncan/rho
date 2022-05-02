package net.rho.util;

import net.rho.renderer.Shader;
import net.rho.renderer.components.VertexArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class ShaderUtil {




    public static void loadVertexes(VertexArray vertexArray){

        int vertexSizeBytes = (vertexArray.getVectorSize() + vertexArray.getColorSize() + vertexArray.getUVSize()) * Float.BYTES;
        glVertexAttribPointer(0, vertexArray.getVectorSize(), GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, vertexArray.getColorSize(), GL_FLOAT, false, vertexSizeBytes, (long) vertexArray.getVectorSize() * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, vertexArray.getUVSize(), GL_FLOAT, false, vertexSizeBytes, (long) (vertexArray.getVectorSize() + vertexArray.getColorSize()) * Float.BYTES);
        glEnableVertexAttribArray(2);

    }


    public static Shader loadShaderFromFile(Path path){

        String vertexSource = null;
        String fragmentSource = null;


        try {
            String source = new String(Files.readAllBytes(path));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            int index = source.indexOf("#type") + 6;
            String endLine = "\n";
            if (System.getProperty("os.name").startsWith("Windows")) {
                endLine = "\r\n";
            } else if (System.getProperty("os.name").equals("Linux")) {
                endLine = "\r\n";
            }
            int eol = source.indexOf(endLine, index);
            String firstPattern = source.substring(index, eol).trim();

            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf(endLine, index);
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

            if (vertexSource == null || fragmentSource == null) throw new IOException(String.format("Unable to load '%s'", path));


        } catch (IOException e) {
            e.printStackTrace();
            assert false : String.format("Error: Could not open file for shader: '%s'", path);
        }


        return new Shader(fragmentSource, vertexSource, path.toString());

    }
}
