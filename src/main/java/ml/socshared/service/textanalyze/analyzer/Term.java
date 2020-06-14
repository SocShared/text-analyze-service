package ml.socshared.service.textanalyze.analyzer;

import java.util.LinkedList;
import java.util.List;

public class Term {
    private List<Token> tokens;
    private Double score = null;

    public Term(List<Token> t) {
        tokens = t;
    }
    public Term() {
        tokens = new LinkedList<>();
    }

    public void addToken(Token token) {
        tokens.add(token);
        score = null;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for(int i = 0; i < tokens.size()-1; i++) {
            builder.append(tokens.get(i).getWord());
            builder.append(" ");
        }
        builder.append(tokens.get(tokens.size()-1).getWord());
        builder.append("; score=");
        builder.append(getScore());
        builder.append("}");
        return String.valueOf(builder);
    }

    public double getScore() {
        if(this.score == null) {
            score = 0.0;
            for(Token el : tokens) {
                score += el.getScore();
            }
        }
        return score;
    }

    public String asPhrase() {
        if(tokens.isEmpty()) {
            return "";
        }
        StringBuilder phrase = new StringBuilder();
        for(int i = 0; i < tokens.size()-1; i++) {
            phrase.append(tokens.get(i).getWord());
            phrase.append(" ");
        }
        phrase.append(tokens.get(tokens.size()-1).getWord());
        return phrase.toString();
    }

    public int size() {
        return tokens.size();
    }

    boolean isContain(Token token) {
        for(Token el : tokens) {
            if(el.equals(token)) {
                return  true;
            }
        }

        return false;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        Term term = (Term) obj;
        if(tokens.size() != term.getTokens().size()) {
            return false;
        }
        for(int i = 0; i < tokens.size(); i++) {
            if(!tokens.get(i).equals(term.getTokens().get(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 0;
        for(Token token : tokens) {
            hash = 18181*((hash*token.hashCode() + hash)%383) + hash;
        }
        return hash;
    }

}
