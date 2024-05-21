package ru.nsu.khamidullin.hwChecker

import ru.nsu.khamidullin.hwChecker.configuration.Students
import ru.nsu.khamidullin.hwChecker.configuration.Tasks
import ru.nsu.khamidullin.hwChecker.html.SimpleHtmlGenerator
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.file.DirectoryNotEmptyException
import java.nio.file.Path
import kotlin.io.path.listDirectoryEntries

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Incorrect directory")
        return
    }

    val workDir = Path.of(args[0])

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

    try {
        cloneRepositories(statistics)
    } catch (e: Exception) {
        println(e.message)
        return
    }

    buildStudentsProjects(statistics, tasks)

    statistics.forEach { println(it.build) }
    statistics.forEach { println(it.javadoc) }
    statistics.forEach { println(it.test) }

    val file = File("./index.html")
    val out = OutputStreamWriter(FileOutputStream(file))

    val html = SimpleHtmlGenerator()
    out.write(html.generateHtml(statistics))
    out.close()
}