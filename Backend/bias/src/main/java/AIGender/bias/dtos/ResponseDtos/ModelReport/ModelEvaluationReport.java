package AIGender.bias.dtos.ResponseDtos.ModelReport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelEvaluationReport {

    private long modelId;
    private BiasDistribution biasDistribution;
    private Classification classification;
    private OverallMetrics overallMetrics;
    private StabilityMetrics stabilityMetrics;

}
