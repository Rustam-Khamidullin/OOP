package ru.nsu.khamidullin.hwChecker.tests

import ru.nsu.khamidullin.hwChecker.model.TestStatistic
import java.nio.file.Path

interface TestParser {
    fun parseAllXmlTests(dir: Path): TestStatistic
}