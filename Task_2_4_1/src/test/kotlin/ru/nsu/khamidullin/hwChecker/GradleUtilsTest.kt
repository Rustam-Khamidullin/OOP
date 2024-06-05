package ru.nsu.khamidullin.hwChecker

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File


class GradleUtilsTest {
    val workingDir = File("./repositoriesTest")

    fun cleanUp(taskDir: File) {
        taskDir.resolve("build").deleteRecursively()
    }

    @Test
    fun buildTest() {
        val taskDir = workingDir.resolve("Task_1_1_1")

        cleanUp(taskDir)

        Assertions.assertFalse(taskDir.resolve("build").exists())

        val statistic = getTaskStatistic(taskDir.toPath())

        Assertions.assertTrue(taskDir.resolve("build").exists())

        Assertions.assertFalse(statistic.build)
        Assertions.assertFalse(statistic.javadoc)
        Assertions.assertEquals(statistic.testStatistic.errors, 3)
        Assertions.assertEquals(statistic.testStatistic.passed, 2)
        Assertions.assertEquals(statistic.testStatistic.total, 5)
    }
}