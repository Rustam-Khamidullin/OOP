package ru.nsu.khamidullin.hwChecker.html

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.nsu.khamidullin.hwChecker.model.Student
import ru.nsu.khamidullin.hwChecker.model.StudentStatistic
import ru.nsu.khamidullin.hwChecker.model.TaskStatistic
import ru.nsu.khamidullin.hwChecker.model.TestStatistic
import java.nio.file.Paths

class HtmlGeneratorTest {
    @Test
    fun `test generateHtml with multiple statistics`() {
        val generator = HtmlGenerator()
        val statistics = listOf(
            StudentStatistic(
                Student("John Doe", "repo1", 1),
                Paths.get("projectDir1")
            ).apply {
                taskStatistics["Task 1"] = TaskStatistic(true, true, TestStatistic(8, 2), 90, 5)
                taskStatistics["Task 2"] = TaskStatistic(false, true, TestStatistic(5, 5), 60, 3)
            },
            StudentStatistic(
                Student("Jane Smith", "repo2", 2),
                Paths.get("projectDir2")
            ).apply {
                taskStatistics["Task 1"] = TaskStatistic(true, false, TestStatistic(9, 1), 95, 4)
                taskStatistics["Task 2"] = TaskStatistic(true, true, TestStatistic(10, 0), 100, 5)
            }
        )

        val expectedHtml = """
            <html><head><style>
                 table, th, td {
                 border: 1px solid black;
                 text-align: center;
                 }
                 th, td {padding: 8px}
            </style><title></title></head><body>
            <table><tr><td colspan="7">Task 1</td>
               </tr><tr>
               <th>Student</th>
               <th>Build</th>
               <th>JavaDoc</th>
               <th>Tests result</th>
               <th>Percent</th>
               <th>Score</th>
               <th>Mark</th>
               </tr><tr><td>John Doe</td>
               <td>true</td>
               <td>true</td>
               <td>8/10</td>
               <td>80</td>
               <td>90/11</td>
               <td>5</td></tr><tr><td>Jane Smith</td>
               <td>true</td>
               <td>false</td>
               <td>9/10</td>
               <td>90</td>
               <td>95/11</td>
               <td>4</td></tr></table>
            <table><tr><td colspan="7">Task 2</td>
               </tr><tr>
               <th>Student</th>
               <th>Build</th>
               <th>JavaDoc</th>
               <th>Tests result</th>
               <th>Percent</th>
               <th>Score</th>
               <th>Mark</th>
               </tr><tr><td>John Doe</td>
               <td>false</td>
               <td>true</td>
               <td>5/10</td>
               <td>50</td>
               <td>60/11</td>
               <td>3</td></tr><tr><td>Jane Smith</td>
               <td>true</td>
               <td>true</td>
               <td>10/10</td>
               <td>100</td>
               <td>100/11</td>
               <td>5</td></tr></table>
            </body></html>
        """.replace(" ", "").replace("\n", "").trimIndent()


        val result = generator.generateHtml(statistics, listOf("Task 1", "Task 2"))
        assertEquals(expectedHtml, result.replace(" ", "").replace("\n", ""))
    }
}