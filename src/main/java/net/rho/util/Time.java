package net.rho.util;

public class Time {
    public static float timeStarted = System.nanoTime();


    // Time elapsed since start, in seconds
    public static float getTime() {
        return (System.nanoTime() - timeStarted) * 1E-9f;
    }
}
