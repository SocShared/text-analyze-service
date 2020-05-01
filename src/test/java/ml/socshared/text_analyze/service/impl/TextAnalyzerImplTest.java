package ml.socshared.text_analyze.service.impl;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TextAnalyzerImplTest {

    private InputStream langDetectModel;
    private String text_ru= "На золотом крыльце сидели: царь, царевич, король, королевич, сапожник, партной, кто ты будешь такой!";

    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void extractKeyWords() {
    }

    @Test
    public void extractTargetPhrases() {
    }

    @Test
    public void addTargetPhrases() {
    }
    @Test
    public void checkTextLanguage() throws IOException {
        File file = new File("./src/test/resources/langdetect-183.bin");
        langDetectModel = new FileInputStream(file);

        LanguageDetector langDetector = new LanguageDetectorME(new LanguageDetectorModel(langDetectModel));
        Language bestLanguage = langDetector.predictLanguage(text_ru);
        System.out.println(bestLanguage.getLang());
    }

    @Test
    public void tokenizeText() {
        SimpleTokenizer tok = SimpleTokenizer.INSTANCE;
        String[] t = tok.tokenize("He said,\"that's it.\" *u* Hello, World.");
        for(String el : t) {
            System.out.println(el);
        }


    }
}