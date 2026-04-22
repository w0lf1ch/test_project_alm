import org.example.Main;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    /**
     * Test for the main method in Main class.
     * The main method is responsible for launching the tournament registration system,
     * displaying menu options, and handling user input to interact with the system.
     */

    @Test
    void testMainShowsMenuAndTerminatesProperly() {
        // Simulate user input to exercise main method functionality
        // The input sequence includes a valid tournament name, max players, entry fee,
        // a menu choice, and the exit option.
        String simulatedInput = """
                Test Tournament
                10
                100.0
                0
                """;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture the output produced by the main method
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Execute main method
        Main.main(new String[]{});

        // The output should include the tournament registration system menu and confirmation
        // that the program finished after the termination choice.
        String output = outputStream.toString();
        assertTrue(output.contains("=== Tournament Registration System ==="));
        assertTrue(output.contains("Enter tournament name:"));
        assertTrue(output.contains("Program finished."));
    }
}