package net.rho.renderer;

import net.rho.components.SpriteRenderer;
import net.rho.core.GameObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Renderer {

    private final Map<Integer, BatchContainer> batches;


    public Renderer(){
        this.batches = new HashMap<>();
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

        Integer z = sprite.getGameObject().getZIndex();
        BatchContainer batchContainer = this.batches.get(z);

        if (batchContainer == null) {
            BatchContainer newContainer = new BatchContainer(z);
            this.batches.put(z, newContainer);
            newContainer.add(sprite);

        } else {
            batchContainer.add(sprite);

        }

    }


    public void render() {
        TreeSet<Integer> zIndexes = new TreeSet<>(this.batches.keySet());
        for (Integer i : zIndexes) {
            BatchContainer batchContainer = this.batches.get(i);
            batchContainer.render();
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
