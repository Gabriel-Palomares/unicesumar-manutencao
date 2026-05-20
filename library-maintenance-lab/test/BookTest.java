import Model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void constructor_whenAvailableCopiesGreaterThanTotalCopies_shouldThrowIllegalArgumentException() {

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> {

                    new Book(
                            1,
                            "Test Book",
                            "Author",
                            2024,
                            "FICTION",
                            5,
                            10,
                            "A1",
                            "ISBN-TEST-001"
                    );
                });

        assertEquals(
                "Available copies cannot exceed total copies.",
                exception.getMessage()
        );
    }
}
