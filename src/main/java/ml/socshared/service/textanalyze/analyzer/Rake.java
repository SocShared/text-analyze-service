package ml.socshared.service.textanalyze.analyzer;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Rake {
    private List<Term> terms = null;
    private List<Token> tokens = null;
    private boolean isSorted;


    public void resetState() {
        if(terms != null)
            terms.clear();
        if(tokens != null)
            tokens.clear();

        terms = null;
        tokens = null;
        isSorted = false;
    }

    public void feed(String text, List<String> stopWords) {
        isSorted = false;
       // String ntext = text.toLowerCase().replaceAll("[\\\\|/,.!?;'1-9\n:]+", " ");
        terms = new ArrayList<>();
        tokens = new LinkedList<>();
        text = text.toLowerCase().replaceAll("[^\\p{L}!,./\\\\;:'\"()]+", " ");
        String[] tmpTerms = text.split(buildRegexFromStopWords(stopWords));
        for (String el : tmpTerms) {
            Term term = new Term();
            for (String word : el.split(" ")) {
                if ("".equals(word)) continue;
                Token token = new Token(word.trim());
                term.addToken(token);
                tokens.add(token);
            }
            if (term.size() == 0) continue;
            terms.add(term);
        }

        //build table "Word Co-Concurrences"
        CoOccurrenceMatrix matrix = new CoOccurrenceMatrix();
//        for (Term term1 : terms) {
//            for (Token token1 : term1.getTokens()) {
//                for (Term term2 : terms) {
//                    if (term2.isContain(token1)) {
//                        for (Token token2 : term2.getTokens()) {
//                            if (token1.equals(token2)) continue;
//                            matrix.update(token1.getWord(), token2.getWord(), (value) -> value + 1);
//                        }
//                    }
//                }
//                matrix.update(token1.getWord(), token1.getWord(), (value) -> value + 1);
//            }
//        }
        for(Token token : tokens) {
            for(Term term : terms) {
                if(term.isContain(token)) {
                    for(Token tokenOfTerm : term.getTokens()) {
                        matrix.update(token.getWord(), tokenOfTerm.getWord(), (value)->value+1);
                    }
                }
            }
        }
            for (Token token : tokens) {
                int tokenDegree = matrix.rowSum(token.getWord());
                int tokenFrequency = matrix.get(token.getWord(), token.getWord());
                token.setScore((double) tokenDegree / (double) tokenFrequency);
            }
        }

    public static String buildRegexFromStopWords(List<String> stopWords) {
        StringBuilder swRegex = new StringBuilder();
        swRegex.append("[\\\\0-9\\(\\)\\/\\-–,.ck:\"'!\\?]+|\\b(");
        //swRegex.append("[^\\p{L}]+|\\b(");
       for(int i = 0; i < stopWords.size() - 1; i++) {
           swRegex.append(stopWords.get(i));
           swRegex.append('|');
       }
       swRegex.append(stopWords.get(stopWords.size() - 1));
        swRegex.append(")\\b");
       return String.valueOf(swRegex);
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<Term> getTerms() {
        if(!isSorted) {
            Collections.sort(terms, (term1, term2) -> {
                double score1 = term1.getScore();
                double score2 = term2.getScore();
                if(score1 < score2) return  1;
                else if(score1 == score2) return  0;
                else return -1;
            });
            isSorted = true;
        }

        return terms;
    }

}
