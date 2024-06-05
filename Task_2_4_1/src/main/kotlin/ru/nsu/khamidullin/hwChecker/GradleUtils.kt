package ru.nsu.khamidullin.hwChecker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import ru.nsu.khamidullin.hwChecker.configuration.Criteria
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic
import ru.nsu.khamidullin.hwChecker.model.TaskStatistic
import ru.nsu.khamidullin.hwChecker.model.TestStatistic
import ru.nsu.khamidullin.hwChecker.tests.RegexTestParser
import java.nio.file.Path
import kotlin.io.path.exists

private val testParser = RegexTestParser()
private const val TEST_RESULT = "./build/test-results/test"


fun aggregateTaskStatistic(statistics: List<TaskStatistic>) {
    statistics.forEach {
        var sum = 0

        if (it.build) sum += Criteria.BUILD
        if (it.javadoc) sum += Criteria.JAVADOC
        if (it.testStatistic.passed == it.testStatistic.total) sum += Criteria.PASSED
    }
}


private fun runTask(task: String, connection: ProjectConnection) {
    with(connection.newBuild()) {
        forTasks(task)
        run()
    }
}

fun getTaskStatistic(projectDir: Path): TaskStatistic {
    var score = 0
    var build = false
    var javadoc = false
    var testStatistic = TestStatistic()

    if (projectDir.exists()) {
        val connector = GradleConnector.newConnector().apply {
            forProjectDirectory(projectDir.toFile())
        }

        connector.connect().use { connection ->
            try {
                runTask("build", connection)
                score += Criteria.BUILD
                build = true

                runTask("javadoc", connection)
                score += Criteria.JAVADOC
                javadoc = true
            } catch (ignored: Exception) {
            }

            try {
                testStatistic = testParser.parseAllXmlTests(projectDir.resolve(TEST_RESULT))
                if (testStatistic.passed == testStatistic.total) score += Criteria.PASSED
            } catch (ignored: Exception) {
            }
        }
    }

    return TaskStatistic(
        build,
        javadoc,
        testStatistic,
        score,
        Criteria.score2mark(score)
    )
}

fun processStudentsProjects(statistics: List<StudentStatistic>, tasks: List<String>) = runBlocking {
    for (statistic in statistics) {
        if (!statistic.isRepoCloned) {
            continue
        }

        for (task in tasks) launch(Dispatchers.Default) {
            val taskDir = statistic.projectDir.resolve(task)

            statistic.taskStatistics[task] = getTaskStatistic(taskDir)
        }
    }
}
