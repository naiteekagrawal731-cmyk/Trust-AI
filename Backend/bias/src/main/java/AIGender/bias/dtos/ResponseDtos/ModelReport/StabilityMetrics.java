package AIGender.bias.dtos.ResponseDtos.ModelReport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StabilityMetrics {

    private double varianceAcrossQuestions;
    private double consistencyScore;
}
