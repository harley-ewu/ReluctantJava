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

    public void saveDefault(String filename, ArrayList<MockUmlClass> classList){

        if(filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("The filename cannot be null or empty.");
        }

        if(classList == null) {
            throw new IllegalArgumentException("The list of classes must not be null.");
        }

        JsonArray jsonArray = new JsonArray();
        Path filePath;

        fillJsonArray(classList, jsonArray);

        filePath = getDefaultPath(filename);

        writeToFile(filePath, jsonArray);
    }

    /**
     * Description: Saves the project under the specified filename to a specified filepath.
     * Use Case: Call if user wants to save project into specific directory.
     */

    public void saveCustom(String path, String filename, ArrayList<MockUmlClass> classList) {

        if(path == null) {
            throw new IllegalArgumentException("The file path is null.");
        }

        if(filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("The filename cannot be null or empty.");
        }

        if(classList == null) {
            throw new IllegalArgumentException("The list of classes must not be null.");
        }

        JsonArray jsonArray = new JsonArray();
        Path filePath;

        fillJsonArray(classList, jsonArray);

        filePath = Paths.get(path).resolve(filename + ".json");

        writeToFile(filePath, jsonArray);

    }

    /**
     * Description: Loads the project from a specified filepath.
     * Use Case: Call if user wants to load a project from a specific filepath.
     */
    public ArrayList<MockUmlClass> load(String path){

        if(path == null || path.isEmpty()){
            throw new IllegalArgumentException("The file path cannot be null or empty.");
        }

        ArrayList<MockUmlClass> classList = new ArrayList<MockUmlClass>();
        Path filepath = Paths.get(path);

        String jsonText = convertJsonTextToString(filepath);

        JsonArray jsonArray = convertStringToJsonArray(jsonText);

        loadUmlClassesIntoArrayList(jsonArray, classList);

        return classList;
    }

    /**
     * Description: Sets the default file path to the users home directory.
     * Use Case: Is only called inside the defaultPathSave() method.
     */

    private Path getDefaultPath(String filename){
        String home = System.getProperty("user.home");
        return Paths.get(home).resolve(filename + ".json");
    }

    private void fillJsonArray(ArrayList<MockUmlClass> classList, JsonArray array){
        for (MockUmlClass mockClass : classList) {
            array.add(mockClass.toJsonObject());
        }
    }

    private void writeToFile(Path filePath, JsonArray array) {
        String jsonText = Jsoner.serialize(array);

        try {
            Files.write(filePath, jsonText.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertJsonTextToString(Path filepath){
        String text = null;

        try{
            text = new String(Files.readAllBytes(filepath));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return text;
    }

    private JsonArray convertStringToJsonArray(String jsonText){
        JsonArray array = null;

        try{
            array = (JsonArray) Jsoner.deserialize(jsonText);
        } catch (JsonException e) {
            throw new RuntimeException(e);
        }

        return array;
    }

    private void loadUmlClassesIntoArrayList(JsonArray jsonArray, ArrayList<MockUmlClass> classList){
        for(Object object : jsonArray){
            JsonObject jsonObject = (JsonObject) object;
            MockUmlClass umlClass = MockUmlClass.fromJsonObject(jsonObject);
            classList.add(umlClass);
        }
    }


}
