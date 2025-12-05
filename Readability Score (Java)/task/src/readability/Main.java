package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

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
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
            String choice = sc.nextLine();
            rs.calculateAndPrintScore(choice);
            sc.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }


    }
}
