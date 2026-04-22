import org.example.Player;
import org.example.RegistrationService;
import org.example.Tournament;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest {

    /**
     * This class tests the `registerPlayer` method of the `RegistrationService` class.
     * The method is responsible for registering a `Player` to a `Tournament`, given
     * that all conditions are met (e.g., player is of eligible age, tournament is open,
     * tournament is not full, etc.).
     */

    @Test
    public void testRegisterPlayer_SuccessfulRegistration() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Player player = new Player(1, "Player1", 20);
        Tournament tournament = new Tournament("Tournament1", 10, 50.0);

        // Act
        registrationService.registerPlayer(player, tournament);

        // Assert
        assertTrue(tournament.hasPlayer(player.getId()));
        assertEquals(9, tournament.getAvailableSlots());
    }

    @Test
    public void testRegisterPlayer_PlayerIsNull() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Tournament tournament = new Tournament("Tournament1", 10, 50.0);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // Act
            registrationService.registerPlayer(null, tournament);
        });
        assertEquals("Player cannot be null.", exception.getMessage());
    }

    @Test
    public void testRegisterPlayer_TournamentIsNull() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Player player = new Player(1, "Player1", 20);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // Act
            registrationService.registerPlayer(player, null);
        });
        assertEquals("Tournament cannot be null.", exception.getMessage());
    }

    @Test
    public void testRegisterPlayer_PlayerUnderage() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Player player = new Player(1, "Player1", 15);
        Tournament tournament = new Tournament("Tournament1", 10, 50.0);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // Act
            registrationService.registerPlayer(player, tournament);
        });
        assertEquals("Player must be at least 16 years old.", exception.getMessage());
    }

    @Test
    public void testRegisterPlayer_TournamentIsClosed() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Player player = new Player(1, "Player1", 20);
        Tournament tournament = new Tournament("Tournament1", 10, 50.0);
        tournament.closeRegistration();

        // Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            // Act
            registrationService.registerPlayer(player, tournament);
        });
        assertEquals("Registration is closed.", exception.getMessage());
    }

    @Test
    public void testRegisterPlayer_TournamentIsFull() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Player player = new Player(1, "Player1", 20);
        Tournament tournament = new Tournament("Tournament1", 1, 50.0);
        tournament.addPlayer(new Player(2, "ExistingPlayer", 25));

        // Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            // Act
            registrationService.registerPlayer(player, tournament);
        });
        assertEquals("Tournament is already full.", exception.getMessage());
    }

    @Test
    public void testRegisterPlayer_PlayerAlreadyRegistered() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Player player = new Player(1, "Player1", 20);
        Tournament tournament = new Tournament("Tournament1", 10, 50.0);
        tournament.addPlayer(player);

        // Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            // Act
            registrationService.registerPlayer(player, tournament);
        });
        assertEquals("Player is already registered.", exception.getMessage());
    }
}
