package ml.socshared.service.text_analyze.analyzer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TextConverterTest {


    @Test
    public void findOneWord() throws IOException {
        String text = "Я бы хотел купить, но дорого";
        TextConverter converter = new TextConverter();
        List<String> res = converter.convert(text);
        Assertions.assertEquals(TextConverter.LinearSearchOfSequence(res, Arrays.asList("хотеть", "купить")), Optional.of(2));
    }
    @Test
    public void findTwoWord() throws IOException {
        String text = "Не подскажете, где можно найти телефон Sony";
        TextConverter converter = new TextConverter();
        List<String> res = converter.convert(text);
        Assertions.assertEquals(TextConverter.LinearSearchOfSequence(res, Arrays.asList("купить")), Optional.empty());
    }
}