package ml.socshared.service.text_analyze.service.impl;

import ml.socshared.service.text_analyze.analyzer.Rake;
import ml.socshared.service.text_analyze.analyzer.Term;
import ml.socshared.service.text_analyze.analyzer.TextConverter;
import ml.socshared.service.text_analyze.configuration.SentryTag;
import ml.socshared.service.text_analyze.domain.db.TargetPhraseDB;
import ml.socshared.service.text_analyze.domain.object.TargetPhrase;
import ml.socshared.service.text_analyze.repository.TargetPhraseRepository;
import ml.socshared.service.text_analyze.service.TextAnalyzer;
import ml.socshared.service.text_analyze.domain.object.KeyWord;
import ml.socshared.service.text_analyze.service.sentry.SentryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ml.socshared.template.exception.impl.HttpNotFoundException;
import java.util.*;


@Service
public class TextAnalyzerImpl implements TextAnalyzer {

    private final Rake extractor;
    private final List<String> stopWordsList;
    private final TargetPhraseRepository repository;
    private final SentryService sentry;

    @Value("${service.return_top}")
    private static final Integer RETURN_TOP_BY_SCORE = 100;
    @Value("${service.send_sentry_top}")
    private static final Integer SEND_SENTRY_TOP_BY_SCORE = 20;

    @Autowired
    public TextAnalyzerImpl(Rake extr, @Qualifier("getStopWords") List<String> stop_words, TargetPhraseRepository tpr,
                     SentryService sentryService) {
        extractor = extr;
        stopWordsList = stop_words;
        repository = tpr;
        sentry = sentryService;
    }

    @Override
    public List<KeyWord> extractKeyWords(String text, Integer minLength, Integer maxLength) {
        extractor.feed(text, stopWordsList);
        List<KeyWord> res = new LinkedList<>();
        int counter = 0;
        for(Term t : extractor.getTerms()) {
            if(t.size() >= minLength && t.size() <= maxLength) {
                if(counter > RETURN_TOP_BY_SCORE) {
                    break;
                }
                KeyWord kw = new KeyWord();
                kw.setKeyWord(t.asPhrase());
                kw.setScore(t.getScore());
                res.add(kw);
                counter = counter + 1;
            }

        }
        Map<String, Object> additional = new HashMap<>();
        additional.put("text", text);
        List<KeyWord> sentry_res = new LinkedList<>();
        counter = 0;
        for(KeyWord k : res) {
            if(counter > SEND_SENTRY_TOP_BY_SCORE) {
                break;
            }
            sentry_res.add(k);
            counter = counter + 1;
        }
        additional.put("key_words", sentry_res);
        sentryMessage("Extract key words from text", additional,
                Collections.singletonList(SentryTag.EXTRACT_KEY_WORDS));
        extractor.resetState();
        return res;
    }

    @Override
    public List<TargetPhrase> extractTargetPhrases(UUID systemUserId, String text) {
        List<String> sequence = TextConverter.convert(text);
        List<TargetPhraseDB>  targets = repository.findBySystemUserId(systemUserId);
        List<TargetPhrase> res = new LinkedList<>();
        for(TargetPhraseDB t : targets) {
            List<String> tmp = Arrays.asList(t.getPhrase().split(" "));
            if(tmp.isEmpty()) {
                throw new HttpNotFoundException("Not found targets phrases for User (" +systemUserId + ")");
            }
            Optional<Integer> index = TextConverter.LinearSearchOfSequence(sequence, tmp);
            if(index.isPresent()) {
                Integer i = index.get();
                TargetPhrase tp = new TargetPhrase(t.getPhrase(), i);
                res.add(tp);
            }
        }
        Map<String, Object> additional = new HashMap<>();
        additional.put("system_user_id", systemUserId);
        additional.put("text", text);
        additional.put("found_target_phrases", res);
        sentryMessage("extract target phrases from text", additional,
                Collections.singletonList(SentryTag.SEARCH_TARGET_PHRASES));
        return res;

    }

    @Override
    public void addTargetPhrases(List<String> targetPhrases, UUID systemUserId) {
       List<String> targets = new LinkedList<>();
        for(String phrase : targetPhrases) {
            List<String> phr = TextConverter.convert(phrase);
            TargetPhraseDB target = new TargetPhraseDB();
            String ph = String.join(" ", phr);
            targets.add(ph);
            target.setPhrase(ph);
            target.setSystemUserId(systemUserId);

            repository.save(target);
        }
        Map<String, Object> additional = new HashMap<>();
        additional.put("target_words", targets);
        additional.put("systemUserId", systemUserId);
        sentryMessage("SystemUser add new target phrases in system", additional,
                Collections.singletonList(SentryTag.ADD_TARGET_PHRASES));
    }

    @Override
    public List<String> getTargetPhrases(UUID systemUserId) {
        List<TargetPhraseDB> target =  repository.findBySystemUserId(systemUserId);
        List<String> res = new LinkedList<>();
        for(TargetPhraseDB el : target) {
            res.add(el.getPhrase());
        }
        HashMap<String, Object> additional = new HashMap<>();
        additional.put("system-user-id", systemUserId);
        sentryMessage("get target phrases", additional,
                Collections.singletonList(SentryTag.GET_TARGET_PHRASES));
        return res;
    }

    private void sentryMessage(String message,Map<String, Object> additionalData, List<SentryTag> tags) {
        Map<String, String> tm = new HashMap<>();
        tm.put("service", SentryTag.SERVICE_NAME);
        for(SentryTag tag : tags) {
            tm.put(tag.type(), tag.value());
        }
        sentry.logMessage(message, tm, additionalData);
    }
}
