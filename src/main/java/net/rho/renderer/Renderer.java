package net.rho.renderer;

import net.rho.components.SpriteRenderer;
import net.rho.core.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final static int MAX_BATCH_SIZE = 1;
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

    private void add(SpriteRenderer sprite){
        if (this.availableBatch == null){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();

            this.batches.add(newBatch);
            newBatch.addSprite(sprite);

            this.availableBatch = newBatch;
        }else if(!this.availableBatch.hasRoom()){
            this.availableBatch = null;
            // create new batch
            this.add(sprite);
        }else{
            this.availableBatch.addSprite(sprite);
        }
    }


    public void render(){
        for (RenderBatch batch : this.batches){
            batch.render();
        }
    }

}