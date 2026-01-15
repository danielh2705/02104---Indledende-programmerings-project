package src.model;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class HighScoreService {

        private static final Path FILE_PATH = Paths.get(
                        System.getProperty("user.home"),
                        ".snake",
                        "highscores.txt");

        public static void saveScore(int score) {
                try {
                        Files.createDirectories(FILE_PATH.getParent());

                        String entry = score + " - " + LocalDateTime.now() + System.lineSeparator();

                        Files.writeString(
                                        FILE_PATH,
                                        entry,
                                        StandardOpenOption.CREATE,
                                        StandardOpenOption.APPEND);

                } catch (IOException e) {
                        e.printStackTrace(); // or log
                }
        }

        public static List<Integer> loadTopScores(int limit) {
                if (!Files.exists(FILE_PATH))
                        return List.of();

                try {
                        return Files.readAllLines(FILE_PATH).stream()
                                        .map(line -> line.split(" - ")[0])
                                        .map(Integer::parseInt)
                                        .sorted(Comparator.reverseOrder())
                                        .limit(limit)
                                        .toList();

                } catch (IOException e) {
                        return List.of();
                }
        }

}
