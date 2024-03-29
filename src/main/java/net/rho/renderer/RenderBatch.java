package net.rho.renderer;

import net.rho.components.SpriteRenderer;
import net.rho.core.Window;
import net.rho.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch implements Comparable<RenderBatch>, IBatch {
    // Vertex
    // ======
    // Pos               Color
    // float, float,     float, float, float, float

    private final static int POS_SIZE = 2;
    private final static int COLOR_SIZE = 4;
    private final static int TEX_COORDS_SIZE = 2;
    private final static int TEX_ID_SIZE = 1;

    private final static int POS_OFFSET = 0;
    private final static int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final static int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
    private final static int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;


    private final static int VERTEX_SIZE = 9;
    private final static int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;




    private final SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private final float[] vertices;
    private final static int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};


    private final List<Texture> textures;
    private int vaoID, vboID;
    private final int maxBatchSize;
    private final Shader shader;
    private final int zIndex;


    public RenderBatch(int maxBatchSize, int zIndex){
        this.shader = AssetPool.getShader("assets/shaders/default.glsl");

        this.sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;


        // 4 vertices quad
        this.vertices  = new float[maxBatchSize * 4 * VERTEX_SIZE];

        this.numSprites = 0;
        this.hasRoom = true;

        this.textures = new ArrayList<>();
        this.zIndex = zIndex;

    }

    @Override
    public void start(){
        // Generate and bind Vertex array object#
        this.vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);


        // Create and upload indices buffer
        int eboID = glGenBuffers();
        int[] indices = this.generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);


        // Enable the buffer attribute pointers
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
        glEnableVertexAttribArray(3);


    }

    @Override
    public void add(SpriteRenderer spr) {
        // Get index and add renderObject
        int index = this.numSprites;
        this.sprites[index] = spr;
        this.numSprites++;

        // Add to local vertices array
        if (spr.getTexture() != null && !textures.contains(spr.getTexture())) {
            textures.add(spr.getTexture());
        }

        // Add properties to local vertices array
        loadVertexProperties(index);

        if (numSprites >= this.maxBatchSize){
            this.hasRoom = false;
        }
    }

    // todo improve this
    @Override
    public void render() {
        boolean rebufferData = false;
        for (int i = 0; i < numSprites; i++) {
            SpriteRenderer spr = sprites[i];
            if (spr.isDirty()){
                loadVertexProperties(i);
                spr.setClean();
                rebufferData = true;
            }
        }

        if (rebufferData) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }
        // Use shader
        shader.use();
        shader.uploadMat4f("uProjection", Window.getCurrentScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getCurrentScene().camera().getViewMatrix());
        for (int i=0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();
        }
        shader.uploadIntArray("uTextures", texSlots);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, this.numSprites * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        for (Texture texture : textures) {
            texture.unbind();
        }
        shader.detach();
    }



    private void loadVertexProperties(int index) {
        SpriteRenderer sprite = this.sprites[index];

        // Find offset within array
        int offset = index * 4 * VERTEX_SIZE;

        Vector4f color = sprite.getColor();
        Vector2f[] texCoords = sprite.getTexCoords();

        int texId = 0;
        if (sprite.getTexture() != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i) == sprite.getTexture()) {
                    texId = i + 1;
                    break;
                }
            }
        }


        // Add vertex
        // Improve this TODO
        float xAdd = 1f;
        float yAdd = 1f;
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                yAdd = 0;
            } else if (i == 2) {
                xAdd = 0;
            } else if (i == 3) {
                yAdd = 1;
            }

            // Load position
            vertices[offset] = sprite.getGameObject().getXPos() + (xAdd * sprite.getGameObject().getXScale());
            vertices[offset + 1] = sprite.getGameObject().getYPos() + (yAdd * sprite.getGameObject().getYScale());

            // Load color
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            // Load texture coordinates
            vertices[offset + 6] = texCoords[i].x;
            vertices[offset + 7] = texCoords[i].y;

            // Load texture id
            vertices[offset + 8] = texId;


            offset += VERTEX_SIZE;
        }
    }



    private int[] generateIndices() {
        // 6 indices per quad (3 per triangle)
        int[] elements = new int[6 * this.maxBatchSize];
        for (int i = 0; i < this.maxBatchSize; i++) {
            this.loadElementIndices(elements, i);
        }

        return elements;

    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        // 3, 2, 0, 0, 2, 1         7, 6, 4, 4, 6, 5
        // Triangle 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        // Triangle 2
        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;

    }

    public boolean hasRoom(){
        return this.hasRoom;
    }


    public boolean hasTextureRoom(){
        return this.textures.size() < 8;
    }

    public boolean hasTexture(Texture texture){
        return this.textures.contains(texture);
    }

    public int getZIndex(){
        return this.zIndex;
    }

    @Override
    public int compareTo(RenderBatch o) {
        return Integer.compare(this.zIndex, o.zIndex);
    }
}
