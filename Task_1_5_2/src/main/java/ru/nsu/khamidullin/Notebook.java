package ru.nsu.khamidullin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Notebook {
    private static final String PROPERTIES_PATH = "Notebook.properties";
    private static final String PATH_VAR = "path";
    private final Map<String, Note> notes;
    private final String path;

    private Notebook(Map<String, Note> notes, String path) {
        this.notes = notes;
        this.path = path;
    }

    public static Notebook open() throws IOException {
        String path;
        try {
            path = getPropertiesPath();
        } catch (IOException e) {
            throw new IOException("Failed to get path form 'Notebook.properties'.", e);
        }

        try (Reader reader = new BufferedReader(new FileReader(path))) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Map<String, Note> notes =  objectMapper.readValue(reader, new TypeReference<>() {});

            return new Notebook(notes, path);
        } catch (FileNotFoundException e) {
            return new Notebook(new HashMap<>(), path);
        }
    }

    private static String getPropertiesPath() throws IOException {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(input);
            return properties.getProperty(PATH_VAR);
        } catch (IOException e) {
            throw new IOException("Failed to get properties path.", e);
        }
    }

    public static void setPropertiesPath(String path) throws IOException {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(input);

            properties.setProperty(PATH_VAR, path);

            try (FileOutputStream output = new FileOutputStream(PROPERTIES_PATH)) {
                properties.store(output, null);
            }
        } catch (IOException e) {
            throw new IOException("Failed to set new properties path.", e);
        }
    }

    public void printSortedNotes() {
        notes.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getValue().createTime()))
                .map(e -> e.getKey() + ": " + e.getValue().text())
                .forEach(System.out::println);
    }

    public void printSortedFilteredNotes(ZonedDateTime from, ZonedDateTime to, String[] contains) {
        notes.entrySet().stream()
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
                .forEach(System.out::println);
    }

    public boolean addNote(String name, String text) throws IOException {
        if (notes.containsKey(name)) {
            return false;
        }
        notes.put(name, new Note(text, ZonedDateTime.now()));
        save();
        return true;
    }

    public boolean removeNote(String name) throws IOException {
        boolean result = notes.remove(name) != null;
        if (result) {
            save();
        }
        return result;
    }

    private void save() throws IOException {
        try (Writer writer = new BufferedWriter(new FileWriter(path))) {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(writer, notes);
        }
    }
}
