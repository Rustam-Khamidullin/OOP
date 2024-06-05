package ru.nsu.khamidullin.hwChecker.model

import java.nio.file.Path

class StudentStatistic(val student: Student, val projectDir : Path) {
    var isRepoCloned = false
    var taskStatistics = mutableMapOf<String, TaskStatistic>()
}