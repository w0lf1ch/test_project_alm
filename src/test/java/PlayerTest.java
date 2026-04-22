import org.example.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Player class.
 * This class contains unit tests for the getId method of the Player class.
 */
public class PlayerTest {

    /**
     * Test to verify that getId correctly returns the ID provided during Player object creation.
     */
    @Test
    public void testGetId_validId() {
        // Arrange
        int id = 5;
        String nickname = "Gamer";
        int age = 25;
        Player player = new Player(id, nickname, age);

        // Act
        int result = player.getId();

        // Assert
        assertEquals(id, result, "The returned ID should match the ID provided during object creation.");
    }

    /**
     * Test to verify that getId correctly returns the ID when the ID is a large, positive number.
     */
    @Test
    public void testGetId_largeId() {
        // Arrange
        int id = 999999;
        String nickname = "ProGamer";
        int age = 30;
        Player player = new Player(id, nickname, age);

        // Act
        int result = player.getId();

        // Assert
        assertEquals(id, result, "The returned ID should match the large ID provided during object creation.");
    }

}