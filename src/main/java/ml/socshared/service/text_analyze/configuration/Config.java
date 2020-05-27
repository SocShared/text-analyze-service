package ml.socshared.service.text_analyze.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Config {

    //@Value("${service.path-to-stop-words}")
    //static String pathToTextStopWords = null;

    List<String> stopWords;

//    Config() throws IOException {
//        stopWords = loadStopWords();
//    }
//
//    public List<String> loadStopWords() throws IOException {
//        InputStream is;
//        if(pathToTextStopWords == null) {
//            is = this.getClass().getClassLoader().getResourceAsStream("stopwords-ru-eng.txt");
//        } else {
//            is = new FileInputStream(pathToTextStopWords);
//        }
//        StringBuilder builder = readTextByLine(is);
//        return Arrays.asList(builder.toString().split("\n"));
//    }

    @Bean
    public List<String> getStopWords() throws IOException {
        InputStream is =  is = this.getClass().getClassLoader().getResourceAsStream("stopwords-ru-eng.txt");
        StringBuilder builder = readTextByLine(is);
        return Arrays.asList(builder.toString().split("\n"));
    }

    StringBuilder readTextByLine(InputStream stream) throws IOException {
        InputStreamReader file = new InputStreamReader(stream);
        int read = 0;
        char[] buffer = new char[100];
        StringBuilder builder = new StringBuilder();
        while((read = file.read(buffer)) > 0) {
            builder.append(buffer, 0, read);
        }
        return builder;
    }
}
