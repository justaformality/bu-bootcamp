package module2;
import java.io.*;
import java.util.ArrayList;

public class GradeAnalyzer {

    public static void main(String[] args) {
        // Step 1: read scores from file
        ArrayList<Integer> scores = readScores("scores.txt");

        if (scores.isEmpty()) {
            System.out.println("No valid scores found.");
            return;
        }
        // Step 2: calculate statistics
        double average = calculateAverage(scores);

        // Step 3: write and print report
        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;

        for (int score : scores) {

            if (score > highest) {
                highest = score;
            }

            if (score < lowest) {
                lowest = score;
            }
        }

        writeReport(
            scores,
            average,
            highest,
            lowest,
            "report.txt"
        );
    }

    // Returns a list of valid scores read from the file
    public static ArrayList<Integer> readScores(String filename) {

        ArrayList<Integer> scores = new ArrayList<Integer>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) {
                    System.out.println("Warning: blank line skipped.");
                    continue;
                }

                try {
                    int score = Integer.parseInt(line);

                    if (score >= 0 && score <= 100) {
                        scores.add(score);
                    } else {
                        System.out.println(
                            "Warning: invalid score skipped: " + line
                        );
                    }

                } catch (NumberFormatException e) {
                    System.out.println(
                        "Warning: invalid line skipped: " + line
                    );
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return scores;
    }

    // Returns the average of a list of scores, or 0.0 if the list is empty
    public static double calculateAverage(ArrayList<Integer> scores) {

        if (scores.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;

        for (int score : scores) {
            total += score;
        }
        return total / scores.size();
    }

    // Writes and prints the report
    public static void writeReport(ArrayList<Integer> scores,
                                   double avg, int high, int low,
                                   String outputFile) {

        int countA = 0;
        int countB = 0;
        int countC = 0;
        int countD = 0;
        int countF = 0;

        for (int score : scores) {

            if (score >= 90) {
                countA++;
            } else if (score >= 80) {
                countB++;
            } else if (score >= 70) {
                countC++;
            } else if (score >= 60) {
                countD++;
            } else {
                countF++;
            }
        }
        String report = String.format(
            "=== Grade Analysis Report ===%n" +
            "Total scores processed: %d%n%n" +
            "Average score: %.2f%n" +
            "Highest score: %d%n" +
            "Lowest score: %d%n%n" +
            "Grade distribution:%n" +
            "  A (90-100): %d%n" +
            "  B (80-89):  %d%n" +
            "  C (70-79):  %d%n" +
            "  D (60-69):  %d%n" +
            "  F (below 60): %d%n",
            scores.size(),
            avg,
            high,
            low,
            countA,
            countB,
            countC,
            countD,
            countF
        );

        System.out.print(report);

    }
}