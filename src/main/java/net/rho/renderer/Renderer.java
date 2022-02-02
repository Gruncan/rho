package net.rho.renderer;

import net.rho.components.SpriteRenderer;
import net.rho.core.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final static int MAX_BATCH_SIZE = 1000;
    private final List<RenderBatch> batches;


    public Renderer(){
        this.batches = new ArrayList<>();
    }

    public void add(GameObject go){
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null){
            add(spr);
        }
    }

    private void add(SpriteRenderer sprite){
        boolean added = false;
        for (RenderBatch renderBatch : this.batches) {
            if (renderBatch.hasRoom()){
                renderBatch.addSprite(sprite);
                added = true;
                break;
            }
        }


        if (!added){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            this.batches.add(newBatch);
            newBatch.addSprite(sprite);
        }
    }


    public void render(){
        for (RenderBatch batch : this.batches){
            batch.render();
        }
    }

}