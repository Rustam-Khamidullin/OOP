package ru.nsu.khamidullin.hwChecker.tests

import ru.nsu.khamidullin.hwChecker.model.TestStatistic
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

private val REGEX = """tests="(\d+)" skipped="(\d+)" failures="(\d+)" errors="(\d+)"""".toRegex()

class RegexTestParser : TestParser {
    override fun parseAllXmlTests(dir: Path): TestStatistic {
        var result = TestStatistic()

        if (!dir.exists() || !dir.isDirectory()) {
            return result
        }

        for (file in dir.toFile().listFiles()!!) {
            try {
                if (file.name.startsWith("TEST") && file.name.endsWith(".xml")) {
                    result += parse(file.readText())
                }
            } catch (e: Exception) {
                println("Incorrect xml file ${file.name}")
            }
        }

        return result
    }

    fun parse(xml: String): TestStatistic {
        val matchResult = REGEX.find(xml) ?: return TestStatistic()

        val (tests, skipped, failures, errors) = matchResult.destructured
        val failed = skipped.toInt() + failures.toInt() + errors.toInt()
        return TestStatistic(tests.toInt() - failed, failed)
    }
}