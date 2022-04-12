package net.rho.core;

import com.google.gson.*;
import net.rho.components.AbstractComponent;

import java.lang.reflect.Type;

public class GameObjectDeserializer implements JsonDeserializer<GameObject> {


    @Override
    public GameObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        JsonArray components = jsonObject.getAsJsonArray("components");
        Transform transform = context.deserialize(jsonObject.get("transform"), Transform.class);
        int zIndex = context.deserialize(jsonObject.get("zIndex"), int.class);
        GameObject go = new GameObject(name, transform, zIndex);
        for (JsonElement e : components) {
            AbstractComponent c = context.deserialize(e, AbstractComponent.class);
            go.addComponent(c);
        }
        return go;

    }

}
