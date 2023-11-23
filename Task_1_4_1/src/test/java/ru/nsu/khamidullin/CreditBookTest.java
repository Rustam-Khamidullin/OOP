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

        creditBook.putCreditBookRecord("OSI", 1, Mark.FIVE);
        creditBook.putCreditBookRecord("OOP", 1, Mark.FOUR);

        var cb = creditBook.getCreditBook();

        assertEquals(2, cb.size());
        for (var record : cb) {
            assertTrue(
                    record.equals(new CreditBook.CreditbookRecord("OSI", 1, Mark.FIVE))
                            || record.equals(new CreditBook.CreditbookRecord("OOP", 1, Mark.FOUR))
            );
        }

        assertFalse(creditBook.isIncreasedScholarship(2));

        assertFalse(creditBook.isRedDiploma());
    }

    @Test
    void isRedDiploma() {
        CreditBook creditBook = new CreditBook("Kate Bolotova");

        creditBook.putCreditBookRecord("OSI", 2, Mark.FIVE);
        creditBook.putCreditBookRecord("OOP", 2, Mark.FIVE);
        creditBook.putCreditBookRecord("DUITFCP", 2, Mark.FOUR);
        creditBook.putCreditBookRecord("FIZRA", 2, Mark.FIVE);
        creditBook.putCreditBookRecord("QUALIFICATION_WORK", 2, Mark.FIVE);

        assertTrue(creditBook.isRedDiploma());

        assertFalse(creditBook.isIncreasedScholarship(3));
    }

    @Test
    void averageMark() {
        CreditBook creditBook = new CreditBook("Andrew Kozubenko");

        creditBook.putCreditBookRecord("OSI", 1, Mark.FOUR);
        creditBook.putCreditBookRecord("OOP", 1, Mark.THREE);
        creditBook.putCreditBookRecord("DUITFCP", 1, Mark.FIVE);

        assertTrue(Math.abs(4.0 - creditBook.averageMark()) < 0.001);
    }

    @Test
    void increasedScholarship() {
        CreditBook creditBook = new CreditBook("Rustam");

        creditBook.putCreditBookRecord("OSI", 1, Mark.FIVE);
        creditBook.putCreditBookRecord("OOP", 1, Mark.FIVE);
        creditBook.putCreditBookRecord("DUITFCP", 1, Mark.FIVE);

        assertTrue(creditBook.isIncreasedScholarship(2));
    }
}