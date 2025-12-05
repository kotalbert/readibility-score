package readability;

import java.util.HashMap;
import java.util.Map;

public class ReadabilityScore {
    private final String text;
    private static final Map<Integer, String> scoreMap = new HashMap<>();

    static {
        scoreMap.put(1, "5-6");
        scoreMap.put(2, "6-7");
        scoreMap.put(3, "7-8");
        scoreMap.put(4, "8-9");
        scoreMap.put(5, "9-10");
        scoreMap.put(6, "10-11");
        scoreMap.put(7, "11-12");
        scoreMap.put(8, "12-13");
        scoreMap.put(9, "13-14");
        scoreMap.put(10, "14-15");
        scoreMap.put(11, "15-16");
        scoreMap.put(12, "16-17");
        scoreMap.put(13, "17-18");
        scoreMap.put(14, "18-22");
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

    /**
     * Calculate the ARI readability score based on the text.
     *
     * @return the readability score category
     * @see <a href="https://en.wikipedia.org/wiki/Automated_readability_index">Automated readability index</a>
     */
    public double getScore() {
        int characters = countCharacters();
        int words = countWords();
        int sentences = countSentences();

        return 4.71 * ((double) characters / words) + 0.5 * ((double) words / sentences) - 21.43;

    }

    public String getAgeRange(double score) {
        assert score >= 1 && score <= 14 : "Score must be between 1 and 14";
        int scoreRounded = (int) Math.ceil(getScore());
        return scoreMap.getOrDefault(scoreRounded, "Unknown");
    }

    public void printCounts() {
        System.out.println("Words: " + countWords());
        System.out.println("Sentences: " + countSentences());
        System.out.println("Characters: " + countCharacters());
    }
}




