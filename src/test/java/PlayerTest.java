package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

    @Test
    void constructorStoresValuesAndTrimsNickname() {
        Player player = new Player(7, "  Ace  ", 21);

        assertEquals(7, player.getId());
        assertEquals("Ace", player.getNickname());
        assertEquals(21, player.getAge());
    }

    @Test
    void constructorRejectsZeroId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Player(0, "Ace", 21));

        assertEquals("Player ID must be greater than 0.", ex.getMessage());
    }

    @Test
    void constructorRejectsNegativeId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Player(-1, "Ace", 21));

        assertEquals("Player ID must be greater than 0.", ex.getMessage());
    }

    @Test
    void constructorRejectsNullNickname() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Player(1, null, 21));

        assertEquals("Nickname cannot be empty.", ex.getMessage());
    }

    @Test
    void constructorRejectsBlankNickname() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Player(1, "   ", 21));

        assertEquals("Nickname cannot be empty.", ex.getMessage());
    }

    @Test
    void constructorRejectsZeroAge() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Player(1, "Ace", 0));

        assertEquals("Age must be greater than 0.", ex.getMessage());
    }

    @Test
    void constructorRejectsNegativeAge() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Player(1, "Ace", -2));

        assertEquals("Age must be greater than 0.", ex.getMessage());
    }

    @Test
    void toStringIncludesAllFields() {
        Player player = new Player(42, "Hero", 19);

        assertEquals("Player{id=42, nickname='Hero', age=19}", player.toString());
    }
}
