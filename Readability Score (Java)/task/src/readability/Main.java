package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide a file name as a command-line argument.");
            return;
        }
        try {
            Path fileName = Paths.get(args[0]);
            String text = Files.readString(fileName);
            ReadabilityScore rs = new ReadabilityScore(text);
            rs.printCounts();
            System.out.println("The score is: " + rs.getScore());
            System.out.printf("The text should be understood by: %s year-olds.", rs.getAgeRange(rs.getScore()));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
