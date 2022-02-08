package net.rho.renderer;

import net.rho.components.SpriteRenderer;
import net.rho.core.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer {

    private final static int MAX_BATCH_SIZE = 1000;
    //private final Map<Integer, List<RenderBatch>> batches;
    //private RenderBatch[] availableBatches;
    private final List<RenderBatch> batches;


    public Renderer(){
//        this.batches = new HashMap<>();
//        this.availableBatches = new RenderBatch[5];
        this.batches = new ArrayList<>();
    }

    public void add(GameObject go){
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null){
            this.add(spr);
        }
    }

    // Only is better if vertexes can't be removed from render batch
    // if so then wasting space, will need changed.




    private void add(SpriteRenderer sprite) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.getZIndex() == sprite.getGameObject().getZIndex()) {
                Texture tex = sprite.getTexture();
                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.getGameObject().getZIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
            Collections.sort(batches);
        }
    }

    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }
}




//    private void add(final SpriteRenderer sprite){
//        List<RenderBatch> batches = this.batches.getOrDefault(sprite.getGameObject().getZIndex(), new ArrayList<>());
//        if (batches.isEmpty()){
//            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.getGameObject().getZIndex());
//            newBatch.start();
//
//            batches.add(newBatch);
//
//            availableBatches[sprite.getGameObject().getZIndex()] = newBatch;
//
//            this.addSpriteTexture(sprite);
//        }
//
//        if (this.availableBatch == null){
//            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.getGameObject().getZIndex());
//            newBatch.start();
//
//            this.batches.add(newBatch);
//
//            this.availableBatch = newBatch;
//
//            this.addSpriteTexture(sprite);
//
//        }else if(!this.availableBatch.hasRoom()){
//            this.availableBatch = null;
//            // create new batch
//            this.add(sprite);
//        }else{
//            this.addSpriteTexture(sprite);
//        }
//    }
//    private void addSpriteTexture(final SpriteRenderer sprite){
//        Texture texture = sprite.getTexture();
//        if (texture == null || (this.availableBatch.hasTexture(texture) || this.availableBatch.hasTextureRoom())){
//            this.availableBatch.addSprite(sprite);
//        }
//    }
//
//
//    public void render(){
//        for (RenderBatch batch : this.batches){
//            batch.render();
//        }
//    }
//}
