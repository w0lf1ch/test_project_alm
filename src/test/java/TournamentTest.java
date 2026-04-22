import org.example.Player;
import org.example.Tournament;
import org.example.TournamentStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TournamentTest {

    /**
     * Tests for the addPlayer method in the Tournament class.
     * The addPlayer method is responsible for registering a player into the tournament.
     * It updates the tournament's status based on the number of registered players compared to the maximum allowed.
     */

    @Test
    public void testAddPlayerSuccessfullyAddsPlayer() {
        Tournament tournament = new Tournament("Chess Championship", 5, 20.0);
        Player player = new Player(1, "PlayerOne", 25);

        tournament.addPlayer(player);

        List<Player> registeredPlayers = tournament.getRegisteredPlayers();
        assertEquals(1, registeredPlayers.size());
        assertTrue(registeredPlayers.contains(player));
    }

    @Test
    public void testAddPlayerUpdatesStatusToFullWhenMaxPlayersReached() {
        Tournament tournament = new Tournament("Chess Championship", 2, 10.0);
        Player player1 = new Player(1, "PlayerOne", 30);
        Player player2 = new Player(2, "PlayerTwo", 22);

        tournament.addPlayer(player1);
        tournament.addPlayer(player2);

        assertEquals(TournamentStatus.FULL, tournament.getStatus());
        assertEquals(0, tournament.getAvailableSlots());
    }

    @Test
    public void testAddPlayerDoesNotChangeClosedStatus() {
        Tournament tournament = new Tournament("Chess Championship", 5, 15.0);
        tournament.closeRegistration();
        Player player = new Player(1, "PlayerOne", 27);

        tournament.addPlayer(player);

        assertEquals(TournamentStatus.CLOSED, tournament.getStatus());
        assertTrue(tournament.getRegisteredPlayers().contains(player));
    }

    @Test
    public void testAddPlayerUpdatesStatusToOpenWhenSlotsAvailable() {
        Tournament tournament = new Tournament("Darts Tournament", 3, 5.0);
        Player player = new Player(1, "DartMaster", 28);

        tournament.addPlayer(player);

        assertEquals(TournamentStatus.OPEN, tournament.getStatus());
        assertEquals(2, tournament.getAvailableSlots());
    }

    @Test
    public void testAddMultiplePlayersMaintainsCorrectOrder() {
        Tournament tournament = new Tournament("Football Match", 3, 40.0);
        Player player1 = new Player(1, "Striker", 21);
        Player player2 = new Player(2, "Goalie", 23);

        tournament.addPlayer(player1);
        tournament.addPlayer(player2);

        List<Player> registeredPlayers = tournament.getRegisteredPlayers();
        assertEquals(2, registeredPlayers.size());
        assertEquals(player1, registeredPlayers.get(0));
        assertEquals(player2, registeredPlayers.get(1));
    }

    @Test
    public void testAddPlayerIncrementsPrizePoolCorrectly() {
        Tournament tournament = new Tournament("Running Event", 4, 50.0);
        Player player = new Player(1, "Runner", 19);

        tournament.addPlayer(player);

        assertEquals(50.0, tournament.getPrizePool());
    }

    @Test
    public void testAddPlayerForPreviouslyFullTournament() {
        Tournament tournament = new Tournament("Table Tennis", 1, 15.0);
        Player player1 = new Player(1, "PingPongStar", 20);

        tournament.addPlayer(player1);

        assertEquals(TournamentStatus.FULL, tournament.getStatus());
        assertEquals(1, tournament.getRegisteredPlayers().size());
    }
}