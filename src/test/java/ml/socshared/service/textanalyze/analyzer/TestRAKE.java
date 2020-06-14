package ml.socshared.service.textanalyze.analyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class TestRAKE {

    public List<String> stopWords = null;

    @BeforeEach
    public void loadStopWords() throws IOException {
        StringBuilder builder = readText("./src/test/resources/stopwords-ru2.txt");
        stopWords = Arrays.asList(builder.toString().split("\n"));
    }

    public StringBuilder readText(String path) throws IOException {
        FileReader file = new FileReader(new File(path));
        int read = 0;
        char[] buffer = new char[100];
        StringBuilder builder = new StringBuilder();
        while((read = file.read(buffer)) > 0) {
            builder.append(buffer, 0, read);
        }
        return builder;
    }

    @Test
    public void getTerms() throws IOException {
        String text = readText("./src/test/resources/news-ru.txt").toString();
        Rake r = new Rake();
        r.feed(text, stopWords);
        for(Term term : r.getTerms()) {
            if(term.getTokens().size() <= 4 && term.getTokens().size() >= 2)
                System.out.println(term);
        }
    }

    @Test
    public void stopWords() {

        System.out.println(Rake.buildRegexFromStopWords(stopWords));
    }
}
