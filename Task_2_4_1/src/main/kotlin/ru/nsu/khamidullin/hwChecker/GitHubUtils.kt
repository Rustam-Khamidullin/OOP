package ru.nsu.khamidullin.hwChecker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists


private fun runCommand(command: List<String>, workingDir: File): Int {
    val processBuilder = ProcessBuilder(command)
    processBuilder.directory(workingDir)
    processBuilder.redirectErrorStream(true)
    val process = processBuilder.start()
    return process.waitFor()
}

fun cloneRepository(repository: String, dir: Path): Int {
    Files.createDirectories(dir)

    println("Клонируем $repository ...")
    return runCommand(listOf("git", "clone", repository, "./"), dir.toFile())
}

fun cloneRepositories(statistics: List<StudentStatistic>) = runBlocking {
    for (statistic in statistics) {
        launch(Dispatchers.IO) {
            val student = statistic.student

            val status = cloneRepository(student.repository, statistic.projectDir)

            if (status == 128) {
                throw Exception("Clear target directory")
            }

            statistic.isClonedRepo = status == 0
        }
    }
}