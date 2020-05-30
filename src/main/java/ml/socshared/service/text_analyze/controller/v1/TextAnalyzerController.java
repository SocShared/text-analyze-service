package ml.socshared.service.text_analyze.controller.v1;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.service.text_analyze.api.v1.TextAnalyzerApi;
import ml.socshared.service.text_analyze.domain.object.KeyWord;
import ml.socshared.service.text_analyze.domain.object.TargetPhrase;
import ml.socshared.service.text_analyze.service.TextAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("isAuthenticated()")
public class TextAnalyzerController implements TextAnalyzerApi {

    TextAnalyzer service;

    @Autowired
    TextAnalyzerController(TextAnalyzer s) {
        service = s;
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping("/key_words")
    public List<KeyWord> extractKeyWords(@RequestBody String text,
                                         @RequestParam(value = "min_len", defaultValue = "2") Integer minLength,
                                         @RequestParam(value = "max_len", defaultValue  = "4") Integer maxLength) {
        log.info("request for extract key words");
        return service.extractKeyWords(text, minLength, maxLength);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping("/users/{systemUserId}/target_phrases/apply")
    public List<TargetPhrase> extractTargetPhrase(@PathVariable UUID systemUserId,
                                                  @RequestBody String text) {

        log.info("request for extract target phrases");
        return service.extractTargetPhrases(systemUserId, text);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @PostMapping("/users/{systemUserId}/target_phrases")
    public void setTargetPhrase(@PathVariable UUID systemUserId,
                                @RequestBody List<String> phrases) {
        log.info("request for set target phrases");
        service.addTargetPhrases(phrases, systemUserId);

    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping("/users/{systemUserId}/target_phrases")
    public List<String> getTargetPhrases(@PathVariable UUID systemUserId) {
        log.info("request for get target phrases");
        return service.getTargetPhrases(systemUserId);
    }
}
