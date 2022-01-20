package net.rho.renderer.components;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class ShaderHelper {




    public static void loadVertexes(VertexArray vertexArray){

        int vertexSizeBytes = (vertexArray.getVectorSize() + vertexArray.getColorSize() + vertexArray.getUVSize()) * Float.BYTES;
        glVertexAttribPointer(0, vertexArray.getVectorSize(), GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, vertexArray.getColorSize(), GL_FLOAT, false, vertexSizeBytes, (long) vertexArray.getVectorSize() * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, vertexArray.getUVSize(), GL_FLOAT, false, vertexSizeBytes, (long) (vertexArray.getVectorSize() + vertexArray.getColorSize()) * Float.BYTES);
        glEnableVertexAttribArray(2);



    }






}
