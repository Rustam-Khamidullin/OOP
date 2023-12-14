package ru.nsu.khamidullin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Notebook implements AutoCloseable {
    private static final String DEFAULT_PATH = "notebook.json";
    private final Map<String, Note> notes;

    private Notebook() {
        this.notes = new HashMap<>();
    }

    public static Notebook open() throws IOException {
        try (Reader reader = new BufferedReader(new FileReader(DEFAULT_PATH))) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(reader, Notebook.class);
        } catch (FileNotFoundException e) {
            return new Notebook();
        }
    }

    public boolean addNote(String name, String text) {
        if (notes.containsKey(name)) {
            return false;
        }
        notes.put(name, new Note(text, LocalDateTime.now()));
        return true;
    }

    public boolean removeNote(String name) {
        return notes.remove(name) != null;
    }

    @Override
    public void close() throws IOException {
        try (Writer writer = new BufferedWriter(new FileWriter(DEFAULT_PATH))) {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(writer, this);
        }
    }

    public Map<String, Note> getNotes() {
        return notes;
    }

    public record Note(String text, LocalDateTime createTime) {
    }
}
