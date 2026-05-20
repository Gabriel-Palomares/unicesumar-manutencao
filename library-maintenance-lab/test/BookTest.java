import Repository.Library;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanManagerTest {

    @Test
    public void borrowBook_whenNoCopiesAvailable_shouldThrowIllegalStateException() {
        Library library = new Library();
        int userId = library.addUser("Test User", "test@example.com", "1234567890", "student", "City", "DOC-1", "ACTIVE");
        int bookId = library.addBook("Test Book", "Author", 2024, "FICTION", 1, 0, "A1", "ISBN-TEST-001");

        LoanManager loanManager = new LoanManager(library);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            loanManager.borrowBook(bookId, userId);
        });

        assertEquals("Não foi possível emprestar: Não há cópias disponíveis para empréstimo.", exception.getMessage());
        assertTrue(library.getLoans().isEmpty(), "Nenhum empréstimo deve ser criado quando não há cópias disponíveis.");
    }
}
