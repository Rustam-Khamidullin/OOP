package ru.nsu.khamidullin;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main {
    private Main() {
    }

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
