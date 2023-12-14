package ru.nsu.khamidullin;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        NotebookParser notebookParser = new NotebookParser();
        CmdLineParser cmdLineParser = new CmdLineParser(notebookParser);

        Notebook notebook;
        try {
            notebook = Notebook.open();
        } catch (IOException e) {
            System.out.println("Failed to open json.");
            return;
        }

        try {
            cmdLineParser.parseArgument(args);

            if (notebookParser.cntOptions() != 1) {
                System.out.println("Incorrect options usage. Use 1 option.");
                return;
            }

            var arguments = notebookParser.getArguments();

            if (notebookParser.isAdd()) {
                boolean result;
                if (arguments == null || (arguments.length != 1 && arguments.length != 2)) {
                    System.out.println("Incorrect usage. Use: -add <name> <text>.");
                    return;
                } else if (arguments.length == 1) {
                    result = notebook.addNote(arguments[0], "");
                } else {
                    result = notebook.addNote(arguments[0], arguments[1]);
                }

                if (!result) {
                    System.out.println("The note with that name already exists.");
                }

            } else if (notebookParser.isRemove()) {
                if (arguments == null || arguments.length != 1) {
                    System.out.println("Incorrect usage. Use: -rm <name>.");
                    return;
                }

                if (!notebook.removeNote(arguments[0])) {
                    System.out.println("There is no note with this name");
                }


            } else if (notebookParser.isShow()) {
                if (arguments == null || arguments.length == 0) {
                    for (var note : NotebookAnalyzer.getSortedNotes(notebook)) {
                        System.out.println(note);
                    }
                } else if (arguments.length >= 3) {
                    LocalDateTime from;
                    LocalDateTime to;
                    var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy H:m");
                    try {
                        from = LocalDateTime.parse(arguments[0], formatter);
                        to = LocalDateTime.parse(arguments[1], formatter);
                    } catch (Exception e) {
                        System.out.println("Incorrect dates. Use: <dd.MM.yyyy H:m>.");
                        return;
                    }

                    String[] contains = Arrays.copyOfRange(arguments, 2, arguments.length);
                    for (var note : NotebookAnalyzer.getSortedFilteredNotes(notebook, from, to, contains)) {
                        System.out.println(note);
                    }
                } else {
                    System.out.println("Incorrect usage. Use: -show <from> <to> [contains].");
                }
            }

        } catch (CmdLineException e) {
            System.out.println("Incorrect usage.");
            return;
        }
        try {
            notebook.close();
        } catch (IOException e) {
            System.out.println("Failed to write to json.");
        }
    }
}
