package ml.socshared.service.textanalyze.domain.db;

import lombok.Data;

import javax.persistence.*;
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
