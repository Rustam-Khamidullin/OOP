package ru.nsu.khamidullin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class.
 */
public class CreditBookTest {
    @Test
    void putCreditBookRecord() {
        CreditBook creditBook = new CreditBook("Ruslan Chudinov");

        creditBook.putCreditBookRecord(Subject.OSI, 1, Mark.FIVE);
        creditBook.putCreditBookRecord(Subject.OOP, 1, Mark.FOUR);

        assertEquals(Mark.FIVE, creditBook.getCreditBook().get(Subject.OSI).get(1));
        assertEquals(Mark.FOUR, creditBook.getCreditBook().get(Subject.OOP).get(1));

        assertFalse(creditBook.isIncreasedScholarship(2));

        assertFalse(creditBook.isRedDiploma());
    }

    @Test
    void isRedDiploma() {
        CreditBook creditBook = new CreditBook("Kate Bolotova");

        creditBook.putCreditBookRecord(Subject.OSI, 2, Mark.FIVE);
        creditBook.putCreditBookRecord(Subject.OOP, 2, Mark.FIVE);
        creditBook.putCreditBookRecord(Subject.DUITFCP, 2, Mark.FOUR);
        creditBook.putCreditBookRecord(Subject.FIZRA, 2, Mark.FIVE);
        creditBook.putCreditBookRecord(Subject.QUALIFICATION_WORK, 2, Mark.FIVE);

        assertTrue(creditBook.isRedDiploma());

        assertFalse(creditBook.isIncreasedScholarship(3));
    }

    @Test
    void averageMark() {
        CreditBook creditBook = new CreditBook("Andrew Kozubenko");

        creditBook.putCreditBookRecord(Subject.OSI, 1, Mark.FOUR);
        creditBook.putCreditBookRecord(Subject.OOP, 1, Mark.THREE);
        creditBook.putCreditBookRecord(Subject.DUITFCP, 1, Mark.FIVE);

        assertTrue(Math.abs(4.0 - creditBook.averageMark()) < 0.001);
    }

    @Test
    void increasedScholarship() {
        CreditBook creditBook = new CreditBook("Rustam");

        creditBook.putCreditBookRecord(Subject.OSI, 1, Mark.FIVE);
        creditBook.putCreditBookRecord(Subject.OOP, 1, Mark.FIVE);
        creditBook.putCreditBookRecord(Subject.DUITFCP, 1, Mark.FIVE);

        assertTrue(creditBook.isIncreasedScholarship(2));
    }
}