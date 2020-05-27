package ml.socshared.text_analyze.service.impl;

import ml.socshared.text_analyze.domain.object.KeyWord;
import ml.socshared.text_analyze.service.TextAnalyzer;

import java.util.List;
import java.util.UUID;

public class TextAnalyzerImpl implements TextAnalyzer {
    @Override
    public List<KeyWord> extractKeyWords(String text) {
        return null;
    }

    @Override
    public List<String> extractTargetPhrases(String text) {
        return null;
    }

    @Override
    public void addTargetPhrases(List<String> targetPhrases, UUID systemUserId) {

    }
}
