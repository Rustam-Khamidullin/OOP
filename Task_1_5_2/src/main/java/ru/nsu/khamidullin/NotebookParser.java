package ru.nsu.khamidullin;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;


public class NotebookParser {
    @Option(name = "-add", usage = "Add note.")
    private boolean add;

    @Option(name = "-rm", usage = "Remove note.")
    private boolean remove;

    @Option(name = "-show", usage = "Show note list.")
    private boolean show;

    @Argument(metaVar = "arguments", usage = "Arguments fot the command.")
    private String[] arguments;

    public NotebookParser() {
        add = false;
        remove = false;
        show = false;
    }

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
        return cnt;
    }


    public boolean isAdd() {
        return add;
    }

    public boolean isRemove() {
        return remove;
    }

    public boolean isShow() {
        return show;
    }

    public String[] getArguments() {
        return arguments;
    }
}
