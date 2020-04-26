package ml.socshared.text_analyze.service.impl;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TextAnalyzerImplTest {

    private InputStream langDetectModel;

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
        String text_eng = "На золотом крыльце сидели: царь, царевич, король, королевич, сапожник, партной, кто ты будешь такой!";
        LanguageDetector langDetector = new LanguageDetectorME(new LanguageDetectorModel(langDetectModel));
        Language bestLanguage = langDetector.predictLanguage(text_eng);
        System.out.println(bestLanguage.getLang());
    }
}