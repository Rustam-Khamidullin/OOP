package ru.nsu.khamidullin.hwChecker.model

import java.nio.file.Path

class StudentStatistic(val student: Student, val projectDir : Path) {
    var isClonedRepo = false
    var build = mutableMapOf<String, Boolean>()
    var test = mutableMapOf<String, Boolean>()
    var javadoc = mutableMapOf<String, Boolean>()
}