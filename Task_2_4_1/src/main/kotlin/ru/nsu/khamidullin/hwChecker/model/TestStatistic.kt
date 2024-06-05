package ru.nsu.khamidullin.hwChecker.model

data class TestStatistic(
    val passed: Int = 0,
    val errors: Int = 0,
) {
    val total: Int = passed + errors
    val percent = if (total != 0) passed * 100 / total else 0

    operator fun plus(other: TestStatistic): TestStatistic {
        return TestStatistic(
            passed = this.passed + other.passed,
            errors = this.errors + other.errors
        )
    }
}
