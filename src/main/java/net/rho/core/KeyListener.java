package net.rho.core;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener implements GLFWKeyCallbackI {


    private static KeyListener inst = null;
    private final boolean[] keyPressed = new boolean[350];


    private KeyListener() {

    }

    public static KeyListener getInstance() {
        if (inst == null) {
            inst = new KeyListener();
        }
        return inst;
    }


    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            this.keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            this.keyPressed[key] = false;
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return this.keyPressed[keyCode];
    }


}
