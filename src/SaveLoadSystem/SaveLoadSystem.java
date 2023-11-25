package SaveLoadSystem;

import Diagram.Diagram;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Description: This class will contain code regarding the save/load system
 * Use Case: Used to save or load a diagram.
 */

public class SaveLoadSystem {

    public SaveLoadSystem(){}

    /**
     * Description: Saves a project to the default file directory which is the users home directory.
     * Use case: Call this method when the user wants to save to the default file path.
     * @param fileName: The name of the project to be saved.
     * @param diagram: The diagram object of the project.
     */
    public static void saveDefaultCLI(String fileName, Diagram diagram){

        if(fileName == null || fileName.isEmpty()){
            throw new IllegalArgumentException("The file name cannot be null or empty");
        }

        String homeFolder = System.getProperty("user.home");
        Path path = Paths.get(homeFolder).resolve(fileName + ".json");
        File fileToBeSaved = new File(path.toString());

        ConvertDiagramToJsonAndWriteToFile(diagram, fileToBeSaved);
    }

    /**
     * Description: Saves a project to a file path specified by the user.
     * Use case: Call if the user wants to save the project to a specified file path.
     * @param path: The folder or directory that the user wishes to save to.
     * @param fileName: The name of the project to be saved.
     * @param diagram: The diagram object of the project.
     */
    public static void saveCustomCLI(String path, String fileName, Diagram diagram){

        nullCheckPathAndFilename(path, fileName);

        Path filePath = Paths.get(path).resolve(fileName + ".json");
        File fileToBeSaved = new File(filePath.toString());

        ConvertDiagramToJsonAndWriteToFile(diagram, fileToBeSaved);
    }

    /**
     * Description: Loads a project from a specified file path.
     * Use case: Call if the user wants to load an existing project.
     * @param path: The file path to the existing project.
     * @param fileName: The name of the existing project.
     * @return - Returns a Diagram object of an existing project on the disk.
     */
    public static Diagram loadDiagramCLI(String path, String fileName){

        nullCheckPathAndFilename(path, fileName);
        try {
            Path filePath = Paths.get(path).resolve(fileName + ".json");
            File fileToBeLoaded = new File(filePath.toString());
            return loadSavedJsonTextAndConvertToDiagramObject(fileToBeLoaded);
        } catch (InvalidPathException e) {
            return null;
        }
    }

    /**
     * Description: This method is called inside the MenuBarController
     * in order to save a project starting from the users home directory.
     * It saves the project once the user has selected a directory.
     * @param diagram - The diagram object to be saved.
     * @param file - The file path to where the project will be saved.
     */
    public static void saveProjectGUI(Diagram diagram, File file){
        ConvertDiagramToJsonAndWriteToFile(diagram, file);
    }

    public static Diagram loadProjectGUI(File file){
        return loadSavedJsonTextAndConvertToDiagramObject(file);
    }

    /**
     * Description: Converts a diagram object into JSON and then saves it to a file.
     * Use case: DO NOT USE! THIS IS A HELPER METHOD.
     * @param diagram: The Diagram object to be converted to JSON.
     * @param fileToBeSaved: The File object that will be saved.
     */
    private static void ConvertDiagramToJsonAndWriteToFile(Diagram diagram, File fileToBeSaved) {
        try{
            FileWriter fileWriter = new FileWriter(fileToBeSaved);
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            String jsonText = gson.toJson(diagram);
            fileWriter.write(jsonText);
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("There was an error writing to the file");
        }
    }

    /**
     * Description: Loads a JSON file of a project and converts it back into a Diagram object.
     * Use case: DO NOT USE! THIS IS A HELPER METHOD.
     * @param fileToBeLoaded: The File object where the existing project is stored.
     * @return - Returns a Diagram object of an existing project on the disk.
     */
    private static Diagram loadSavedJsonTextAndConvertToDiagramObject(File fileToBeLoaded){

        Diagram diagram;

        if(fileToBeLoaded != null && fileToBeLoaded.exists()){
            try{
                FileReader fileReader = new FileReader(fileToBeLoaded);
                Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
                diagram = gson.fromJson(fileReader, Diagram.class);
                fileReader.close();
                return diagram;
            } catch (FileNotFoundException e) {
                System.err.println("There was an error trying to find the file.");
                System.err.println("Try checking the file path and file name for typos.");
            } catch (IOException e) {
                System.err.println("There was an error opening or closing the file.");
            }
        }
        return null;
    }

    /**
     * Description: Used to simplify the parameter checking inside the saveCustomCLI and loadDiagramCLI methods.
     * Use case: DO NOT USE! THIS IS A HELPER METHOD.
     * @param path: The file path where the project will be saved to / loaded from.
     * @param fileName: The name of the project.
     */
    private static void nullCheckPathAndFilename(String path, String fileName){
        if(path == null || path.isEmpty()){
            throw new IllegalArgumentException("The file path cannot be null or empty.");
        }

        if(fileName == null || fileName.isEmpty()){
            throw new IllegalArgumentException("The file name cannot be null or empty.");
        }
    }
}

