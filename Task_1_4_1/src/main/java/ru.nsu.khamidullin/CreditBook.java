package ru.nsu.khamidullin;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a student's credit book with marks for different subjects over terms.
 * Provides methods for adding records, checking for a red diploma, calculating average marks,
 * and determining eligibility for an increased scholarship.
 */
public class CreditBook {
    private final String name;
    private final Map<Subject, TreeMap<Integer, Mark>> creditBook;

    /**
     * Constructs a new {@code CreditBook} with the specified student name.
     *
     * @param name The name of the student.
     */
    public CreditBook(String name) {
        this.name = name;
        creditBook = new HashMap<>();
        for (var subject : Subject.values()) {
            creditBook.put(subject, new TreeMap<>());
        }
    }

    /**
     * Adds a credit book record for a subject, term, and mark.
     *
     * @param subject The subject.
     * @param term    The term.
     * @param mark    The mark.
     */
    public void putCreditBookRecord(Subject subject, int term, Mark mark) {
        creditBook.get(subject).put(term, mark);
    }

    /**
     * Checks if the student is eligible for a red diploma based on marks.
     *
     * @return {@code true} if eligible; otherwise, {@code false}.
     */
    public boolean isRedDiploma() {
        int countFiveForLastTerm = 0;
        int countNotQW = 0;

        for (var subject : creditBook.keySet()) {
            var marks = creditBook.get(subject);

            if (marks.isEmpty()) {
                return false;
            }

            int lastTerm = marks.lastKey();

            if (subject == Subject.QUALIFICATION_WORK) {
                if (marks.get(lastTerm) != Mark.FIVE) {
                    return false;
                }
                continue;
            }

            if (marks.get(lastTerm) == Mark.FIVE) {
                countFiveForLastTerm++;
            }

            countNotQW++;
        }

        return countFiveForLastTerm * 100 >= countNotQW * 75;
    }

    /**
     * Calculates the average mark across all subjects and terms.
     *
     * @return The average mark or 0 if the credit book is empty.
     */
    public double averageMark() {
        return creditBook.values().stream()
                .flatMapToInt(termMarks -> termMarks.values().stream().mapToInt(Mark::getMark))
                .average()
                .orElse(0);
    }

    /**
     * Checks if the student qualifies for an increased scholarship in a term.
     *
     * @param term The term to check.
     * @return {@code true} if eligible; otherwise, {@code false}.
     */
    public boolean isIncreasedScholarship(int term) {
        if (term <= 1) {
            return false;
        }

        return creditBook.values().stream()
                .flatMap(termMarks -> termMarks.entrySet().stream())
                .filter(entry -> entry.getKey() == term - 1)
                .allMatch(entry -> entry.getValue() == Mark.FIVE);

    }

    /**
     * Gets the student's name.
     *
     * @return The student's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the credit book with subject-wise term marks.
     *
     * @return A map of subjects to term-wise marks.
     */
    public Map<Subject, TreeMap<Integer, Mark>> getCreditBook() {
        return creditBook;
    }
}
