package SaveLoadSystem;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * Description: This class will contain code regarding the save/load system
 * Use Case: Used to save or load a project.
 */
public class SaveLoadSystem {

    public SaveLoadSystem(){}

    /**
    * Description: Sets the default file path to the users home directory.
    * Use Case: Is only called inside the defaultPathSave() method.
    */

    private Path getDefaultPath(String filename){
       String home = System.getProperty("user.home");
       return Paths.get(home).resolve(filename + ".json");
    }

    /**
     * Description: Saves the project under the specified filename to the default filepath (User.home).
     * Use Case: Call if the user wants to save to the default filepath.
     */
    public void defaultPathSave(String filename, ArrayList<MockUmlClass> classList){
        save(null, filename, classList, true);
    }

    /**
     * Description: Saves the project under the specified filename to a specified filepath.
     * Use Case: Call if user wants to save project into specific directory.
     */

    public void save(String path, String filename, ArrayList<MockUmlClass> classList, boolean isDefaultPath) {
        JsonArray jsonArray = new JsonArray();
        Path filePath;

        for (MockUmlClass mockClass : classList) {
            jsonArray.add(mockClass.toJsonObject());
        }

        if(isDefaultPath) {
            filePath = getDefaultPath(filename);
        } else {
            filePath = Paths.get(path).resolve(filename + ".json");
        }

        String jsonText = Jsoner.serialize(jsonArray);

        try{
            Files.write(filePath, jsonText.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Description: Loads the project from a specified filepath.
     * Use Case: Call if user wants to load a project from a specific filepath.
     */
    public ArrayList<MockUmlClass> load(Path path){
        String jsonText;
        JsonArray jsonArray;
        ArrayList<MockUmlClass> classList = new ArrayList<MockUmlClass>();

        try{
            jsonText = new String(Files.readAllBytes(path));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        try{
            jsonArray = (JsonArray) Jsoner.deserialize(jsonText);
        } catch (JsonException e) {
            throw new RuntimeException(e);
        }

        for(Object object : jsonArray){
            JsonObject jsonObject = (JsonObject) object;
            MockUmlClass umlClass = MockUmlClass.fromJsonObject(jsonObject);
            classList.add(umlClass);
        }

        return classList;
    }
}
