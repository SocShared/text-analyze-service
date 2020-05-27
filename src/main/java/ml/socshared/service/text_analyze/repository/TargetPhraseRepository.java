package ml.socshared.service.text_analyze.repository;

import ml.socshared.service.text_analyze.domain.db.TargetPhraseDB;
import ml.socshared.service.text_analyze.domain.db.TargetPhrasesId;
import ml.socshared.service.text_analyze.domain.object.TargetPhrase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TargetPhraseRepository extends CrudRepository<TargetPhraseDB, TargetPhrasesId> {
    List<TargetPhraseDB> findBySystemUserId(UUID systemUserId);
}
