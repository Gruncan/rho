package net.rho.core;

import net.rho.core.gui.ImGuiLayer;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {


    private static Window inst = null;
    private static Scene currentScene = null;
    private int width, height;
    private String title;
    protected float r, g, b;
    private long glfwWindow = 0;
    private final Map<Integer, Scene> sceneMap;
    private ImGuiLayer imGuiLayer = null;

    private boolean imGuiEnable = false;


    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Title";
        this.r = 0f;
        this.g = 0f;
        this.b = 0f;
        this.sceneMap = new HashMap<>();
    }




    public void setWindow(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
    }


    public void changeScene(int newScene) {
        Scene scene = this.sceneMap.get(newScene);
        if(scene == null) throw new IllegalArgumentException(String.format("Unknown scene '%d'\nTo add a scene use Window.addScene(int n, Scene scene)", newScene));
        else if (scene == currentScene) return;
        else
            currentScene = scene;

        currentScene.init();
        currentScene.start();
    }


    public void addScene(int n, Scene scene){
        this.sceneMap.put(n, scene);
    }

    public static Window getInstance() {
        if (inst == null) {
            inst = new Window();
        }
        return inst;
    }


    public void setRGB(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void run() {
        System.out.printf("Running LWJGL version %s!%n", Version.getVersion());


        init();
        loop();

        // Free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void close() {
        glfwDestroyWindow(glfwWindow);
    }

    private void init() {
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
        //glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);


        // Create the window (Memory address long)
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);

        if (glfwWindow == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener.getInstance().getMousePosCallBack());
        glfwSetMouseButtonCallback(glfwWindow, MouseListener.getInstance().getMouseButtonCallBack());
        glfwSetScrollCallback(glfwWindow, MouseListener.getInstance().getMouseScrollCallBackWrapper());
        glfwSetKeyCallback(glfwWindow, KeyListener.getInstance());
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) ->{
            this.width = newWidth;
            this.height = newHeight;
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);


        //Make the window visible
        glfwShowWindow(glfwWindow);


        // Ensures we can use the bindings, IMPORTANT!!!
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        if (this.imGuiEnable) {
            this.imGuiLayer = new ImGuiLayer(glfwWindow);
            this.imGuiLayer.initImGui();
        }

        this.changeScene(0);


    }

    private void loop() {
        float beginTime = ((float) glfwGetTime());
        float endTime;
        float dt = -1f;


        //currentScene.load();


        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            glClearColor(this.r, this.g, this.b, 1f);
            glClear(GL_COLOR_BUFFER_BIT);


            if (dt >= 0)
                currentScene.update(dt);

            if (this.imGuiLayer != null)
                this.imGuiLayer.update(dt, currentScene);


            glfwSwapBuffers(glfwWindow);

            endTime = ((float) glfwGetTime());
            dt = endTime - beginTime;
            beginTime = endTime;
        }
        currentScene.saveExit();
    }

    public static Scene getCurrentScene(){
        return currentScene;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

}
