package ru.nsu.khamidullin;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class NotebookAnalyzer {

    public static List<String> getSortedNotes(Notebook notebook) {
        Objects.requireNonNull(notebook);

        var notes = notebook.getNotes();

        return notes.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getValue().createTime()))
                .map(e -> e.getKey() + ": " + e.getValue().text())
                .toList();
    }

    public static List<String> getSortedFilteredNotes(Notebook notebook, LocalDateTime from, LocalDateTime to, String[] contains) {
        Objects.requireNonNull(notebook);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        Objects.requireNonNull(contains);

        var notes = notebook.getNotes();

        return notes.entrySet().stream()
                .filter(entry -> {
                    var date = entry.getValue().createTime();
                    if (date.isAfter(to) || date.isBefore(from)) {
                        return false;
                    }
                    for (var con : contains) {
                        if (entry.getKey().contains(con)) {
                            return true;
                        }
                    }
                    return false;
                })
                .sorted(Comparator.comparing(e -> e.getValue().createTime()))
                .map(e -> e.getKey() + ": " + e.getValue().text())
                .toList();
    }
}
