package ru.nsu.khamidullin.hwChecker.configuration

import kotlin.math.roundToInt

data object Criteria {
    const val BUILD = 5
    const val JAVADOC = 1
    const val PASSED = 5

    val score2mark = { score: Int ->
        (score * 3 / MAX.toDouble()).roundToInt() + 2
    }

    const val MAX = BUILD + JAVADOC + PASSED
}
