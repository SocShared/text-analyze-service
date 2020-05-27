package ml.socshared.text_analyze.analyzer;


import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCoOccurrenceMatrix {

    @Test
    public void addAndGetValue() {
        CoOccurrenceMatrix matrix = new CoOccurrenceMatrix();
        matrix.set("test", "test", 1);
        Assertions.assertEquals(matrix.get("test", "test"), 1);
        Assertions.assertEquals(matrix.get("test", "test2"), 0);
        Assertions.assertEquals(matrix.get("test2", "test"), 0);
    }

    @Test
    public void addAndUpdateValue() {
        CoOccurrenceMatrix matrix = new CoOccurrenceMatrix();
        matrix.set("test", "test", 10);
        matrix.update("test", "test", (value) -> value + 10);
        matrix.update("otherRow", "otherCol", (value)-> value + 10);
        Assertions.assertEquals(matrix.get("test", "test"), 20);
        Assertions.assertEquals(matrix.get("otherRow", "otherCol"), 10);
    }

    @Test
    public void addAndGetsValues() {
        CoOccurrenceMatrix matrix = new CoOccurrenceMatrix();
        matrix.set("1", "1", 1);
        matrix.set("1", "2", 2);
        matrix.set("2", "1", 3);
        matrix.set("2", "2", 4);
        Assertions.assertEquals(matrix.get("1", "1"), 1);
        Assertions.assertEquals(matrix.get("1", "2"), 2);
        Assertions.assertEquals(matrix.get("2", "1"), 3);
        Assertions.assertEquals(matrix.get("2", "2"), 4);
    }

    @Test
    public void addAndGetRowSum() {
        CoOccurrenceMatrix matrix = new CoOccurrenceMatrix();
        matrix.set("1", "1", 1);
        matrix.set("1", "2", 2);
        matrix.set("2", "1", 3);
        matrix.set("2", "2", 4);
        matrix.set("3", "25", 100);
        Assertions.assertEquals(matrix.rowSum("1"), 3);
        Assertions.assertEquals(matrix.rowSum("2"), 7);
        Assertions.assertEquals(matrix.rowSum("3"), 100);
    }
}
