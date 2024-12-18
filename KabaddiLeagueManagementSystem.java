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

    public Team(String name, int teamSize) throws InvalidTeamSizeException {
        super(name);
        if (teamSize != 7) { // Kabaddi requires exactly 7 players
            throw new InvalidTeamSizeException("A team must have exactly 7 players.");
        }
        this.teamSize = teamSize;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

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
}

// Class for a Player
class Player extends Participant {
    private Role role; // Role of the player

    public Player(String name, Role role) {
        super(name);
        this.role = role;
    }

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
    public static int generateRandomScore(int maxScore) {
        return new Random().nextInt(maxScore + 1);
    }

    public static void displayMatchResult(String team1, int score1, String team2, int score2) {
        System.out.println("\nMatch Result: " + team1 + " (" + score1 + ") vs " + team2 + " (" + score2 + ")");
        if (score1 > score2) {
            System.out.println(team1 + " wins!");
        } else if (score2 > score1) {
            System.out.println(team2 + " wins!");
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
        System.out.println("\n Scheduled Match: " + participant1.name + " vs " + participant2.name);
    }

    public void recordScore(ArrayList<String> matchHistory) throws InvalidScoreException {
        int score1 = KabaddiUtils.generateRandomScore(50);
        int score2 = KabaddiUtils.generateRandomScore(50);

        String result = participant1.name + " (" + score1 + ") vs " + participant2.name + " (" + score2 + ")";
        matchHistory.add(result);

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
            Team team3 = new Team("Patna Pirates", 7);
            Team team4 = new Team("Bengal Warriors", 7);

            // Adding players to teams
            team1.addPlayer(new Player("Ajay Thakur", Role.RAIDER));
            team1.addPlayer(new Player("Manjeet Chhillar", Role.ALL_ROUNDER));
            team1.addPlayer(new Player("Surender Nada", Role.DEFENDER));
            team1.addPlayer(new Player("Nilesh Salunke", Role.RAIDER));
            team1.addPlayer(new Player("Ravinder Pahal", Role.DEFENDER));
            team1.addPlayer(new Player("Joginder Narwal", Role.DEFENDER));
            team1.addPlayer(new Player("Mohit Chhillar", Role.ALL_ROUNDER));

            team2.addPlayer(new Player("Fazal Atrachali", Role.DEFENDER));
            team2.addPlayer(new Player("Sandeep Narwal", Role.ALL_ROUNDER));
            team2.addPlayer(new Player("Pawan Sehrawat", Role.RAIDER));
            team2.addPlayer(new Player("Surender Singh", Role.ALL_ROUNDER));
            team2.addPlayer(new Player("Rohit Baliyan", Role.RAIDER));
            team2.addPlayer(new Player("Shrikant Jadhav", Role.RAIDER));
            team2.addPlayer(new Player("Dinesh Kumar", Role.DEFENDER));

            team3.addPlayer(new Player("Pardeep Narwal", Role.RAIDER));
            team3.addPlayer(new Player("Maninder Singh", Role.ALL_ROUNDER));
            team3.addPlayer(new Player("Neeraj Kumar", Role.DEFENDER));
            team3.addPlayer(new Player("Jang Kun Lee", Role.RAIDER));
            team3.addPlayer(new Player("Rohit Gulia", Role.RAIDER));
            team3.addPlayer(new Player("Rinku Narwal", Role.DEFENDER));
            team3.addPlayer(new Player("Rajesh Narwal", Role.ALL_ROUNDER));

            team4.addPlayer(new Player("Deepak Hooda", Role.ALL_ROUNDER));
            team4.addPlayer(new Player("K. Prapanjan", Role.RAIDER));
            team4.addPlayer(new Player("Maninder Singh", Role.ALL_ROUNDER));
            team4.addPlayer(new Player("Baldev Singh", Role.DEFENDER));
            team4.addPlayer(new Player("Vinod Kumar", Role.RAIDER));
            team4.addPlayer(new Player("Ran Singh", Role.ALL_ROUNDER));
            team4.addPlayer(new Player("B. Ajay Kumar", Role.DEFENDER));

            ArrayList<String> matchHistory = new ArrayList<>();
            boolean exit = false;

            while (!exit) {
                System.out.println("\n--- Kabaddi League Menu ---");
                System.out.println("1. Display Team Info");
                System.out.println("2. Schedule Matches");
                System.out.println("3. Display Winner");
                System.out.println("4. View Match History");
                System.out.println("5. Exit");

                int choice = -1;

                try {
                    System.out.print("Choose an option: ");
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.next(); // Clear the invalid input
                }

                switch (choice) {
                    case 1:
                        System.out.println("Choose a team to display: 1. Dabang Delhi, 2. U Mumba, 3. Patna Pirates, 4. Bengal Warriors");
                        int teamChoice = sc.nextInt();
                        switch (teamChoice) {
                            case 1:
                                team1.displayInfo();
                                break;
                            case 2:
                                team2.displayInfo();
                                break;
                            case 3:
                                team3.displayInfo();
                                break;
                            case 4:
                                team4.displayInfo();
                                break;
                            default:
                                System.out.println("Invalid team choice!");
                        }
                        break;

                    case 2:
                        // Round-robin scheduling
                        ArrayList<Team> teams = new ArrayList<>();
                        teams.add(team1);
                        teams.add(team2);
                        teams.add(team3);
                        teams.add(team4);

                        for (int i = 0; i < teams.size(); i++) {
                            for (int j = i + 1; j < teams.size(); j++) {
                                TeamMatch match = new TeamMatch(teams.get(i), teams.get(j));
                                match.scheduleMatch();
                                try {
                                    match.recordScore(matchHistory);
                                } catch (InvalidScoreException e) {
                                    System.out.println("Error recording match scores: " + e.getMessage());
                                }
                            }
                        }
                        break;

                    case 3:
                        Team winner = team1; // Assume team1 initially as the champion
                        if (team2.getWins() > winner.getWins()) winner = team2;
                        if (team3.getWins() > winner.getWins()) winner = team3;
                        if (team4.getWins() > winner.getWins()) winner = team4;

                        System.out.println("\nThe Champion Team is: " + winner.name + " with " + winner.getWins() + " wins!");
                        break;

                    case 4:
                        System.out.println("\nMatch History:");
                        for (String result : matchHistory) {
                            System.out.println(result);
                        }
                        break;

                    case 5:
                        System.out.println("Thank you for using the Kabaddi League Management System!");
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }

        } catch (InvalidTeamSizeException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
