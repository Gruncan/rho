package net.rho.renderer;

import net.rho.components.SpriteRenderer;

public interface IBatch {


    void render();

    //void add(IComponent component);

    void add(SpriteRenderer spriteRenderer);

    void start();
}
