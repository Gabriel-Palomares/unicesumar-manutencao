import Repository.Library;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    @Test
    public void notifyLoanCreated_shouldPrintEmailNotificationAndNotThrow() {
        Library library = new Library();
        int userId = library.addUser("Test User", "test@example.com", "1234567890", "student", "City", "DOC-1", "ACTIVE");
        int bookId = library.addBook("Test Book", "Author", 2024, "FICTION", 2, 2, "A1", "ISBN-TEST-001");

        NotificationService notificationService = new NotificationService(library);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            assertDoesNotThrow(() -> notificationService.notifyLoanCreated(userId, bookId, "2026-05-27", "2026-06-10", "email"));
        } finally {
            System.setOut(originalOut);
        }

        String output = outputStream.toString();
        assertTrue(output.contains("EMAIL:"), "A notificação deve ser registrada como email.");
        assertTrue(output.contains("Test Book"), "A mensagem deve conter o título do livro.");
        assertTrue(output.contains("2026-06-10"), "A mensagem deve conter a data de devolução.");
    }
}

@274590362247180
