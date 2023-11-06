package SaveLoadSystem;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

public class MockUmlClass {

    private String name;

    private String description;

    public MockUmlClass(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public JsonObject toJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        return jsonObject;
    }

    public static MockUmlClass fromJsonObject(JsonObject jsonObject){
        String title = (String) jsonObject.get("name");
        String description = (String) jsonObject.get("description");
        return new MockUmlClass(title, description);
    }
}
