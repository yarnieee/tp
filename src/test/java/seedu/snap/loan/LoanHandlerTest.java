package seedu.snap.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.snap.exceptions.LoanAdditionUnsuccessful;

class LoanHandlerTest {
    @Test
    public void addsLoanUsingDocumentedFormat() throws LoanAdditionUnsuccessful {
        LoanHandler handler = new LoanHandler();

        Loan loan = handler.addLoan("loan id/001 b/012 issued/15-09-26 due/17-09-26");

        assertNotNull(loan);
        assertEquals("001", loan.getItemID());
        assertEquals("012", loan.getBorrowerID());
        assertEquals(LocalDateTime.of(2026, 9, 15, 0, 0), loan.getIssuedDate());
        assertEquals(LocalDateTime.of(2026, 9, 17, 0, 0), loan.getDueDate());
        assertEquals(1, handler.numLoans);
    }

    @Test
    public void addsLoanUsingSlashPrefixedFormat() throws LoanAdditionUnsuccessful {
        LoanHandler handler = new LoanHandler();

        Loan loan = handler.addLoan("loan /id002 /b020 /issued20-02-21 /due20-02-23");

        assertNotNull(loan);
        assertEquals("002", loan.getItemID());
        assertEquals("020", loan.getBorrowerID());
        assertEquals(LocalDateTime.of(2021, 2, 20, 0, 0), loan.getIssuedDate());
        assertEquals(LocalDateTime.of(2023, 2, 20, 0, 0), loan.getDueDate());
    }

    @Test
    public void rejectsIncorrectArgumentCount() {
        LoanHandler handler = new LoanHandler();

        LoanAdditionUnsuccessful exception = assertThrows(LoanAdditionUnsuccessful.class,
                () -> handler.addLoan("loan id/001 b/012 issued/15-09-26"));

        assertEquals("Incorrect number of arguments.", exception.getMessage());
    }

    @Test
    public void rejectsMalformedId() {
        LoanHandler handler = new LoanHandler();

        LoanAdditionUnsuccessful exception = assertThrows(LoanAdditionUnsuccessful.class,
                () -> handler.addLoan("loan /id01 /b020 /issued20-02-21 /due20-02-23"));

        assertEquals("Invalid item ID format. Expected id/[three digits] or /id[three digits].",
                exception.getMessage());
    }

    @Test
    public void rejectsMalformedDate() {
        LoanHandler handler = new LoanHandler();

        LoanAdditionUnsuccessful exception = assertThrows(LoanAdditionUnsuccessful.class,
                () -> handler.addLoan("loan /id001 /b020 /issued31-02-21 /due20-02-23"));

        assertEquals("Invalid date format. Expected issued/DD-MM-YY or /issuedDD-MM-YY.",
                exception.getMessage());
    }

    @Test
    public void rejectsIssuedDateAfterDueDate() throws LoanAdditionUnsuccessful {
        LoanHandler handler = new LoanHandler();

        LoanAdditionUnsuccessful exception = assertThrows(LoanAdditionUnsuccessful.class,
                () -> handler.addLoan("loan /id001 /b020 /issued20-02-23 /due20-02-21"));

        assertEquals("Issued date must not be later than the due date.", exception.getMessage());
    }

    @Test
    public void rejectsActiveDuplicateButAllowsReturnedLoan() throws LoanAdditionUnsuccessful {
        LoanHandler handler = new LoanHandler();
        Loan firstLoan = handler.addLoan("loan id/001 b/012 issued/15-09-26 due/17-09-26");

        assertThrows(LoanAdditionUnsuccessful.class,
                () -> handler.addLoan("loan id/001 b/020 issued/18-09-26 due/19-09-26"));

        firstLoan.setReturned(true);
        Loan replacementLoan = handler.addLoan("loan id/001 b/020 issued/18-09-26 due/19-09-26");

        assertNotNull(replacementLoan);
        assertEquals(2, handler.numLoans);
    }
}
