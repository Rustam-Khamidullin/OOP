package ru.nsu.khamidullin;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Entry point for the command-line note-taking application.
 *
 * <p>This class contains the main method that initializes the application by parsing command-line
 * arguments using {@code CmdLineParser} and executing corresponding actions on the notebook using
 * {@code CmdLineHandler}.
 */
public class Main {
    private Main() {
    }

    /**
     * The entry point for the command-line note-taking application.
     *
     * @param args Command-line arguments provided by the user.
     */
    public static void main(String[] args) {
        NotebookParser notebookParser = new NotebookParser();
        CmdLineParser cmdLineParser = new CmdLineParser(notebookParser);

        try {
            cmdLineParser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println("Incorrect usage");
        }

        CmdLineHandler.handle(notebookParser);
    }
}
