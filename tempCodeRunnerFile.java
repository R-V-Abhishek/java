import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

// Custom exception for invalid team size
class InvalidTeamSizeException extends Exception {
    public InvalidTeamSizeException(String message) {
        super(message);
    }
}

// Exception for invalid scores
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

// Abstract class for participants (Teams or Players)
abstract class Participant {
    protected String name; // Name of the team/player

    public Participant(String name) {
        this.name = name;
    }

    public abstract void displayInfo();
}

// Class representing a Team
class Team extends Participant {
    private int teamSize; // Total number of players
    private int wins = 0; // Wins scored by the team
    private ArrayList<Player> players; // List of players in the team

    // Constructor to create a team and validate its size
    public Team(String name, int teamSize) throws InvalidTeamSizeException {
        super(name);
        if (teamSize != 7) { // Kabaddi requires exactly 7 players
            throw new InvalidTeamSizeException("A team must have exactly 7 players.");
        }
        this.teamSize = teamSize;
        this.players = new ArrayList<>();
    }

    // Method to add a player to the team
    public void addPlayer(Player player) {
        players.add(player);
    }

    // Show team details and its players
    public void displayInfo() {
        System.out.println("\nTeam Name: " + name);
        System.out.println("Team Size: " + teamSize);
        System.out.println("Wins: " + wins);
        System.out.println("Players:");
        for (Player player : players) {
            player.displayInfo();
        }
    }

    public void addWin() {
        wins++;
    }

    public int getWins() {
        return wins;
    }

    public String getName() {
        return name;
    }
}

// Class for a Player
class Player extends Participant {
    private Role role; // Role of the player
    private String teamName; // Team to which the player belongs

    public Player(String name, Role role, String teamName) {
        super(name);
        this.role = role;
        this.teamName = teamName;
    }

    // Display player details
    public void displayInfo() {
        System.out.println("  - " + name + " | Role: " + role);
    }
}

// Enum to represent player roles
enum Role {
    RAIDER, DEFENDER, ALL_ROUNDER;
}

// Utility class for common Kabaddi functions
class KabaddiUtils {
    // Generate a random score
    public static int generateRandomScore(int maxScore) {
        return new Random().nextInt(maxScore + 1);
    }

    // Display match result based on scores
    public static void displayMatchResult(String team1, int score1, String team2, int score2) {
        System.out.println("\nMatch Result: " + team1 + " (" + score1 + ") vs " + team2 + " (" + score2 + ")");
        if (score1 > score2) {
            System.out.println("🏆 " + team1 + " wins!");
        } else if (score2 > score1) {
            System.out.println("🏆 " + team2 + " wins!");
        } else {
            System.out.println("It's a tie! Both teams played hard.");
        }
    }
}

// Abstract class for Matches
abstract class Match {
    protected Participant participant1;
    protected Participant participant2;

    public Match(Participant participant1, Participant participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
    }

    public abstract void scheduleMatch();

    public abstract void recordScore(ArrayList<String> matchHistory) throws InvalidScoreException;
}

// Match between two teams
class TeamMatch extends Match {
    public TeamMatch(Team team1, Team team2) {
        super(team1, team2);
    }

    public void scheduleMatch() {
        System.out.println("\n📅 Scheduled Match: " + participant1.name + " vs " + participant2.name);
    }

    public void recordScore(ArrayList<String> matchHistory) throws InvalidScoreException {
        int score1 = KabaddiUtils.generateRandomScore(50);
        int score2 = KabaddiUtils.generateRandomScore(50);

        // Log the match result to history
        String result = participant1.name + " (" + score1 + ") vs " + participant2.name + " (" + score2 + ")";
        matchHistory.add(result);

        // Display the match result
        KabaddiUtils.displayMatchResult(participant1.name, score1, participant2.name, score2);

        if (score1 > score2) {
            ((Team) participant1).addWin();
        } else if (score2 > score1) {
            ((Team) participant2).addWin();
        }
    }
}

// Main Class
public class KabaddiLeagueManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Creating teams
            Team team1 = new Team("Dabang Delhi", 7);
            Team team2 = new Team("U Mumba", 7);

            // Adding players to teams
            team1.addPlayer(new Player("Ajay Thakur", Role.RAIDER, "Dabang Delhi"));
            team1.addPlayer(new Player("Manjeet Chhillar", Role.ALL_ROUNDER, "Dabang Delhi"));
            team1.addPlayer(new Player("Surender Nada", Role.DEFENDER, "Dabang Delhi"));

            team2.addPlayer(new Player("Fazal Atrachali", Role.DEFENDER, "U Mumba"));
            team2.addPlayer(new Player("Sandeep Narwal", Role.ALL_ROUNDER, "U Mumba"));
            team2.addPlayer(new Player("Pawan Sehrawat", Role.RAIDER, "U Mumba"));

            // History of matches
            ArrayList<String> matchHistory = new ArrayList<>();

            // Menu system for interaction
            boolean exit = false;

            while (!exit) {
                System.out.println("\n🏐 --- Kabaddi League Menu --- 🏐");
                System.out.println("1. Display Team Info");
                System.out.println("2. Start Match");
                System.out.println("3. View Match History");
                System.out.println("4. Exit");

                int choice = -1;

                try {
                    System.out.print("Choose an option: ");
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.next();
                }

                switch (choice) {
                    case 1:
                        System.out.println("Choose a team to display: 1. Dabang Delhi, 2. U Mumba");
                        int teamChoice = sc.nextInt();
                        if (teamChoice == 1) team1.displayInfo();
                        else if (teamChoice == 2) team2.displayInfo();
                        else System.out.println("Invalid choice!");
                        break;

                    case 2:
                        System.out.println("\n--- Match Starting ---");
                        TeamMatch match = new TeamMatch(team1, team2);
                        match.scheduleMatch();
                        match.recordScore(matchHistory);
                        break;

                    case 3:
                        System.out.println("\n--- Match History ---");
                        for (String result : matchHistory) {
                            System.out.println(result);
                        }
                        break;

                    case 4:
                        System.out.println("Exiting the Kabaddi League. See you next season! 👋");
                        exit = true;
                        break;

                    default:
                        System.out.println("Oops! Invalid option. Try again.");
                }
            }
        } catch (InvalidTeamSizeException e) {
            System.out.println(e.getMessage());
        }
    }
}
