package readability;

public class ReadabilityScore {
    private final String text;

    public ReadabilityScore(String text) {
        this.text = text;

    }

    public Score getScore() {
        if (text.length() <= 100)
            return Score.EASY;
        else
            return Score.HARD;
    }
}
