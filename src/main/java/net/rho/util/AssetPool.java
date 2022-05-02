package net.rho.util;

import net.rho.components.SpriteSheet;
import net.rho.renderer.Shader;
import net.rho.renderer.Texture;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {

    private final static Map<String, Shader> shaders = new HashMap<>();
    private final static Map<String, Texture> textures = new HashMap<>();
    private final static Map<String, SpriteSheet> spriteSheets = new HashMap<>();



    public static Shader getShader(String resourceName) {
        File file = getResourceFile(resourceName);

        if (shaders.containsKey(file.getAbsolutePath())) {
            return shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = ShaderUtil.loadShaderFromFile(Paths.get(resourceName));
            shader.compile();
            shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }

    }

    public static Texture getTexture(String resourceName){
        File file = getResourceFile(resourceName);
        if (textures.containsKey(file.getAbsolutePath())){
            return textures.get(file.getAbsolutePath());
        }else{
            Texture texture = new Texture();
            texture.init(resourceName);
            textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }


    public static void addSpriteSheet(String resourceName, SpriteSheet spriteSheet) {
        File file = getResourceFile(resourceName);
        spriteSheets.putIfAbsent(file.getAbsolutePath(), spriteSheet);
    }

    public static SpriteSheet getSpriteSheet(String resourceName) {
        File file = getResourceFile(resourceName);
        if (!spriteSheets.containsKey(file.getAbsolutePath())) {
            throw new IllegalArgumentException("Can't find required spritesheet.");
        }
        // add default spritesheet
        return spriteSheets.get(file.getAbsolutePath());
    }


    private static File getResourceFile(final String fileName) {
        File url = new File(Thread.currentThread().getContextClassLoader().getResource("database.properties").getFile());


        if (url == null) {
            throw new IllegalArgumentException(fileName + " is not found 1");
        }


        return url;
    }


}