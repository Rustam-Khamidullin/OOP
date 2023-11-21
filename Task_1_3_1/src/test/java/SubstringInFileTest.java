import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.SubstringInFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;


/**
 * Test Class.
 */
public class SubstringInFileTest {
    @Test
    void testSimpleFile1() {
        String substring = "a";

        List<Long> expected = new ArrayList<>(Arrays.asList(0L, 6L, 46L, 58L, 59L));
        List<Long> result = SubstringInFile.findSubstringInFile("File1", substring, true);

        assertIterableEquals(expected, result);
    }

    @Test
    void testSimpleFile2() {
        String substring = "aa";

        List<Long> expected = new ArrayList<>(Arrays.asList(0L, 1L, 2L, 3L));
        List<Long> result = SubstringInFile.findSubstringInFile("File2", substring, true);

        assertIterableEquals(expected, result);
    }

    @Test
    void testSimpleFile3() {
        String substring = "World";

        List<Long> result = SubstringInFile.findSubstringInFile("File3", substring, true);
        List<Long> expected = List.of(7L);

        assertIterableEquals(expected, result);
    }

    @Test
    void testRussianFile4() {
        String substring = new String("а".getBytes(), StandardCharsets.UTF_8); // Russian 'a'

        List<Long> result = SubstringInFile.findSubstringInFile("File4", substring, true);
        List<Long> expected = List.of();

        assertIterableEquals(expected, result);
    }

    @Test
    void testRussianFile5() {
        String substring = new String("абв".getBytes(), StandardCharsets.UTF_8);

        List<Long> result = SubstringInFile.findSubstringInFile("File5", substring, true);
        List<Long> expected = List.of(1L, 43L);

        assertIterableEquals(expected, result);
    }

    @Test
    void testChineseFile6() {
        String substring = new String("﨓﨩".getBytes(), StandardCharsets.UTF_8);

        List<Long> result = SubstringInFile.findSubstringInFile("File6", substring, true);
        List<Long> expected = List.of(25L, 67L);

        assertIterableEquals(expected, result);
    }

    @Test
    void testFullStringFile7() {
        String substring = "asdfgjkl;qwertyuizxcvcvbn";

        List<Long> result = SubstringInFile.findSubstringInFile("File7", substring, true);
        List<Long> expected = List.of(0L);

        assertIterableEquals(expected, result);
    }


    void testLargeFileStringFile() {
        try {
            var tempFilePath = Files.createTempFile("testLargeFile", ".txt");

            try (var writer = Files.newBufferedWriter(tempFilePath)) {
                String string = "b".repeat(1000);

                for (long i = 1; i <= 10_000_000L; i++) {
                    writer.write(string);
                }

                writer.write("a");
            }

            String substring = "a";

            List<Long> result = SubstringInFile.findSubstringInFile(tempFilePath.toString(), substring, false);
            List<Long> expected = List.of(10_000_000_000L);

            assertIterableEquals(expected, result);

            Files.deleteIfExists(tempFilePath);
        } catch (Exception ignored) {
        }
    }
}
