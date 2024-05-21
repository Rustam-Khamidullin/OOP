package ru.nsu.khamidullin.hwChecker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.gradle.tooling.GradleConnector
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic
import java.io.FileNotFoundException
import java.nio.file.Path
import kotlin.io.path.exists


fun buildProject(projectDir: Path) {
    if (!projectDir.exists()) {
        throw FileNotFoundException("Can't find $projectDir")
    }

    val connector = GradleConnector.newConnector().apply {
        forProjectDirectory(projectDir.toFile())
    }

    connector.connect().use { connection ->
        val buildLauncher = connection.newBuild()
        buildLauncher.forTasks("build")
        buildLauncher.run()
    }
}

fun testProject(projectDir: Path) {
    if (!projectDir.exists()) {
        throw FileNotFoundException("Can't find $projectDir")
    }

    val connector = GradleConnector.newConnector().apply {
        forProjectDirectory(projectDir.toFile())
    }

    connector.connect().use { connection ->
        val buildLauncher = connection.newBuild()
        buildLauncher.forTasks("test")
        buildLauncher.run()
    }
}

fun javadocProject(projectDir: Path) {
    if (!projectDir.exists()) {
        throw FileNotFoundException("Can't find $projectDir")
    }

    val connector = GradleConnector.newConnector().apply {
        forProjectDirectory(projectDir.toFile())
    }

    connector.connect().use { connection ->
        val buildLauncher = connection.newBuild()
        buildLauncher.forTasks("javadoc")
        buildLauncher.run()
    }
}


fun buildStudentsProjects(statistics: List<StudentStatistic>, tasks: List<String>) = runBlocking {
    for (statistic in statistics) {
        for (task in tasks) {
            launch(Dispatchers.Default) {
                val taskDir = statistic.projectDir.resolve(task)

                statistic.build[task] = false
                if (taskDir.exists()) {
                    try {
                        buildProject(taskDir)
                        statistic.build[task] = true
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }
                println("${statistic.student.name} build: $task")

                statistic.javadoc[task] = false
                if (statistic.build[task] == true) {
                    try {
                        javadocProject(taskDir)
                        statistic.javadoc[task] = true
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }
                println("${statistic.student.name} javadoc: $task")

                statistic.test[task] = false
                if (statistic.javadoc[task] == true) {
                    try {
                        testProject(taskDir)
                        statistic.test[task] = true
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }
                println("${statistic.student.name} test: $task")
            }
        }
    }
}