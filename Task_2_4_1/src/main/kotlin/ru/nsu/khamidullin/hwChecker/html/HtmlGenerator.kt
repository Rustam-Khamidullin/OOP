package ru.nsu.khamidullin.hwChecker.html

import ru.nsu.khamidullin.hwChecker.model.StudentStatistic

interface HtmlGenerator {
    fun generateHtml(statistics: List<StudentStatistic>): String
}