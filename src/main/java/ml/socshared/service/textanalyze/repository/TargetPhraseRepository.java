package ml.socshared.service.textanalyze.repository;

import ml.socshared.service.textanalyze.domain.db.TargetPhraseDB;
import ml.socshared.service.textanalyze.domain.db.TargetPhrasesId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TargetPhraseRepository extends CrudRepository<TargetPhraseDB, TargetPhrasesId> {
    List<TargetPhraseDB> findBySystemUserId(UUID systemUserId);
}
