package ru.nsu.khamidullin.hwChecker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import ru.nsu.khamidullin.hwChecker.model.GradleStatistic
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic
import java.nio.file.Path
import kotlin.io.path.exists

fun runTask(task: String, connection: ProjectConnection) {
    with(connection.newBuild()) {
        forTasks(task)
        run()
    }
}

fun gradleProcessProject(projectDir: Path): GradleStatistic {
    var build = false
    var javadoc = false
    var test = false

    if (projectDir.exists()) {
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
    }

    return GradleStatistic(build, javadoc, test)
}


fun buildStudentsProjects(statistics: List<StudentStatistic>, tasks: List<String>) = runBlocking {
    for (statistic in statistics) {
        if (!statistic.isRepoCloned) {
            continue
        }

        for (task in tasks) {
            launch(Dispatchers.Default) {
                val taskDir = statistic.projectDir.resolve(task)

                statistic.gradleStatistics[task] = gradleProcessProject(taskDir)
            }
        }
    }
}