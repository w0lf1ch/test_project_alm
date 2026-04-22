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
    @Test
    public void testRegisterPlayer_PlayerHasInvalidAge() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Player player = new Player(1, "Player1", 15); // Возраст меньше 16, чтобы пройти проверку конструктора
        Tournament tournament = new Tournament("Tournament1", 10, 50.0);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.registerPlayer(player, tournament);
        });

        // Проверяем корректность сообщения об исключении
        assertEquals("Player must be at least 16 years old.", exception.getMessage());

        // Убеждаемся, что турнир остался пустым
        assertTrue(tournament.getRegisteredPlayers().isEmpty(), "No players should be registered in the tournament.");
    }
    @Test
    public void testRegisterPlayer_DuplicatePlayerIdWithDifferentName() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Tournament tournament = new Tournament("Tournament1", 10, 50.0);

        Player player1 = new Player(1, "Player1", 20);
        Player player2 = new Player(1, "Player2", 20); // Тот же id, другое имя

        tournament.addPlayer(player1);

        // Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            // Act
            registrationService.registerPlayer(player2, tournament);
        });
        assertEquals("Player is already registered.", exception.getMessage());
    }
    @Test
    public void testRegisterPlayer_MultiplePlayersUntilFull() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Tournament tournament = new Tournament("Tournament1", 3, 50.0); // Лимит: 3 игрока

        Player player1 = new Player(1, "Player1", 20);
        Player player2 = new Player(2, "Player2", 22);
        Player player3 = new Player(3, "Player3", 25);
        Player player4 = new Player(4, "Player4", 26); // Лишний игрок

        // Act
        registrationService.registerPlayer(player1, tournament);
        registrationService.registerPlayer(player2, tournament);
        registrationService.registerPlayer(player3, tournament);

        // Assert: Добавление четвёртого игрока не допускается
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            registrationService.registerPlayer(player4, tournament);
        });
        assertEquals("Tournament is already full.", exception.getMessage());
    }
    @Test
    public void testRegisterPlayer_MinimumEligibleAge() {
        // Arrange
        RegistrationService registrationService = new RegistrationService();
        Player player = new Player(1, "Player1", 16); // Ровно 16 лет
        Tournament tournament = new Tournament("Tournament1", 10, 50.0);

        // Act
        registrationService.registerPlayer(player, tournament);

        // Assert
        assertTrue(tournament.hasPlayer(player.getId()));
        assertEquals(9, tournament.getAvailableSlots());
    }
}
