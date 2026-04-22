package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TournamentTest {

    @Test
    void constructorStoresValuesAndDefaultsToOpen() {
        Tournament tournament = new Tournament("  Spring Cup  ", 3, 25.0);

        assertEquals("Spring Cup", tournament.getName());
        assertEquals(3, tournament.getMaxPlayers());
        assertEquals(25.0, tournament.getEntryFee());
        assertEquals(TournamentStatus.OPEN, tournament.getStatus());
        assertEquals(3, tournament.getAvailableSlots());
        assertEquals(0.0, tournament.getPrizePool());
        assertTrue(tournament.getRegisteredPlayers().isEmpty());
    }

    @Test
    void constructorRejectsNullName() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Tournament(null, 3, 25.0));

        assertEquals("Tournament name cannot be empty.", ex.getMessage());
    }

    @Test
    void constructorRejectsBlankName() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Tournament("  ", 3, 25.0));

        assertEquals("Tournament name cannot be empty.", ex.getMessage());
    }

    @Test
    void constructorRejectsZeroMaxPlayers() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Tournament("Cup", 0, 25.0));

        assertEquals("Max players must be greater than 0.", ex.getMessage());
    }

    @Test
    void constructorRejectsNegativeMaxPlayers() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Tournament("Cup", -1, 25.0));

        assertEquals("Max players must be greater than 0.", ex.getMessage());
    }

    @Test
    void constructorRejectsNegativeEntryFee() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Tournament("Cup", 2, -0.01));

        assertEquals("Entry fee cannot be negative.", ex.getMessage());
    }

    @Test
    void registeredPlayersViewIsUnmodifiable() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);

        List<Player> players = tournament.getRegisteredPlayers();

        assertThrows(UnsupportedOperationException.class,
                () -> players.add(new Player(1, "A", 20)));
    }

    @Test
    void hasPlayerReturnsFalseWhenNotRegistered() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);

        assertFalse(tournament.hasPlayer(99));
    }

    @Test
    void addPlayerMakesPlayerDiscoverable() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);
        Player player = new Player(1, "A", 20);

        tournament.addPlayer(player);

        assertTrue(tournament.hasPlayer(1));
        assertEquals(1, tournament.getRegisteredPlayers().size());
        assertEquals(1, tournament.getAvailableSlots());
        assertEquals(10.0, tournament.getPrizePool());
        assertEquals(TournamentStatus.OPEN, tournament.getStatus());
    }

    @Test
    void addPlayerSetsStatusToFullAtCapacity() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);

        tournament.addPlayer(new Player(1, "A", 20));
        tournament.addPlayer(new Player(2, "B", 21));

        assertEquals(TournamentStatus.FULL, tournament.getStatus());
        assertEquals(0, tournament.getAvailableSlots());
        assertEquals(20.0, tournament.getPrizePool());
    }

    @Test
    void addPlayerAfterCapacityKeepsFullStatusAndGoesOverbooked() {
        Tournament tournament = new Tournament("Cup", 1, 10.0);

        tournament.addPlayer(new Player(1, "A", 20));
        tournament.addPlayer(new Player(2, "B", 21));

        assertEquals(TournamentStatus.FULL, tournament.getStatus());
        assertEquals(-1, tournament.getAvailableSlots());
        assertEquals(2, tournament.getRegisteredPlayers().size());
        assertEquals(20.0, tournament.getPrizePool());
    }

    @Test
    void closeRegistrationSetsStatusClosed() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);

        tournament.closeRegistration();

        assertEquals(TournamentStatus.CLOSED, tournament.getStatus());
    }

    @Test
    void addPlayerAfterCloseKeepsClosedStatus() {
        Tournament tournament = new Tournament("Cup", 2, 10.0);
        tournament.closeRegistration();

        tournament.addPlayer(new Player(1, "A", 20));

        assertEquals(TournamentStatus.CLOSED, tournament.getStatus());
        assertEquals(1, tournament.getRegisteredPlayers().size());
    }
}
