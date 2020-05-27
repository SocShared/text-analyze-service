package ml.socshared.service.text_analyze.service.impl;

import ml.socshared.service.text_analyze.analyzer.Rake;
import ml.socshared.service.text_analyze.analyzer.Term;
import ml.socshared.service.text_analyze.analyzer.TextConverter;
import ml.socshared.service.text_analyze.domain.db.TargetPhraseDB;
import ml.socshared.service.text_analyze.domain.object.TargetPhrase;
import ml.socshared.service.text_analyze.repository.TargetPhraseRepository;
import ml.socshared.service.text_analyze.service.TextAnalyzer;
import ml.socshared.service.text_analyze.domain.object.KeyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ml.socshared.template.exception.impl.HttpNotFoundException;

import java.util.*;

@Service
public class TextAnalyzerImpl implements TextAnalyzer {

    Rake extractor;
    List<String> stopWordsList;
    TargetPhraseRepository repository;

    @Autowired
    TextAnalyzerImpl(Rake extr, @Qualifier("getStopWords") List<String> stop_words, TargetPhraseRepository tpr) {
        extractor = extr;
        stopWordsList = stop_words;
        repository = tpr;
    }

    @Override
    public List<KeyWord> extractKeyWords(String text, Integer minLength, Integer maxLength) {
        extractor.feed(text, stopWordsList);
        List<KeyWord> res = new LinkedList<>();
        for(Term t : extractor.getTerms()) {
            if(t.size() >= minLength && t.size() <= maxLength) {
                KeyWord kw = new KeyWord();
                kw.setKeyWord(t.asPhrase());
                kw.setScore(t.getScore());
                res.add(kw);
            }

        }
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
        return res;

    }

    @Override
    public void addTargetPhrases(List<String> targetPhrases, UUID systemUserId) {
        for(String phrase : targetPhrases) {
            List<String> phr = TextConverter.convert(phrase);
            TargetPhraseDB target = new TargetPhraseDB();
            target.setPhrase(String.join(" ", phr));
            target.setSystemUserId(systemUserId);
            repository.save(target);
        }
    }

    @Override
    public List<String> getTargetPhrases(UUID systemUserId) {
        List<TargetPhraseDB> target =  repository.findBySystemUserId(systemUserId);
        List<String> res = new LinkedList<>();
        for(TargetPhraseDB el : target) {
            res.add(el.getPhrase());
        }
        return res;
    }
}
