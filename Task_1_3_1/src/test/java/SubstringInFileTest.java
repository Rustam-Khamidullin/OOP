import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.SubstringInFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


/**
 * Test Class.
 */
public class SubstringInFileTest {
    static final String directory = "src/test/resources/";

    @Test
    void testSimpleFile1() {
        String file = directory + "File1";
        String substring = "a";

        List<Long> expected = new ArrayList<>(Arrays.asList(0L, 6L, 46L, 58L, 59L));
        List<Long> result = SubstringInFile.findSubstringInFile(file, substring);

        assertIterableEquals(expected, result);
    }

    @Test
    void testSimpleFile2() {
        String file = directory + "File2";
        String substring = "aa";

        List<Long> expected = new ArrayList<>(Arrays.asList(0L, 1L, 2L, 3L));
        List<Long> result = SubstringInFile.findSubstringInFile(file, substring);

        assertIterableEquals(expected, result);
    }

    @Test
    void testSimpleFile3() {
        String file = directory + "File3";
        String substring = "World";

        List<Long> result = SubstringInFile.findSubstringInFile(file, substring);
        List<Long> expected = List.of(7L);

        assertIterableEquals(expected, result);
    }

    @Test
    void testRussianFile4() {
        String file = directory + "File4";
        String substring = new String("а".getBytes(), StandardCharsets.UTF_8); // Russian 'a'

        List<Long> result = SubstringInFile.findSubstringInFile(file, substring);
        List<Long> expected = List.of();

        assertIterableEquals(expected, result);
    }

    @Test
    void testRussianFile5() {
        String file = directory + "File5";
        String substring = new String("абв".getBytes(), StandardCharsets.UTF_8);

        List<Long> result = SubstringInFile.findSubstringInFile(file, substring);
        List<Long> expected = List.of(1L, 43L);

        assertIterableEquals(expected, result);
    }

    @Test
    void testChineseFile6() {
        String file = directory + "File6";
        String substring = new String("﨓﨩".getBytes(), StandardCharsets.UTF_8);

        List<Long> result = SubstringInFile.findSubstringInFile(file, substring);
        List<Long> expected = List.of(25L, 67L);

        assertIterableEquals(expected, result);
    }

    @Test
    void testFullStringFile7() {
        String file = directory + "File7";
        String substring = "asdfgjkl;qwertyuizxcvcvbn";

        List<Long> result = SubstringInFile.findSubstringInFile(file, substring);
        List<Long> expected = List.of(0L);

        assertIterableEquals(expected, result);
    }
}
