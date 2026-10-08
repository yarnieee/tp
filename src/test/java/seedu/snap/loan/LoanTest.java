package seedu.snap.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class LoanTest {
    @Test
    public void createsLoanWithExpectedValues() {
        LocalDateTime issuedDate = LocalDateTime.of(2026, 9, 15, 0, 0);
        LocalDateTime dueDate = LocalDateTime.of(2026, 9, 17, 0, 0);
        Loan loan = new Loan("001", "012", issuedDate, dueDate);

        assertEquals("001", loan.getItemID());
        assertEquals("012", loan.getBorrowerID());
        assertEquals(issuedDate, loan.getIssuedDate());
        assertEquals(dueDate, loan.getDueDate());
        assertFalse(loan.isReturned());
    }

    @Test
    public void updatesLoanValues() {
        Loan loan = new Loan("001", "012", LocalDateTime.MIN, LocalDateTime.MAX);
        LocalDateTime issuedDate = LocalDateTime.of(2026, 9, 15, 0, 0);
        LocalDateTime dueDate = LocalDateTime.of(2026, 9, 17, 0, 0);

        loan.setItemID("002");
        loan.setBorrowerID("020");
        loan.setIssuedDate(issuedDate);
        loan.setDueDate(dueDate);
        loan.setReturned(true);

        assertEquals("002", loan.getItemID());
        assertEquals("020", loan.getBorrowerID());
        assertEquals(issuedDate, loan.getIssuedDate());
        assertEquals(dueDate, loan.getDueDate());
        assertTrue(loan.isReturned());
    }
}
