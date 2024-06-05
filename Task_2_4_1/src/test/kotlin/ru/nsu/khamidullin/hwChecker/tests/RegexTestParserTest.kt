package ru.nsu.khamidullin.hwChecker.tests

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.nsu.khamidullin.hwChecker.model.TestStatistic

class RegexTestParserTest {
    private val parser = RegexTestParser()

    @Test
    fun parseTest1() {
        val xmlContent = """
        <?xml version="1.0" encoding="UTF-8"?>
        <testsuite name="ru.nsu.khamidullin.MainTest" tests="5" skipped="0" failures="0" errors="2" timestamp="2024-05-22T04:45:48" time="0.06">
        </testsuite>
    """

        Assertions.assertEquals(parser.parse(xmlContent), TestStatistic(3,2))
    }

    @Test
    fun parseTest2() {
        val xmlContent = """
        <?xml version="1.0" encoding="UTF-8"?>
        <testsuite name="ru.nsu.khamidullin.MainTest" tests="5" skipped="4" failures="3" errors="" timestamp="2024-05-22T04:45:48" time="0.06">
        </testsuite>
    """

        Assertions.assertEquals(parser.parse(xmlContent), TestStatistic())
    }
}