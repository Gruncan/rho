package net.rho.core;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {

    private static MouseListener inst = null;
    private final boolean[] mouseButtonPressed = new boolean[3];
    private final MousePosCallBackWrapper mousePosCallBackWrapper;
    private final MouseButtonCallbackWrapper mouseButtonCallbackWrapper;
    private final MouseScrollCallBackWrapper mouseScrollCallBackWrapper;
    private double xPos, yPos, lastY, lastX, scrollX, scrollY;
    private boolean isDragging;


    private MouseListener() {
        this.xPos = 0d;
        this.yPos = 0d;
        this.lastY = 0d;
        this.lastX = 0d;
        this.scrollX = 0d;
        this.scrollY = 0d;

        this.mousePosCallBackWrapper = new MousePosCallBackWrapper();
        this.mouseButtonCallbackWrapper = new MouseButtonCallbackWrapper();
        this.mouseScrollCallBackWrapper = new MouseScrollCallBackWrapper();


    }

    public static MouseListener getInstance() {
        if (inst == null) {
            inst = new MouseListener();
        }
        return inst;
    }


    public MousePosCallBackWrapper getMousePosCallBack() {
        return this.mousePosCallBackWrapper;
    }

    public MouseButtonCallbackWrapper getMouseButtonCallBack() {
        return this.mouseButtonCallbackWrapper;
    }

    public MouseScrollCallBackWrapper getMouseScrollCallBackWrapper() {
        return this.mouseScrollCallBackWrapper;
    }


    public float getX() {
        return ((float) this.xPos);
    }

    public float getY() {
        return ((float) this.yPos);
    }

    public float getDx() {
        return ((float) (this.lastX - this.xPos));
    }

    public float getDy() {
        return ((float) (this.lastY - this.yPos));
    }

    public float getScrollX() {
        return ((float) this.scrollX);
    }

    public float getScrollY() {
        return ((float) this.scrollY);
    }

    public boolean isDragging() {
        return this.isDragging;
    }

    public boolean mouseButtonDown(int button) {
        if (button < this.mouseButtonPressed.length) {
            return this.mouseButtonPressed[button];
        } else {
            return false;
        }
    }

    public void endFrame() {
        this.scrollX = 0d;
        this.scrollY = 0d;
        this.lastX = this.xPos;
        this.lastY = this.yPos;
    }


    private class MousePosCallBackWrapper implements GLFWCursorPosCallbackI {

        @Override
        public void invoke(long window, double xpos, double ypos) {
            lastX = xPos;
            lastY = yPos;
            xPos = xpos;
            yPos = ypos;
            isDragging = mouseButtonPressed[0] || mouseButtonPressed[1] || mouseButtonPressed[2];
        }
    }


    private class MouseButtonCallbackWrapper implements GLFWMouseButtonCallbackI {

        @Override
        public void invoke(long window, int button, int action, int mods) {
            if (button >= mouseButtonPressed.length) {
                // do nothing
                assert true;
            } else if (action == GLFW_PRESS) {
                mouseButtonPressed[button] = true;
            } else if (action == GLFW_RELEASE) {
                mouseButtonPressed[button] = false;
                isDragging = false;
            }
        }
    }

    private class MouseScrollCallBackWrapper implements GLFWScrollCallbackI {

        @Override
        public void invoke(long window, double xoffset, double yoffset) {
            scrollX = xoffset;
            scrollY = yoffset;
        }
    }


}
