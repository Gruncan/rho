package net.rho.renderer;

import net.rho.components.SpriteRenderer;
import net.rho.core.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final static int MAX_BATCH_SIZE = 1000;
    private final List<RenderBatch> batches;
    private RenderBatch availableBatch;


    public Renderer(){
        this.batches = new ArrayList<>();
        this.availableBatch = null;
    }

    public void add(GameObject go){
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null){
            this.add(spr);
        }
    }

    // Only is better if vertexes can't be removed from render batch
    // if so then wasting space, will need changed.
    private void add(final SpriteRenderer sprite){
        if (this.availableBatch == null){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();

            this.batches.add(newBatch);

            this.availableBatch = newBatch;

            this.addSpriteTexture(sprite);

        }else if(!this.availableBatch.hasRoom()){
            this.availableBatch = null;
            // create new batch
            this.add(sprite);
        }else{
            this.addSpriteTexture(sprite);
        }
    }
    private void addSpriteTexture(final SpriteRenderer sprite){
        Texture texture = sprite.getTexture();
        if (texture == null || (this.availableBatch.hasTexture(texture) || this.availableBatch.hasTextureRoom())){
            this.availableBatch.addSprite(sprite);
        }
    }


    public void render(){
        for (RenderBatch batch : this.batches){
            batch.render();
        }
    }

}