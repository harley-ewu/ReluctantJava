package MenuPrompts;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class AutoComplete {

    private Terminal terminal;
    private LineReader lineReader;

    public AutoComplete() {
        try {
            this.terminal = TerminalBuilder.builder()
                            .build();
            /*this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add relationship", "add method", "rename", "view", "back", "edit", "save", "load", "exit")))
                    .build();*/
        } catch(Exception e) {
            System.out.println("You tryna crash the program??");
        }
        
    }

    public void classLineReader(){
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add class", "delete class", "rename class", "add relationship", "delete relationship", "edit class", "edit relationships", "view class", "view diagram", "help")))
                    .build();
    }

    public void mainLineReader() {
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("view diagram", "edit current diagram", "save current", "load existing", "help", "gui")))
                    .build();
    }

    public void newClassLineReader(){
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add attribute", "add relationship", "back", "help")))
                    .build();
    }

    public void editClassLineReader() {
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add attribute", "delete attribute", "rename attribute", "display attributes", "display relationships", "display all contents", "back", "help")))
                    .build();
    }

    public void relationshipEditorLineReader() {
        this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add relationship", "delete relationship")))
                    .build();
    }

    public String getCommands(){
        String line = "";
        try {
            // Set up JLine terminal
            /*Terminal terminal = TerminalBuilder.builder()
                    .build();
            LineReader lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new SimpleCompleter(Arrays.asList("add", "delete", "rename", "view", "back", "edit", "save", "load", "exit")))
                    .build();*/

            // Main loop to read input
            while (true) {
                //String prompt = "Type Command (press Tab for autocomplete): ";
                //String prompt2 = "Type what entity to edit (press Tab for autocomplete): ";
                line = this.lineReader.readLine();
                //String line2 = lineReader2.readLine(prompt2);

                if (line == "") {
                    break; // User pressed Enter
                }
                return line ;
            }
        } catch (Exception e) {
            System.out.println("You tryna crash the program?? Again???");
        }
        return line;
    }

    public boolean isNumber(final String input) {
        if(input.isEmpty()){
            return false;
        }
        if(Character.isDigit(input.charAt(0))){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        AutoComplete test = new AutoComplete();
        test.getCommands();
    }

    private class SimpleCompleter implements Completer {
        private final SortedSet<String> candidates = new TreeSet<>();

        public SimpleCompleter(List<String> suggestions) {
            candidates.addAll(suggestions);
        }

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
