package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        ReadabilityScore rs = new ReadabilityScore(text);
        Score score = rs.getScore();
        System.out.println(score.name());

    }
}
