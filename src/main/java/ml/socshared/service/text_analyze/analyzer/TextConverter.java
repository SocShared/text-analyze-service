package ml.socshared.service.text_analyze.analyzer;


import lombok.extern.slf4j.Slf4j;
import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TextConverter {


    private static LuceneMorphology rusMorphology = null;
    //private static LuceneMorphology engMorphology = null;
    static {
        try {
            rusMorphology = new RussianLuceneMorphology();
        } catch (IOException e) {
            log.error("Error loading model of russian morphology", e);
        }
    }

    public TextConverter() {


    }
    public static List<String> convert(String text) {
        Pattern r = Pattern.compile("[\t\\s\n\r\f\\\\w ,.:\\\"'!\\\\t]");
        String[] words = r.split(text);
        List<String> convertedWords = new ArrayList<>();
        Pattern rusLangSymbol = Pattern.compile("([А-я]+\\-{0,1}[А-я]+)|[А-я]");
        for(String word: words) {
            try {
                convertedWords.add(rusMorphology.getMorphInfo(word.toLowerCase()).get(0).split("\\|")[0].trim());
            } catch (Exception exp) {
                log.info("the text contains unfiltered non-Russian characters: " + exp.getMessage());
            }
        }

        return convertedWords;
    }

    public static Optional<Integer> LinearSearchOfSequence(List<String> array, List<String> sequence) {
        if(array.size() < sequence.size()) {
            return Optional.empty();
        }
        int index = 0;
        while((index+sequence.size()-1) < array.size()) {
            boolean isFound = true;
            for(int j = 0; j < sequence.size(); j++) {
                if(!array.get(index+j).equals(sequence.get(j))) {
                    isFound = false;
                    break;
                }
            }
            if(!isFound) {
                index = index + 1;
            } else {
                return Optional.of(index);
            }
        }
        return Optional.empty();
    }
}
