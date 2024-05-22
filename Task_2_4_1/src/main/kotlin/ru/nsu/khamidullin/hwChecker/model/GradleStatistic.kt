package ru.nsu.khamidullin.hwChecker.model

data class GradleStatistic(
    val build : Boolean,
    val javadoc : Boolean,
    val test : Boolean
)
