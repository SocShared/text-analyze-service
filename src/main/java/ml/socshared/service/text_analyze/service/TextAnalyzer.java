package ml.socshared.text_analyze.service;

import ml.socshared.text_analyze.domain.object.KeyWord;

import java.util.List;
import java.util.UUID;

public interface TextAnalyzer {
    List<KeyWord> extractKeyWords(String text);
    List<String> extractTargetPhrases(String text);
    void addTargetPhrases(List<String> targetPhrases, UUID systemUserId);
}
