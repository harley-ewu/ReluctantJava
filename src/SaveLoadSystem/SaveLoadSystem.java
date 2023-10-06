package SaveLoadSystem;

import java.nio.file.Path;
import java.nio.file.Paths;

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
     * Description: Saves the project under the specified filename to the default filepath.
     * Use Case: Call if the user wants to save to the default filepath.
     */
    public void defaultPathSave(String filename){
        save(getDefaultPath(filename), "");
    }

    /**
     * Description: Saves the project under the specified filename to a specified filepath.
     * Use Case: Call if user wants to save project into specific directory.
     */

    //TODO: Convert list of class objects into JsonArray, pass list of class objects as argument
    public void save(Path path, String filename) {

    }

    //TODO: Create default load method

    //TODO: Create specified filepath load method
}
