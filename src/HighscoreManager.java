package src;

import java.io.*;
import java.util.*;


//Written by Adrian
public class HighscoreManager {
    private static final String FILE = "highscores.txt";
    private static final int MAX_SCORES = 10;

    public static List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                scores.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            // File may not exist yet → that's fine
        }

        return scores;
    }

    public static void addScore(int newScore) {
        List<Integer> scores = loadScores();
        scores.add(newScore);

        scores.sort(Collections.reverseOrder());

        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }

        saveScores(scores);
    }

    private static void saveScores(List<Integer> scores) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (int score : scores) {
                pw.println(score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
