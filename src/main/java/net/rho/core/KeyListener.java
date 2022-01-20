package net.rho.core;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;


public class KeyListener {


    private static KeyListener inst = null;
    private boolean keyPressed[] = new boolean[350];


    private KeyListener() {

    }

    public static KeyListener getInstance() {
        if (inst == null) {
            inst = new KeyListener();
        }
        return inst;
    }


    public static void keyCallBack(long window, int key, int scancode, int action, int mods) {
        getInstance();
        if (action == GLFW_PRESS) {
            inst.keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            inst.keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        getInstance();
        return inst.keyPressed[keyCode];
    }


}
