package ru.nsu.khamidullin;

import  org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;


/**
 * Parses command-line arguments using Args4J library for the command-line note-taking application.
 *
 * <p>This class defines options and arguments for adding, removing, showing, and modifying the path
 * of the notebook file. It is used in conjunction with Args4J annotations to specify how command-line
 * arguments should be parsed.
 */
public class NotebookParser {
    @Option(name = "-add", usage = "Add note.")
    private boolean add;

    @Option(name = "-rm", usage = "Remove note.")
    private boolean remove;

    @Option(name = "-show", usage = "Show note list.")
    private boolean show;

    @Option(name = "-path", usage = "Show note list.")
    private boolean path;

    @Argument(metaVar = "arguments", usage = "Arguments for the command.")
    private String[] arguments;

    /**
     * Default constructor for the NotebookParser class.
     * Initializes options to false.
     */
    public NotebookParser() {
        add = false;
        remove = false;
        show = false;
        path = false;
    }

    /**
     * Counts the number of specified options.
     *
     * @return The number of specified options.
     */
    public int cntOptions() {
        int cnt = 0;
        if (add) {
            cnt++;
        }
        if (remove) {
            cnt++;
        }
        if (show) {
            cnt++;
        }
        if (path) {
            cnt++;
        }
        return cnt;
    }

    /**
     * Checks if the "add" option is specified.
     *
     * @return {@code true} if the "add" option is specified, otherwise {@code false}.
     */
    public boolean isAdd() {
        return add;
    }

    /**
     * Checks if the "remove" option is specified.
     *
     * @return {@code true} if the "remove" option is specified, otherwise {@code false}.
     */
    public boolean isRemove() {
        return remove;
    }

    /**
     * Checks if the "show" option is specified.
     *
     * @return {@code true} if the "show" option is specified, otherwise {@code false}.
     */
    public boolean isShow() {
        return show;
    }

    /**
     * Checks if the "path" option is specified.
     *
     * @return {@code true} if the "path" option is specified, otherwise {@code false}.
     */
    public boolean isPath() {
        return path;
    }

    /**
     * Gets the arguments provided for the command.
     *
     * @return An array of arguments for the command.
     */
    public String[] getArguments() {
        return arguments;
    }
}