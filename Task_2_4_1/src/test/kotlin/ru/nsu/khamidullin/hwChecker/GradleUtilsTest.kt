package ru.nsu.khamidullin.hwChecker

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File


class GradleUtilsTest {
    private val workingDir = File("./repositoriesTest")

    private fun cleanUp(taskDir: File) {
        taskDir.resolve("build").deleteRecursively()
    }

    @Test
    fun testTask_1_1_1() {
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

    @Test
    fun testTask_1_1_2() {
        val taskDir = workingDir.resolve("Task_1_1_2")

        cleanUp(taskDir)

        Assertions.assertFalse(taskDir.resolve("build").exists())

        val statistic = getTaskStatistic(taskDir.toPath())

        Assertions.assertTrue(taskDir.resolve("build").exists())

        Assertions.assertTrue(statistic.build)
        Assertions.assertTrue(statistic.javadoc)
        Assertions.assertEquals(statistic.testStatistic.errors, 0)
        Assertions.assertEquals(statistic.testStatistic.passed, 10)
        Assertions.assertEquals(statistic.testStatistic.total, 10)
    }
}
