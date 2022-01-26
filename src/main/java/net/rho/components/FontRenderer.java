package net.rho.components;

import net.rho.core.Component;

public class FontRenderer extends Component {





    @Override
    public void start() {
        if (super.gameObject.getComponent(SpriteRenderer.class) != null){
            System.out.println("Found font renderer!");
        }
    }

    @Override
    public void update(float dt) {

    }
}
