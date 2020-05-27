package ml.socshared.service.text_analyze.analyzer;

public class Token {

    private String word;
    private double score;

    public Token(String representation) {
        if(representation == null) {
            throw new IllegalArgumentException("representation cannot be null");
        }

        word = representation;
        score = 0;
    }
    public Token(String representation, double score) {
        if(representation == null) {
            throw new IllegalArgumentException("representation cannot be null");
        }
        if(score < 0) {
            throw new IllegalArgumentException("score cannot be less than zero");
        }
        this.word = representation;
        this.score = score;

    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public String toString() {
        return "{word=" + word + "; score=" + String.valueOf(score) + "}";
    }
    public int hashCode() {
        return word.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        Token token = (Token)obj;
        return (word == token.word) && (score == token.score);
    }
}
