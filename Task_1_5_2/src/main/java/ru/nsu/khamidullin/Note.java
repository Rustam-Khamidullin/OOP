package ru.nsu.khamidullin;

import java.time.ZonedDateTime;

public record Note(String text, ZonedDateTime createTime) {
}