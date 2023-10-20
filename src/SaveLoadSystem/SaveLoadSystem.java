package SaveLoadSystem;

import Diagram.Diagram;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Description: This class will contain code regarding the save/load system
 * Use Case: Used to save or load a diagram.
 */

public class SaveLoadSystem {

    public SaveLoadSystem(){}

    public static void saveDefaultCLI(String fileName, Diagram diagram){

        if(fileName == null || fileName.isEmpty()){
            throw new IllegalArgumentException("The file name cannot be null or empty");
        }

        String homeFolder = System.getProperty("user.home");
        Path path = Paths.get(homeFolder).resolve(fileName + ".json");
        File fileToBeSaved = new File(path.toString());

        ConvertDiagramToJsonAndWriteToFile(diagram, fileToBeSaved);
    }

    public static void saveCustomCLI(String path, String fileName, Diagram diagram){

        nullCheckPathAndFilename(path, fileName);

        Path filePath = Paths.get(path).resolve(fileName + ".json");
        File fileToBeSaved = new File(filePath.toString());

        ConvertDiagramToJsonAndWriteToFile(diagram, fileToBeSaved);
    }

    public static Diagram loadDiagramCLI(String path, String fileName){

        nullCheckPathAndFilename(path, fileName);

        Path filePath = Paths.get(path).resolve(fileName + ".json");
        File fileToBeLoaded = new File(filePath.toString());
        Diagram diagram = null;

        if(fileToBeLoaded.exists()){
            try{
                FileReader fileReader = new FileReader(fileToBeLoaded);
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                diagram = gson.fromJson(fileReader, Diagram.class);
                fileReader.close();
                return diagram;
            } catch (FileNotFoundException e) {
                System.err.println("There was an error trying to find the file.");
            } catch (IOException e) {
                System.err.println("There was an error opening or closing the file.");
            }
        }

        System.out.println("The file could not be found or does not exist.");
        System.out.println("Try checking the file path and file name for typos.");

        return diagram;
    }

    private static void ConvertDiagramToJsonAndWriteToFile(Diagram diagram, File fileToBeSaved) {
        try{
            FileWriter fileWriter = new FileWriter(fileToBeSaved);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String jsonText = gson.toJson(diagram);
            fileWriter.write(jsonText);
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("There was an error writing to the file");
        }
    }

    private static void nullCheckPathAndFilename(String path, String fileName){
        if(path == null || path.isEmpty()){
            throw new IllegalArgumentException("The file path cannot be null or empty.");
        }

        if(fileName == null || fileName.isEmpty()){
            throw new IllegalArgumentException("The file name cannot be null or empty.");
        }
    }
}

