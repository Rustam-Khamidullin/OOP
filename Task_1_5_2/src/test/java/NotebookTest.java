import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import ru.nsu.khamidullin.Main;
import ru.nsu.khamidullin.Notebook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


/**
 * Test class.
 */
public class NotebookTest {
    /**
     * Clean up.
     */
    @AfterAll
    @BeforeAll
    public static void cleanUp() throws IOException {
        File currentDirectory = new File(".");
        File[] files = currentDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    file.delete();
                }
            }
        }
        Notebook.setPropertiesPath("Notebook.json");
    }

    @Test
    public void testNotebookAdd() {
        String[] args;

        args = new String[]{"-path", "Notebook1.json"};
        Main.main(args);

        args = new String[]{"-add", "1", "text1"};
        Main.main(args);

        args = new String[]{"-add", "2", "text2"};
        Main.main(args);

        args = new String[]{"-add", "3", "text3"};
        Main.main(args);

        Notebook notebook;
        try {
            notebook = Notebook.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var notes = notebook.getNotes();
        assertEquals(notes.size(), 3);
        assertEquals(notes.get("1").text(), "text1");
        assertEquals(notes.get("2").text(), "text2");
        assertEquals(notes.get("3").text(), "text3");
    }

    @Test
    public void testNotebookRemove() {
        String[] args;

        args = new String[]{"-path", "Notebook2.json"};
        Main.main(args);

        args = new String[]{"-add", "1", "text1"};
        Main.main(args);

        args = new String[]{"-add", "2", "text2"};
        Main.main(args);

        args = new String[]{"-add", "3", "text3"};
        Main.main(args);

        args = new String[]{"-rm", "3"};
        Main.main(args);

        args = new String[]{"-rm", "1"};
        Main.main(args);

        Notebook notebook;
        try {
            notebook = Notebook.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var notes = notebook.getNotes();
        assertEquals(notes.size(), 1);
        assertEquals(notes.get("2").text(), "text2");
    }

    @Test
    public void testNotebookPath() throws IOException {
        String[] args;

        args = new String[]{"-path", "Notebook3.json"};
        Main.main(args);

        assertEquals(Notebook.getPropertiesPath(), "Notebook3.json");
    }

    @Test
    public void testNotebookSorted() throws IOException {
        String[] args;

        args = new String[]{"-path", "Notebook3.json"};
        Main.main(args);

        assertEquals(Notebook.getPropertiesPath(), "Notebook3.json");
    }

    @Test
    public void testShowSorted() {
        String[] args;

        args = new String[]{"-path", "Notebook4.json"};
        Main.main(args);

        args = new String[]{"-add", "1", "text1"};
        Main.main(args);

        args = new String[]{"-add", "2", "text2"};
        Main.main(args);

        args = new String[]{"-add", "3", "text3"};
        Main.main(args);


        Notebook notebook;
        try {
            notebook = Notebook.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var expected = List.of("1: text1", "2: text2", "3: text3");
        assertIterableEquals(notebook.getSortedNotes(), expected);
    }

    @Test
    public void testShowSortedFiltered() throws IOException {
        String[] args;

        args = new String[]{"-path", "Notebook5.json"};
        Main.main(args);

        args = new String[]{"-add", "1", "text1"};
        Main.main(args);

        args = new String[]{"-add", "2", "text2"};
        Main.main(args);

        args = new String[]{"-add", "3", "text3"};
        Main.main(args);

        Notebook notebook = Notebook.open();

        var expected = List.of("1: text1", "3: text3");
        assertIterableEquals(notebook.getSortedFilteredNotes(
                ZonedDateTime.now().minusDays(5),
                ZonedDateTime.now().plusDays(5),
                new String[]{"3", "1"}), expected);
    }
}

