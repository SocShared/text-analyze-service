package ml.socshared.service.text_analyze.analyzer;

import java.util.HashMap;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class CoOccurrenceMatrix {
    private HashMap<String, HashMap<String, Integer>> matrix = new HashMap<>();

    public int get(String rowName, String colName) {
        HashMap<String, Integer> row = matrix.getOrDefault(rowName, null);
        if(row == null) {
            return 0;
        }
        return row.getOrDefault(colName, 0);
    }

    public void set(String rowName, String colName, int value) {
        HashMap<String, Integer> row = matrix.getOrDefault(rowName, null);
        if(row == null) {
            row = new HashMap<>();
            matrix.put(rowName, row);
        }
        row.put(colName, value);
    }

    public void update(String rowName, String colName, UnaryOperator<Integer> fooUpdate) {
        HashMap<String, Integer> row = matrix.getOrDefault(rowName, null);
        if(row == null) {
            row = new HashMap<>();
            matrix.put(rowName, row);
        }
        Integer value = row.getOrDefault(colName, 0);
        value = fooUpdate.apply(value);
        row.put(colName, value);
    }

    public int rowSum(String rowName) {
        int sum = 0;
        HashMap<String, Integer> row = matrix.getOrDefault(rowName, null);
        if(row == null) {
            return 0;
        }
        for(Integer value : row.values()) {
            sum += value;
        }
        return sum;
    }

}
