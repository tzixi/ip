package Gilbert;

import Gilbert.exceptions.GilbertException;
import Gilbert.tasks.*;
import Gilbert.parser.*;
import Gilbert.ui.UI;
import Gilbert.storage.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Gilbert {
    public static TaskList taskList;
    public static String FILEPATH = "data/gilbert.txt";
    public static String[] input;

    public static void run(){
        boolean active = true;

        UI.userGreeting();

        while(active){
            try {
                input = Parser.userInput();
                Parser parse = new Parser();
                parse.executeCommand(taskList, input);
                active = Parser.isExit();
                try {
                    Files.writeToFile(FILEPATH, taskList);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (GilbertException e) {
                e.errorMessage("");
            }
        }

        UI.userExit();
    }

    /**
     * Main method of the Gilbert program that executes whenever Gilbert starts.
     *
     * @param args  Command line instructions inputted by the user.
     */
    public static void main(String[] args) {
        taskList = new TaskList();
        try {
            Files.loadFile(FILEPATH, taskList);
        } catch (FileNotFoundException | IndexOutOfBoundsException e) {
            System.out.println("Oops, no existing files found!" + e.getMessage());
        }
        Gilbert.run();
    }

}
