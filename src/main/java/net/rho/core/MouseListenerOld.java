package net.rho.core;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;


/**
 * Old version of MouseListener,
 * removed weird static usage in singleton
 */
@Deprecated
public class MouseListenerOld {

    private static MouseListenerOld inst = null;
    private double xPos, yPos, lastY, lastX, scrollX, scrollY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;


    private MouseListenerOld() {
        this.xPos = 0d;
        this.yPos = 0d;
        this.lastY = 0d;
        this.lastX = 0d;
        this.scrollX = 0d;
        this.scrollY = 0d;

    }

    public static MouseListenerOld getInstance() {
        if (inst == null) {
            inst = new MouseListenerOld();
        }
        return inst;
    }


    public static void mousePosCallback(long window, double xPos, double yPos) {
        getInstance();
        inst.lastX = inst.xPos;
        inst.lastY = inst.yPos;
        inst.xPos = xPos;
        inst.yPos = yPos;
        inst.isDragging = inst.mouseButtonPressed[0] || inst.mouseButtonPressed[1] || inst.mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        getInstance();
        if (button >= inst.mouseButtonPressed.length) {
            // do nothing
            assert true;
        } else if (action == GLFW_PRESS) {
            inst.mouseButtonPressed[button] = true;
        } else if (action == GLFW_RELEASE) {
            inst.mouseButtonPressed[button] = false;
            inst.isDragging = false;
        }
    }

    public static void mouseScrollCallBack(long window, double xOffSet, double yOffSet) {
        getInstance();
        inst.scrollX = xOffSet;
        inst.scrollY = yOffSet;
    }

    public static void endFrame() {
        getInstance();
        inst.scrollX = 0d;
        inst.scrollY = 0d;
        inst.lastX = inst.xPos;
        inst.lastY = inst.yPos;
    }

    public static float getX() {
        getInstance();
        return ((float) inst.xPos);
    }

    public static float getY() {
        getInstance();
        return ((float) inst.yPos);
    }

    public static float getDx() {
        getInstance();
        return ((float) (inst.lastX - inst.xPos));
    }

    public static float getDy() {
        getInstance();
        return ((float) (inst.lastY - inst.yPos));
    }

    public static float getScrollX() {
        getInstance();
        return ((float) inst.scrollX);
    }

    public static float getScrollY() {
        getInstance();
        return ((float) inst.scrollY);
    }

    public static boolean isDragging() {
        getInstance();
        return inst.isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        getInstance();
        if (button < inst.mouseButtonPressed.length) {
            return inst.mouseButtonPressed[button];
        } else {
            return false;
        }
    }


}

