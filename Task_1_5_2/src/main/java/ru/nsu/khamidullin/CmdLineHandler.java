package ru.nsu.khamidullin;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Handles command-line options and arguments to perform actions on a {@code Notebook}.
 *
 * <p>This class interprets command-line options and arguments to perform actions on a {@code Notebook},
 * such as changing the notebook file path, adding, removing, or displaying notes. It interacts with the
 * {@code Notebook} class and utilizes the {@code NotebookParser} to parse command-line inputs.
 */
public class CmdLineHandler {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy H:m");

    /**
     * Handles the provided {@code NotebookParser} and executes corresponding actions on the {@code Notebook}.
     *
     * @param notebookParser The {@code NotebookParser} instance containing parsed command-line options and arguments.
     */
    public static void handle(NotebookParser notebookParser) {
        if (notebookParser.cntOptions() != 1) {
            System.out.println("Incorrect options usage. Use 1 option.");
            return;
        }

        var arguments = notebookParser.getArguments();

        try {
            // Static options
            if (notebookParser.isPath()) {
                if (arguments == null || arguments.length != 1) {
                    System.out.println("Incorrect usage. Use: -path <newPath>.");
                    return;
                }

                Notebook.setPropertiesPath(arguments[0]);
                return;
            }


            // Non-static options
            Notebook notebook = Notebook.open();


            if (notebookParser.isAdd()) {
                String text;
                if (arguments == null || (arguments.length != 1 && arguments.length != 2)) {
                    System.out.println("Incorrect usage. Use: -add <name> <text>.");
                    return;
                } else if (arguments.length == 1) {
                    text = "";
                } else {
                    text = arguments[1];
                }

                if (!notebook.addNote(arguments[0], text)) {
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
                    notebook.printSortedNotes();
                } else if (arguments.length >= 3) {
                    LocalDateTime from;
                    LocalDateTime to;

                    try {
                        from = LocalDateTime.parse(arguments[0], TIME_FORMATTER);
                        to = LocalDateTime.parse(arguments[1], TIME_FORMATTER);
                    } catch (Exception e) {
                        System.out.println("Incorrect date. Use: <dd.MM.yyyy H:m>.");
                        return;
                    }

                    var curZone = ZoneId.systemDefault();

                    String[] contains = Arrays.copyOfRange(arguments, 2, arguments.length);
                    notebook.printSortedFilteredNotes(from.atZone(curZone), to.atZone(curZone), contains);
                } else {
                    System.out.println("Incorrect usage. Use: -show <from> <to> [contains].");
                }


            }
        } catch (IOException e) {
            System.out.println("There is some problem: " + e.getMessage());
        }
    }
}
