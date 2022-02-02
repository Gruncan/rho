package net.rho.core;

import net.rho.util.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {


    private static Window inst = null;
    private static Scene currentScene = null;
    private final int width, height;
    private final String title;
    protected float r, g, b;
    private long glfwWindow = 0;
    private boolean fadeToBlack = false;


    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Title";
        this.r = 1f;
        this.g = 1f;
        this.b = 1f;

    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : String.format("Unknown scene '%d'", newScene);
                break;
        }
    }

    public static Window getInstance() {
        if (inst == null) {
            inst = new Window();
        }
        return inst;
    }

    public void decreaseRGB(float v1, float v2, float v3) {
        this.r -= r;
        this.g -= g;
        this.b -= b;
    }

    public void setRGB(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void run() {
        System.out.printf("Hello LWJGL %s!%n", Version.getVersion());

        init();
        loop();

        // Free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);


        // Create the window (Memory address long)
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);

        if (glfwWindow == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener.getInstance().getMousePosCallBack());
        glfwSetMouseButtonCallback(glfwWindow, MouseListener.getInstance().getMouseButtonCallBack());
        glfwSetScrollCallback(glfwWindow, MouseListener.getInstance().getMouseScrollCallBackWrapper());
        glfwSetKeyCallback(glfwWindow, KeyListener.getInstance());

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);


        //Make the window visable
        glfwShowWindow(glfwWindow);


        // Ensures we can use the bindings, IMPORTANT!!!
        GL.createCapabilities();


        Window.changeScene(0);


    }

    public void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            glClearColor(this.r, this.g, this.b, 1f);
            glClear(GL_COLOR_BUFFER_BIT);


            if (dt >= 0)
                currentScene.update(dt);


            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }

    }

    public static Scene getCurrentScene(){
        return currentScene;
    }


}
