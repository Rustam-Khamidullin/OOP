package ru.nsu.khamidullin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Simple note-taking application with basic CRUD operations and filtering capabilities.
 *
 * <p>Each note has a name, text content, and a creation timestamp. Notes are stored in
 * a map where the key is the note name and the value is the corresponding {@code Note} object.
 */
public class Notebook {
    private static final String PROPERTIES_PATH = "Notebook.properties";
    private static final String PATH_VAR = "path";
    private final Map<String, Note> notes;
    private final String path;

    private Notebook(Map<String, Note> notes, String path) {
        this.notes = notes;
        this.path = path;
    }

    /**
     * Creates a new notebook instance by opening an existing one or creating a new one.
     *
     * @return A new {@code Notebook} instance.
     * @throws IOException If an error occurs while opening the notebook.
     */
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
            Map<String, Note> notes = objectMapper.readValue(reader, new TypeReference<>() {
            });

            return new Notebook(notes, path);
        } catch (FileNotFoundException e) {
            return new Notebook(new HashMap<>(), path);
        }
    }

    /**
     * Retrieves the properties path from the configuration file.
     *
     * <p>This method reads the properties file specified by the constant {@code PROPERTIES_PATH},
     * loads its content using the {@code Properties} class, and returns the value associated with
     * the key specified by the constant {@code PATH_VAR}. The properties file is expected to
     * contain a key-value pair where the key is {@code "path"} and the value is the path to the
     * notebook file.
     *
     * @return The properties path retrieved from the configuration file.
     * @throws IOException If an error occurs while reading or loading the properties file,
     *                     or if the specified key is not found in the file.
     */
    public static String getPropertiesPath() throws IOException {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(input);
            return properties.getProperty(PATH_VAR);
        } catch (IOException e) {
            throw new IOException("Failed to get properties path.", e);
        }
    }

    /**
     * Sets the properties path for the notebook.
     *
     * @param path The new properties path.
     * @throws IOException If an error occurs while setting the new properties path.
     */
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

    /**
     * Get all notes in the notebook, sorted by creation timestamp.
     */
    public List<String> getSortedNotes() {
        return notes.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getValue().createTime()))
                .map(e -> e.getKey() + ": " + e.getValue().text())
                .toList();
    }

    /**
     * Print all notes in the notebook, sorted by creation timestamp.
     */
    public void printSortedNotes() {
        getSortedNotes().forEach(System.out::println);
    }

    /**
     * Get notes in the notebook, filtered by date range and containing specific keywords,
     * and sorted by creation timestamp.
     *
     * @param from     The start of the date range.
     * @param to       The end of the date range.
     * @param contains Keywords to filter notes by.
     */
    public List<String> getSortedFilteredNotes(ZonedDateTime from, ZonedDateTime to, String[] contains) {
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

    /**
     * Print notes in the notebook, filtered by date range and containing specific keywords,
     * and sorted by creation timestamp.
     *
     * @param from     The start of the date range.
     * @param to       The end of the date range.
     * @param contains Keywords to filter notes by.
     */
    public void printSortedFilteredNotes(ZonedDateTime from, ZonedDateTime to, String[] contains) {
        getSortedFilteredNotes(from, to, contains).forEach(System.out::println);
    }

    /**
     * Adds a new note to the notebook.
     *
     * @param name The name of the new note.
     * @param text The text content of the new note.
     * @return {@code true} if the note was added successfully, {@code false} if a note with
     * the same name already exists.
     * @throws IOException If an error occurs while adding the note.
     */
    public boolean addNote(String name, String text) throws IOException {
        if (notes.containsKey(name)) {
            return false;
        }
        notes.put(name, new Note(text, ZonedDateTime.now()));
        save();
        return true;
    }

    /**
     * Removes a note from the notebook by name.
     *
     * @param name The name of the note to be removed.
     * @return {@code true} if the note was removed successfully, {@code false} if the note
     * does not exist.
     * @throws IOException If an error occurs while removing the note.
     */
    public boolean removeNote(String name) throws IOException {
        boolean result = notes.remove(name) != null;
        if (result) {
            save();
        }
        return result;
    }

    /**
     * Saves the current state of the notebook to the file.
     *
     * @throws IOException If an error occurs while saving the notebook.
     */
    private void save() throws IOException {
        try (Writer writer = new BufferedWriter(new FileWriter(path))) {
            ObjectMapper objectMapper = new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(writer, notes);
        }
    }

    public Map<String, Note> getNotes() {
        return notes;
    }
}
