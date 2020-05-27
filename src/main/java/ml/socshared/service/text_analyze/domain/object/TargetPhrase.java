package ml.socshared.service.text_analyze.domain.object;

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
