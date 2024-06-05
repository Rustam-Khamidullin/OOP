package ru.nsu.khamidullin.hwChecker.model

data class TaskStatistic(
    val build: Boolean,
    val javadoc: Boolean,
    val testStatistic: TestStatistic,
    val sum: Int,
    val mark: Int
)
