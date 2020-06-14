package ml.socshared.service.textanalyze.domain.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetPhrase {
    String phrase;
    Integer indexPosition;
}
