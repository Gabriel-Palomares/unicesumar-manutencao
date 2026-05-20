import Model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void constructor_whenNameIsBlank_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new User(1, "", "test@example.com", "1234567890", "student", "City", "DOC-1", "ACTIVE");
        });

        assertEquals("Name is required and must not be blank.", exception.getMessage());
    }
}
