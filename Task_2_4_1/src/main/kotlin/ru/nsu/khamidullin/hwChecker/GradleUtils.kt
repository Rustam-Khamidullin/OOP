package ru.nsu.khamidullin.hwChecker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic
import java.io.FileNotFoundException
import java.nio.file.Path
import kotlin.io.path.exists

fun runTask(task: String, connection: ProjectConnection) {
    with(connection.newBuild()) {
        forTasks(task)
        run()
    }
}

fun gradleProcessProject(projectDir: Path): Triple<Boolean, Boolean, Boolean> {
    var build = false
    var javadoc = false
    var test = false

    if (!projectDir.exists()) {
        throw FileNotFoundException("Can't find $projectDir")
    }

    val connector = GradleConnector.newConnector().apply {
        forProjectDirectory(projectDir.toFile())
    }

    connector.connect().use { connection ->
        try {
            runTask("build", connection)
            build = true

            runTask("javadoc", connection)
            javadoc = true

            runTask("test", connection)
            test = true
        } catch (ignored: Exception) {
        }
    }

    return Triple(build, javadoc, test)
}


fun buildStudentsProjects(statistics: List<StudentStatistic>, tasks: List<String>) = runBlocking {
    for (statistic in statistics) {
        for (task in tasks) {
            launch(Dispatchers.Default) {
                val taskDir = statistic.projectDir.resolve(task)

                with(gradleProcessProject(taskDir)) {
                    statistic.build[task] = first
                    statistic.javadoc[task] = second
                    statistic.test[task] = third
                }
            }
        }
    }
}