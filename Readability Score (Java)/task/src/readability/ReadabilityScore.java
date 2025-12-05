package readability;

import java.util.HashMap;
import java.util.Map;

public class ReadabilityScore {
    private final String text;
    private static final Map<Integer, String> scoreMap = new HashMap<>();

    static {
        scoreMap.put(1, "6");
        scoreMap.put(2, "7");
        scoreMap.put(3, "8");
        scoreMap.put(4, "9");
        scoreMap.put(5, "10");
        scoreMap.put(6, "11");
        scoreMap.put(7, "12");
        scoreMap.put(8, "13");
        scoreMap.put(9, "14");
        scoreMap.put(10, "15");
        scoreMap.put(11, "16");
        scoreMap.put(12, "17");
        scoreMap.put(13, "18");
        scoreMap.put(14, "22");
    }

    public ReadabilityScore(String text) {
        this.text = text;
    }

    /**
     * Count the number of words in the text.
     *
     * @return number of words
     */
    private int countWords() {
        return text.split("\\s+").length;
    }

    /**
     * Count the number of characters in the text.
     *
     * @return number of characters
     */
    private int countCharacters() {
        return text.replaceAll("\\s+", "").length();
    }

    /**
     * Count the number of sentences in the text.
     *
     * @return number of sentences
     */
    private int countSentences() {
        String[] sentences = text.split("[.!?]+");
        return sentences.length;
    }

    public static String getAgeRange(double score) {
        score = Math.max(1, Math.min(14, score));
        int scoreRounded = (int) Math.ceil(score);
        return scoreMap.getOrDefault(scoreRounded, "Unknown");
    }

    /**
     * Calculate the ARI readability score based on the text.
     *
     * @return the readability score category
     * @see <a href="https://en.wikipedia.org/wiki/Automated_readability_index">Automated readability index</a>
     */
    public double getARIScore() {
        int characters = countCharacters();
        int words = countWords();
        int sentences = countSentences();

        return 4.71 * ((double) characters / words) + 0.5 * ((double) words / sentences) - 21.43;

    }

    public double getFKScore() {
        int words = countWords();
        int sentences = countSentences();
        int syllables = countSyllables();

        return 0.39 * ((double) words / sentences) + 11.8 * ((double) syllables / words) - 15.59;
    }

    public double getSMOGScore() {
        int sentences = countSentences();
        int polysyllables = countPolysyllables();

        return 1.043 * Math.sqrt(polysyllables * (30.0 / sentences)) + 3.1291;
    }

    public double getCLScore() {
        int characters = countCharacters();
        int words = countWords();
        int sentences = countSentences();

        double L = ((double) characters / words) * 100;
        double S = ((double) sentences / words) * 100;

        return 0.0588 * L - 0.296 * S - 15.8;
    }

    public void printCounts() {
        System.out.println("Words: " + countWords());
        System.out.println("Sentences: " + countSentences());
        System.out.println("Characters: " + countCharacters());
        System.out.println("Syllables: " + countSyllables());
        System.out.println("Polysyllables: " + countPolysyllables());
    }

    private int countPolysyllables() {
        String[] words = text.split("\\s+");
        int polysyllableCount = 0;
        for (String word : words) {
            if (countSyllablesInWord(word) > 2) {
                polysyllableCount++;
            }
        }
        return polysyllableCount;
    }

    /**
     * Count the number of syllables in the text.
     *
     * @return number of syllables
     */
    private int countSyllables() {
        // split text into words
        // for each word, count syllables

        String[] words = text.split("\\s+");
        int syllableCount = 0;
        for (String word : words) {
            syllableCount += countSyllablesInWord(word);
        }
        return syllableCount;

    }

    /**
     * Count the number of syllables in a word.
     * <p>
     * For now simply count the number of vowels as syllables.
     *
     * @param word the word to count syllables in
     * @return number of syllables in the word
     */
    private int countSyllablesInWord(String word) {
        // Remove non-alphabetic characters and convert to lowercase
        String lowerWord = word.toLowerCase().replaceAll("[^a-z]", "");

        if (lowerWord.isEmpty()) {
            return 0;
        }

        int count = 0;
        boolean previousWasVowel = false;

        for (int i = 0; i < lowerWord.length(); i++) {
            char c = lowerWord.charAt(i);
            boolean isVowel = "aeiouy".indexOf(c) != -1;

            // Count vowel groups, not individual vowels
            if (isVowel && !previousWasVowel) {
                count++;
            }

            previousWasVowel = isVowel;
        }

        // Handle silent 'e' at the end
        if (lowerWord.endsWith("e") && count > 1) {
            count--;
        }

        // Handle 'le' ending (like "table", "bottle")
        if (lowerWord.endsWith("le") && lowerWord.length() > 2 &&
                "aeiouy".indexOf(lowerWord.charAt(lowerWord.length() - 3)) == -1) {
            count++;
        }

        // Ensure at least one syllable
        return Math.max(count, 1);
    }

    public void calculateAndPrintScore(String choice) {
        switch (choice) {
            case "ARI":
                double ariScore = getARIScore();
                System.out.printf("Automated Readability Index: %.2f (about %s year olds).%n", ariScore, getAgeRange(ariScore));
                break;
            case "FK":
                double fkScore = getFKScore();
                System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s year olds).%n", fkScore, getAgeRange(fkScore));
                break;
            case "SMOG":
                double smogScore = getSMOGScore();
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s year olds).%n", smogScore, getAgeRange(smogScore));
                break;
            case "CL":
                double clScore = getCLScore();
                System.out.printf("Coleman–Liau index: %.2f (about %s year olds).%n", clScore, getAgeRange(clScore));
                break;
            case "all":
                calculateAndPrintScore("ARI");
                calculateAndPrintScore("FK");
                calculateAndPrintScore("SMOG");
                calculateAndPrintScore("CL");

                double averageScore = (getARIScore() + getFKScore() + getSMOGScore() + getCLScore()) / 4.0;
                String averageAgeRange = getAgeRange(averageScore);
                System.out.printf("%nThis text should be understood in average by %s year olds.%n", averageAgeRange);

                break;
            default:
                System.out.println("Unknown score type.");
        }
    }
}




