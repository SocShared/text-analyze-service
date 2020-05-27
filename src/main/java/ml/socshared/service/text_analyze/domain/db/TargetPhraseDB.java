package ml.socshared.service.text_analyze.domain.db;

import lombok.Data;
import ml.socshared.service.text_analyze.domain.object.TargetPhrase;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "target_phrase_db")
@IdClass(TargetPhrasesId.class)
public class TargetPhraseDB {
    @Id
    @GeneratedValue
    Integer id;
    @Id
    UUID systemUserId;
    String phrase;
}
