package ml.socshared.service.textanalyze.controller.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.service.textanalyze.api.v1.TextAnalyzerApi;
import ml.socshared.service.textanalyze.domain.object.KeyWord;
import ml.socshared.service.textanalyze.domain.object.TargetPhrase;
import ml.socshared.service.textanalyze.domain.request.TextRequest;
import ml.socshared.service.textanalyze.service.TextAnalyzer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class TextAnalyzerController implements TextAnalyzerApi {

    private final TextAnalyzer service;

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @PostMapping("/private/key_words")
    public List<KeyWord> extractKeyWords(@RequestBody TextRequest text,
                                         @RequestParam(value = "min_len", defaultValue = "2") Integer minLength,
                                         @RequestParam(value = "max_len", defaultValue  = "4") Integer maxLength) {
        log.info("request for extract key words");
        return service.extractKeyWords(text.getText(), minLength, maxLength);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping("/private/users/{systemUserId}/target_phrases/apply")
    public List<TargetPhrase> extractTargetPhrase(@PathVariable UUID systemUserId,
                                                  @RequestBody String text) {

        log.info("request for extract target phrases");
        return service.extractTargetPhrases(systemUserId, text);
    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @PostMapping("/private/users/{systemUserId}/target_phrases")
    public void setTargetPhrase(@PathVariable UUID systemUserId,
                                @RequestBody List<String> phrases) {
        log.info("request for set target phrases");
        service.addTargetPhrases(phrases, systemUserId);

    }

    @Override
    @PreAuthorize("hasRole('SERVICE')")
    @GetMapping("/private/users/{systemUserId}/target_phrases")
    public List<String> getTargetPhrases(@PathVariable UUID systemUserId) {
        log.info("request for get target phrases");
        return service.getTargetPhrases(systemUserId);
    }
}
