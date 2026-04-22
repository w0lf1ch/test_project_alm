package org.example;

import java.util.List;

public class RegistrationService {

    public void addPlayerToSystem(List<Player> players, Player player) {
        if (players == null) {
            throw new IllegalArgumentException("Players list cannot be null.");
        }
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }

        for (Player existingPlayer : players) {
            if (existingPlayer.getId() == player.getId()) {
                throw new IllegalArgumentException("Player with this ID already exists.");
            }
        }

        players.add(player);
    }

    public Player findPlayerById(List<Player> players, int playerId) {
        if (players == null) {
            throw new IllegalArgumentException("Players list cannot be null.");
        }

        for (Player player : players) {
            if (player.getId() == playerId) {
                return player;
            }
        }

        return null;
    }

    public void registerPlayer(Player player, Tournament tournament) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament cannot be null.");
        }
        if (player.getAge() < 16) {
            throw new IllegalArgumentException("Player must be at least 16 years old.");
        }
        if (tournament.getStatus() == TournamentStatus.CLOSED) {
            throw new IllegalStateException("Registration is closed.");
        }
        if (tournament.getStatus() == TournamentStatus.FULL || tournament.getAvailableSlots() == 0) {
            throw new IllegalStateException("Tournament is already full.");
        }
        if (tournament.hasPlayer(player.getId())) {
            throw new IllegalStateException("Player is already registered.");
        }

        tournament.addPlayer(player);
    }
}