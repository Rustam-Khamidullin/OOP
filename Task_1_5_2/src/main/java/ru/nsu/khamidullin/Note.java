package ru.nsu.khamidullin;

import java.time.ZonedDateTime;

/**
 * Represents a note with text content and a creation timestamp.
 *
 * <p>This record encapsulates the essential properties of a note, including its textual content
 * and the timestamp when it was created.
 */
public record Note(String text, ZonedDateTime createTime) {
}