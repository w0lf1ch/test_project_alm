package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void mainRunsHappyPathAndExits() {
        String input = String.join(System.lineSeparator(),
                "Test Tournament",
                "2",
                "100.0",
                "0") + System.lineSeparator();

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output));

            Main.main(new String[0]);
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }

        String console = output.toString(StandardCharsets.UTF_8);
        assertTrue(console.contains("=== Tournament Registration System ==="));
        assertTrue(console.contains("Program finished."));
    }
}
