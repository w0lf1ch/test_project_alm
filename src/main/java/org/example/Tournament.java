package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tournament {
    private final String name;
    private final int maxPlayers;
    private final double entryFee;
    private final List<Player> registeredPlayers;
    private TournamentStatus status;

    public Tournament(String name, int maxPlayers, double entryFee) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tournament name cannot be empty.");
        }
        if (maxPlayers <= 0) {
            throw new IllegalArgumentException("Max players must be greater than 0.");
        }
        if (entryFee < 0) {
            throw new IllegalArgumentException("Entry fee cannot be negative.");
        }

        this.name = name.trim();
        this.maxPlayers = maxPlayers;
        this.entryFee = entryFee;
        this.registeredPlayers = new ArrayList<>();
        this.status = TournamentStatus.OPEN;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public double getEntryFee() {
        return entryFee;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public List<Player> getRegisteredPlayers() {
        return Collections.unmodifiableList(registeredPlayers);
    }

    public int getAvailableSlots() {
        return maxPlayers - registeredPlayers.size();
    }

    public double getPrizePool() {
        return entryFee * registeredPlayers.size();
    }

    public boolean hasPlayer(int playerId) {
        for (Player player : registeredPlayers) {
            if (player.getId() == playerId) {
                return true;
            }
        }
        return false;
    }

    public void addPlayer(Player player) {
        registeredPlayers.add(player);
        updateStatus();
    }

    public void closeRegistration() {
        status = TournamentStatus.CLOSED;
    }

    private void updateStatus() {
        if (registeredPlayers.size() >= maxPlayers) {
            status = TournamentStatus.FULL;
        } else if (status != TournamentStatus.CLOSED) {
            status = TournamentStatus.OPEN;
        }
    }
}