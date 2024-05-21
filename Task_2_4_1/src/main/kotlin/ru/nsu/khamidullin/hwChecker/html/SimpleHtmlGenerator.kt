package ru.nsu.khamidullin.hwChecker.html

import ru.nsu.khamidullin.hwChecker.configuration.Tasks
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic

class SimpleHtmlGenerator : HtmlGenerator {
    override fun generateHtml(statistics: List<StudentStatistic>): String {
        val htmlStringBuilder = StringBuilder("<html>" +
                "<head>    <style>\n" +
                "        table, th, td {            border: 1px solid black;\n" +
                "            text-align: center;        }\n" +
                "        th, td {            padding: 8px;\n" +
                "        }    </style>\n" +
                "    <title></title></head><body>")

        for (task in Tasks.list) {
            htmlStringBuilder.append("<table>")
            htmlStringBuilder.append(getTemplate(task))

            for (statistic in statistics) {
                val student = statistic.student
                val studentUserName = student.name
                val buildMark = statistic.build[task]
                val javaDocMark = statistic.javadoc[task]
                val testMark = statistic.test[task]
                htmlStringBuilder.append("<tr><td>$studentUserName</td>")
                htmlStringBuilder.append("<td>$buildMark</td>")
                htmlStringBuilder.append("<td>$javaDocMark</td>")
                htmlStringBuilder.append("<td>$testMark</td></tr>")
            }
            htmlStringBuilder.append("</table>")
        }
        htmlStringBuilder.append("</body></html>")

        println(htmlStringBuilder.toString())
        return htmlStringBuilder.toString()
    }

    private fun getTemplate(task: String): StringBuilder {
        val htmlStringBuilder = StringBuilder()
        htmlStringBuilder.append(
            """<tr>
    <td colspan="7">${task}</td>
</tr>
<tr>
    <th>Student</th>
    <th>Build</th>
    <th>JavaDoc</th>
    <th>Tests</th>
</tr>""")
        return htmlStringBuilder
    }
}
