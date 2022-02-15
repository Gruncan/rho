package net.rho.components;

public class FontRenderer extends AbstractComponent {


    @Override
    public void start() {
        if (super.gameObject.getComponent(SpriteRenderer.class) != null) {
            System.out.println("Found font renderer!");
        }
    }

    @Override
    public void update(float dt) {

    }
}
