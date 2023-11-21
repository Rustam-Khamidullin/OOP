package ru.nsu.khamidullin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SubstringInFile} class provides a method to search for a substring in a file and
 * retrieve the positions of its occurrences.
 */
public class SubstringInFile {

    /**
     * The minimum buffer size for reading data from the file.
     */
    private static final int MIN_BUFFER_SIZE = 1 << 23;

    /**
     * Searches for occurrences of a specified substring in the given file and returns the positions
     * of the substring within the file.
     *
     * @param file      The path to the file to be searched.
     * @param substring The substring to search for in the file.
     * @return A list of positions (offsets) of the substring in the file. An empty list is returned
     * if the substring is not found or an error occurs during file processing.
     */
    public static List<Long> findSubstringInFile(
            String file, String substring, boolean fromResources) {
        ArrayList<Long> result = new ArrayList<>();

        InputStream inputStream;
        try {
            if (fromResources) {
                inputStream = SubstringInFile.class.getClassLoader().getResourceAsStream(file);
            } else {
                inputStream = new FileInputStream(file);
            }
            if (inputStream == null) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            return List.of();
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            int substringLength = substring.length();
            int bufferSize = substringLength;
            do {
                bufferSize <<= 1;
            } while (bufferSize < MIN_BUFFER_SIZE);
            int charsToRead = bufferSize - substringLength;

            char[] buffer = new char[bufferSize];
            int charsRead;

            if (reader.read(buffer, 0, substringLength) < substringLength) {
                return result;
            }

            long currentOffset = 0;
            String blockContent;
            while ((charsRead = reader.read(buffer, substringLength, charsToRead)) != -1) {
                blockContent = new String(buffer, 0, charsRead + substringLength);

                for (int i = 0; i < charsRead; ) {
                    int index = blockContent.indexOf(substring, i);
                    if (index == -1 || index >= charsRead) {
                        break;
                    }
                    i = index + 1;
                    result.add(currentOffset + index);
                }

                System.arraycopy(buffer, charsRead, buffer, 0, substringLength);
                currentOffset += charsRead;
            }
            blockContent = new String(buffer, 0, substringLength);
            if (blockContent.equals(substring)) {
                result.add(currentOffset);
            }
        } catch (IOException e) {
            return List.of();
        }
        return result;
    }
}
