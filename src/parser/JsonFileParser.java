package parser;

import java.io.FileReader;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import composition.Container;
import factory.Factory;
import icon.IconFactory;

public class JsonFileParser {
    public Container parse(String filePath, Factory factory, IconFactory iconFactory) {
        try {
            FileReader reader = new FileReader(filePath);
            JsonElement jsonElement = new JsonParser().parse(reader);
            return parseElement(jsonElement, factory, iconFactory, "", 0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Container parseElement(JsonElement jsonElement, Factory factory, IconFactory iconFactory, String name, int level) {
        if (jsonElement.isJsonObject()) {
            return parseObject(jsonElement.getAsJsonObject(), factory, iconFactory, name, level);
        } else if (jsonElement.isJsonArray()) {
            return parseArray(jsonElement.getAsJsonArray(), factory, iconFactory, name, level);
        } else if (jsonElement.isJsonNull()) {
            return factory.createContainer(name, level, iconFactory);
        } else {
            return factory.createContainer(name + ": " + jsonElement.getAsString(), level, iconFactory);
        }
    }

    private Container parseObject(JsonObject jsonObject, Factory factory, IconFactory iconFactory, String name, int level) {
        Container container = factory.createContainer(name, level, iconFactory);
        for (String key : jsonObject.keySet()) {
            JsonElement element = jsonObject.get(key);
            container.addLeaf(parseElement(element, factory, iconFactory, key, level + 1));
        }
        return container;
    }

    private Container parseArray(JsonArray jsonArray, Factory factory, IconFactory iconFactory, String name, int level) {
        Container container = factory.createContainer(name, level, iconFactory);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement element = jsonArray.get(i);
            container.addLeaf(parseElement(element, factory, iconFactory, "[" + i + "]", level + 1));
        }
        return container;
    }
}
