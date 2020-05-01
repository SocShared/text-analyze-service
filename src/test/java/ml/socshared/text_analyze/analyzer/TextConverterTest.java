package ml.socshared.text_analyze.analyzer;

import opennlp.tools.langdetect.LanguageDetectorModel;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.awt.X11.XSystemTrayPeer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TextConverterTest {

    private  LanguageDetectorModel model = null;

    @BeforeEach
    public void loadLangModel() throws IOException {
            File file = new File("./src/test/resources/langdetect-183.bin");
            model  = new LanguageDetectorModel(new FileInputStream(file));
    }

    @Test
    public void findOneWord() throws IOException {
        String text = "Я бы хотел купить, но дорого";
        TextConverter converter = new TextConverter(model);
        List<String> res = converter.convert(text);
        Assertions.assertEquals(TextConverter.LinearSearchOfSequence(res, Arrays.asList("купить")), Optional.of(3));
    }
    @Test
    public void findTwoWord() throws IOException {
        String text = "Не подскажете, где можно найти телефон Sony";
        TextConverter converter = new TextConverter(model);
        List<String> res = converter.convert(text);
        Assertions.assertEquals(TextConverter.LinearSearchOfSequence(res, Arrays.asList("купить")), Optional.of(3));
    }
}