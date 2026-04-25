package AIGender.bias.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScorePair {
    private String variant1;
    private int score1;
    private String variant2;
    private int score2;
}
