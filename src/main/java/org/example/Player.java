package org.example;

public class Player {
    private final int id;
    private final String nickname;
    private final int age;

    public Player(int id, String nickname, int age) {
        if (id <= 0) {
            throw new IllegalArgumentException("Player ID must be greater than 0.");
        }
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be empty.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be greater than 0.");
        }

        this.id = id;
        this.nickname = nickname.trim();
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Player{id=" + id + ", nickname='" + nickname + "', age=" + age + "}";
    }
}