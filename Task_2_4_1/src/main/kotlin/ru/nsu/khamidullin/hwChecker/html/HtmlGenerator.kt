package ru.nsu.khamidullin.hwChecker.html

import ru.nsu.khamidullin.hwChecker.configuration.Criteria
import ru.nsu.khamidullin.hwChecker.configuration.Tasks
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic

class HtmlGenerator {
    fun generateHtml(statistics: List<StudentStatistic>): String {
        val htmlStringBuilder = StringBuilder(
            """
            <html><head><style>
                 table, th, td {
                 border: 1px solid black;
                 text-align: center;
                 }
                 th, td {padding: 8px}
            </style><title></title></head><body>
        """.trimIndent()
        )

        for (task in Tasks.list) {
            htmlStringBuilder.append("<table>")
            htmlStringBuilder.append(getTemplate(task))

            for (statistic in statistics) {
                appendLine(htmlStringBuilder, statistic, task)
            }
            htmlStringBuilder.append("</table>")
        }
        htmlStringBuilder.append("</body></html>")

        return htmlStringBuilder.toString()
    }

    private fun appendLine(
        htmlStringBuilder: StringBuilder,
        statistic: StudentStatistic,
        task: String
    ) {
        htmlStringBuilder.append("<tr><td>${statistic.student.name}</td>")
        statistic.taskStatistics[task]?.let {
            htmlStringBuilder.append("<td>${it.build}</td>")
            htmlStringBuilder.append("<td>${it.javadoc}</td>")
            val testStatistic = it.testStatistic
            htmlStringBuilder.append("<td>${testStatistic.passed}/${testStatistic.total}</td>")
            htmlStringBuilder.append("<td>${testStatistic.percent}</td>")
            htmlStringBuilder.append("<td>${it.sum}/${Criteria.MAX}</td>")
            htmlStringBuilder.append("<td>${it.mark}</td></tr>")
        }
    }

    private fun getTemplate(task: String): StringBuilder {
        val htmlStringBuilder = StringBuilder()
        htmlStringBuilder.append(
            """<tr><td colspan="7">${task}</td>
               </tr><tr>
               <th>Student</th>
               <th>Build</th>
               <th>JavaDoc</th>
               <th>Tests result</th>
               <th>Percent</th>
               <th>Score</th>
               <th>Mark</th>
               </tr>"""
        )
        return htmlStringBuilder
    }
}
