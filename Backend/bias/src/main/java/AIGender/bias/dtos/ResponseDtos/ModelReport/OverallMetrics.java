package AIGender.bias.dtos.ResponseDtos.ModelReport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OverallMetrics {
    private double fairnessScore;
    private double biasScore;
    private double averageDelta;
    private double maxDeltaObserved;
}
