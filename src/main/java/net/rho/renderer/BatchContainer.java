package net.rho.renderer;

import net.rho.components.SpriteRenderer;

import java.util.ArrayList;
import java.util.List;

public class BatchContainer {

    private final static int MAX_BATCH_SIZE = 1000;
    private final int zIndex;
    private final List<RenderBatch> batches;
    private RenderBatch availableBatch;

    public BatchContainer(int zIndex) {
        this.zIndex = zIndex;
        this.availableBatch = null;
        this.batches = new ArrayList<>();

        // Create blank batch
        this.createNewBatch();
    }


    private void createNewBatch() {
        RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, this.zIndex);
        this.batches.add(newBatch);
        this.availableBatch = newBatch;
        this.availableBatch.start();
    }

    public RenderBatch getAvailableBatch() {
        return this.availableBatch;
    }

    public void setAvailableBatch(RenderBatch batch) {
        this.availableBatch = batch;
    }


    public void add(SpriteRenderer component) {
        if (this.availableBatch.hasRoom()) {
            Texture texture = component.getTexture();
            if (texture == null || this.availableBatch.hasTexture(texture) || this.availableBatch.hasTextureRoom()) {
                this.availableBatch.add(component);
            }
        } else {
            this.createNewBatch();
            this.add(component);
        }
    }

    public void render() {
        for (RenderBatch renderBatch : this.batches) {
            renderBatch.render();
        }
    }
}
