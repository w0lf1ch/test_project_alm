package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationServiceTest {

    private final RegistrationService service = new RegistrationService();

    @Test
    void addPlayerToSystemRejectsNullPlayersList() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.addPlayerToSystem(null, new Player(1, "A", 20)));

        assertEquals("Players list cannot be null.", ex.getMessage());
    }

    @Test
    void addPlayerToSystemRejectsNullPlayer() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.addPlayerToSystem(new ArrayList<>(), null));

        assertEquals("Player cannot be null.", ex.getMessage());
    }

    @Test
    void addPlayerToSystemRejectsDuplicateId() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "A", 20));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.addPlayerToSystem(players, new Player(1, "B", 22)));

        assertEquals("Player with this ID already exists.", ex.getMessage());
        assertEquals(1, players.size());
    }

    @Test
    void addPlayerToSystemAddsUniquePlayer() {
        List<Player> players = new ArrayList<>();
        Player player = new Player(2, "A", 20);

        service.addPlayerToSystem(players, player);

        assertEquals(1, players.size());
        assertEquals(player, players.get(0));
    }

    @Test
    void findPlayerByIdRejectsNullList() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findPlayerById(null, 1));

        assertEquals("Players list cannot be null.", ex.getMessage());
    }

    @Test
    void findPlayerByIdReturnsMatchingPlayer() {
        List<Player> players = new ArrayList<>();
        Player expected = new Player(3, "FindMe", 20);
        players.add(new Player(1, "A", 20));
        players.add(expected);

        Player actual = service.findPlayerById(players, 3);

        assertEquals(expected, actual);
    }

    @Test
    void findPlayerByIdReturnsNullWhenMissing() {
        List<Player> players = List.of(new Player(1, "A", 20));

        Player result = service.findPlayerById(players, 99);

        assertNull(result);
    }

    @Test
    void registerPlayerRejectsNullPlayer() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.registerPlayer(null, tournament));

        assertEquals("Player cannot be null.", ex.getMessage());
    }

    @Test
    void registerPlayerRejectsNullTournament() {
        Player player = new Player(1, "A", 20);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.registerPlayer(player, null));

        assertEquals("Tournament cannot be null.", ex.getMessage());
    }

    @Test
    void registerPlayerRejectsUnderagePlayer() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);
        Player player = new Player(1, "A", 15);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.registerPlayer(player, tournament));

        assertEquals("Player must be at least 16 years old.", ex.getMessage());
    }

    @Test
    void registerPlayerRejectsWhenTournamentClosed() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);
        tournament.closeRegistration();
        Player player = new Player(1, "A", 20);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> service.registerPlayer(player, tournament));

        assertEquals("Registration is closed.", ex.getMessage());
    }

    @Test
    void registerPlayerRejectsWhenTournamentFull() {
        Tournament tournament = new Tournament("Cup", 1, 10.0);
        tournament.addPlayer(new Player(9, "Existing", 20));
        Player player = new Player(1, "A", 20);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> service.registerPlayer(player, tournament));

        assertEquals("Tournament is already full.", ex.getMessage());
    }

    @Test
    void registerPlayerRejectsAlreadyRegisteredPlayer() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);
        Player player = new Player(1, "A", 20);
        tournament.addPlayer(player);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> service.registerPlayer(player, tournament));

        assertEquals("Player is already registered.", ex.getMessage());
    }

    @Test
    void registerPlayerAcceptsPlayerAtMinimumAge() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);
        Player player = new Player(1, "A", 16);

        service.registerPlayer(player, tournament);

        assertTrue(tournament.hasPlayer(1));
        assertEquals(1, tournament.getRegisteredPlayers().size());
        assertEquals(1, tournament.getAvailableSlots());
    }

    @Test
    void registerPlayerSuccessfullyRegistersEligiblePlayer() {
        Tournament tournament = new Tournament("Cup", 3, 10.0);
        Player player = new Player(7, "A", 25);

        service.registerPlayer(player, tournament);

        assertTrue(tournament.hasPlayer(7));
        assertEquals(1, tournament.getRegisteredPlayers().size());
        assertEquals(TournamentStatus.OPEN, tournament.getStatus());
    }

    @Test
    void registerPlayerChecksAgeBeforeTournamentStatus() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);
        tournament.closeRegistration();
        Player underage = new Player(1, "A", 15);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.registerPlayer(underage, tournament));

        assertEquals("Player must be at least 16 years old.", ex.getMessage());
    }

    @Test
    void registerPlayerChecksFullBeforeDuplicate() {
        Tournament tournament = new Tournament("Cup", 1, 10.0);
        Player player = new Player(1, "A", 20);
        tournament.addPlayer(player);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> service.registerPlayer(player, tournament));

        assertEquals("Tournament is already full.", ex.getMessage());
    }
}
