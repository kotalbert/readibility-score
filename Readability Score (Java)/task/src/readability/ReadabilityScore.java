package readability;

public class ReadabilityScore {
    private final String text;
    private final String[] sentences;

    public ReadabilityScore(String text) {
        this.text = text;
        this.sentences = text.split("[.!?]+");
    }

    private int countWords(String sentence) {
        String[] words = sentence.split("\\s+");
        return words.length;
    }

    public Score getScore() {
        int totalWords = 0;
        for (String sentence : sentences) {
            totalWords += countWords(sentence);
        }
        double averageWordsPerSentence = (double) totalWords / sentences.length;
        if (Math.floor(averageWordsPerSentence) <= 10) {
            return Score.EASY;
        } else {
            return Score.HARD;
        }
    }
}
