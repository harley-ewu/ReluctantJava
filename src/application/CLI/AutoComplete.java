package application.CLI;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import Class.Class;
import Attributes.Field;
import Attributes.Method;

public class AutoComplete {

    private Terminal terminal;
    private LineReader lineReader;

    public AutoComplete() {
        try {
            this.terminal = TerminalBuilder.builder()
                            .dumb(false)
                            .jna(true)
                            .build();
        } catch(Exception e) {
            System.out.println("You tryna crash the program??"); }
    }

    /**
    * Constructor for LineReader that reads class actions from standard input
    */
    public void classLineReader(){
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add-class", "delete-class", "rename-class", "edit-class", "view-class", "view-diagram", "save", "help", "back")))
                    .build();
    }

    /**
    * Sets the LineReader used to read actions for main menu from the command line. 
    */
    public void mainLineReader() {
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("new-diagram", "view-diagram", "edit-current-diagram", "save", "load-existing-diagram", "help", "gui", "exit")))
                    .build();
    }

    /**
    * Creates a new line reader for new class actions.
    */
    public void newClassLineReader(){
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add-attribute", "add-relationship", "save", "back", "help")))
                    .build();
    }

    /**
    * Constructor for LineReader for editClass
    */
    public void editClassLineReader() {
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add-attribute", "delete-field", "delete-method", "rename-attribute", "display-attributes", "add-relationship", "delete-relationship", "edit-relationships", "display-relationships", "display-all", "add-class", "save", "back", "help")))
                    .build();
    }

    /**
    * Line reader for relationship editor.
    */
    public void relationshipEditorLineReader() {
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add-relationship", "delete-relationship", "view-relationships", "save", "back", "exit")))
                    .build();
    }

    public void listClassLineReader(final HashMap<String, Class> classList) {
        List<String> stringList = new ArrayList<String>();
        for (Map.Entry<String, Class> classEntity : classList.entrySet()) {
            String className = classEntity.getKey();
            stringList.add(className);
        }
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(stringList))
                    .build();
        
    }

    public void listFieldsLineReader(final ArrayList<Field> fieldList) {
        List<String> stringList = new ArrayList<String>();
        for(int i = 0; i < fieldList.size(); i++){
            String fieldName = fieldList.get(0).getName();
            stringList.add(fieldName);
        }
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(stringList))
                    .build();
    }

    public void listMethodsLineReader(final ArrayList<Method> methodList) {
        List<String> stringList = new ArrayList<String>();
        for(int i = 0; i < methodList.size(); i++){
            String fieldName = methodList.get(0).getName();
            stringList.add(fieldName);
        }
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(stringList))
                    .build();
    }

    /**
    * Gets commands from JLine and prompts user for input. This is used to create command and entity to edit
    * 
    * 
    * @return String of commands or
    */
    public String getCommands(){
        String line = "";
        try {

            // Main loop to read input
            while (true) {
                line = this.lineReader.readLine();

                if (line.isEmpty()) {
                    break; // User pressed Enter
                }
                return line ;
            }
        } catch (Exception e) {
            System.out.println("\nYou tryna crash the program??");}
        return line;
    }

    /**
    * Checks if the input is a number. This is used to determine if the user is trying to enter a number
    * 
    * @param input - the string to check for a number
    * 
    * @return true if the input is a number false if not or if the input is empty or not a number
    */
    public boolean isNumber(final String input) {
        if(input.isEmpty()){
            return false;
        }
        if(Character.isDigit(input.charAt(0))){
            return true;
        }
        return false;
    }

    public static class SimpleCompleter implements Completer {
        private final SortedSet<String> candidates = new TreeSet<>();

        public SimpleCompleter(List<String> suggestions) {
            candidates.addAll(suggestions);
        }

        /**
         * Completes the given line. This is called by Parser#complete ( ParsedLine Candidate ) when it encounters a line that doesn't start with a word or is part of a word.
         *
         * @param reader     - the line reader to use. Can be null in which case the parser is not used.
         * @param line       - the line to complete. Can be null in which case the parser is not used.
         * @param candidates - the list to fill with completions for the given
         */
        @Override
        public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
            String word = line.word().toLowerCase();

            for (String suggestion : this.candidates) {
                if (suggestion.startsWith(word)) {
                    candidates.add(new Candidate(suggestion));
                }
            }
        }
    }
}
