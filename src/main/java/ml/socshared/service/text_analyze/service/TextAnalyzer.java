package ml.socshared.service.text_analyze.service;

import ml.socshared.service.text_analyze.domain.object.KeyWord;
import ml.socshared.service.text_analyze.domain.object.TargetPhrase;

import java.util.List;
import java.util.UUID;

public interface TextAnalyzer {
    List<KeyWord> extractKeyWords(String text, Integer minLength, Integer maxLength);
    List<TargetPhrase>  extractTargetPhrases(UUID systemUserId, String text);
    void addTargetPhrases(List<String> targetPhrases, UUID systemUserId);
    List<String> getTargetPhrases(UUID systemUserId);
}
