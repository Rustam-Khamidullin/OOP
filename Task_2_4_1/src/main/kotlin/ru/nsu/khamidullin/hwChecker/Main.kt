package ru.nsu.khamidullin.hwChecker

import ru.nsu.khamidullin.hwChecker.configuration.Students
import ru.nsu.khamidullin.hwChecker.configuration.Tasks
import ru.nsu.khamidullin.hwChecker.html.HtmlGenerator
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.file.DirectoryNotEmptyException
import java.nio.file.Path
import kotlin.io.path.listDirectoryEntries

private const val DEFAULT_DIR = "./repositories"
private const val DEFAULT_OUTPUT = "./index.html"

fun main(args: Array<String>) {
    val workDir = Path.of(if (args.isNotEmpty()) args[0] else DEFAULT_DIR)
    val output = Path.of(if (args.size == 2) args[1] else DEFAULT_OUTPUT)

    if (workDir.listDirectoryEntries().isNotEmpty()) {
        throw DirectoryNotEmptyException("Clear directory")
    }

    val students = Students.list
    val tasks = Tasks.list

    val statistics = ArrayList<StudentStatistic>()
    students.forEach {
        val studentDir = workDir.resolve("${it.name}_${it.group}")
        statistics.add(StudentStatistic(it, studentDir))
    }

    // Clone
   cloneRepositories(statistics)

    // Build
    processStudentsProjects(statistics, tasks)

    // Result html
    OutputStreamWriter(FileOutputStream(output.toFile())).use {
        val htmlGenerator = HtmlGenerator()
        it.write(htmlGenerator.generateHtml(statistics, Tasks.list))
    }
}