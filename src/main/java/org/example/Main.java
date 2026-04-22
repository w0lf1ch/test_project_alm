package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RegistrationService registrationService = new RegistrationService();
    private static final List<Player> players = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Tournament Registration System ===");

        System.out.print("Enter tournament name: ");
        String tournamentName = scanner.nextLine();

        int maxPlayers = readInt("Enter max number of players: ");
        double entryFee = readDouble("Enter entry fee: ");

        Tournament tournament = new Tournament(tournamentName, maxPlayers, entryFee);

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addPlayer();
                    break;
                case 2:
                    registerPlayerToTournament(tournament);
                    break;
                case 3:
                    showTournamentInfo(tournament);
                    break;
                case 4:
                    showAllPlayers();
                    break;
                case 5:
                    showRegisteredPlayers(tournament);
                    break;
                case 6:
                    closeRegistration(tournament);
                    break;
                case 0:
                    running = false;
                    System.out.println("Program finished.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("1. Add player to system");
        System.out.println("2. Register player to tournament");
        System.out.println("3. Show tournament info");
        System.out.println("4. Show all players");
        System.out.println("5. Show registered players");
        System.out.println("6. Close registration");
        System.out.println("0. Exit");
    }

    private static void addPlayer() {
        try {
            int id = readInt("Enter player ID: ");

            System.out.print("Enter nickname: ");
            String nickname = scanner.nextLine();

            int age = readInt("Enter age: ");

            Player player = new Player(id, nickname, age);
            registrationService.addPlayerToSystem(players, player);

            System.out.println("Player added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerPlayerToTournament(Tournament tournament) {
        try {
            int playerId = readInt("Enter player ID to register: ");
            Player player = registrationService.findPlayerById(players, playerId);

            if (player == null) {
                System.out.println("Player not found.");
                return;
            }

            registrationService.registerPlayer(player, tournament);
            System.out.println("Player registered successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showTournamentInfo(Tournament tournament) {
        System.out.println("=== Tournament Info ===");
        System.out.println("Name: " + tournament.getName());
        System.out.println("Status: " + tournament.getStatus());
        System.out.println("Max players: " + tournament.getMaxPlayers());
        System.out.println("Registered players: " + tournament.getRegisteredPlayers().size());
        System.out.println("Available slots: " + tournament.getAvailableSlots());
        System.out.println("Entry fee: " + tournament.getEntryFee());
        System.out.println("Prize pool: " + tournament.getPrizePool());
    }

    private static void showAllPlayers() {
        System.out.println("=== All Players ===");

        if (players.isEmpty()) {
            System.out.println("No players in the system.");
            return;
        }

        for (Player player : players) {
            System.out.println(player);
        }
    }

    private static void showRegisteredPlayers(Tournament tournament) {
        System.out.println("=== Registered Players ===");

        if (tournament.getRegisteredPlayers().isEmpty()) {
            System.out.println("No registered players.");
            return;
        }

        for (Player player : tournament.getRegisteredPlayers()) {
            System.out.println(player);
        }
    }

    private static void closeRegistration(Tournament tournament) {
        tournament.closeRegistration();
        System.out.println("Registration has been closed.");
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}