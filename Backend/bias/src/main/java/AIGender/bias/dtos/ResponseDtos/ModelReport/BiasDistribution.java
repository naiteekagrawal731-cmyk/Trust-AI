package AIGender.bias.dtos.ResponseDtos.ModelReport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BiasDistribution {

    private double lowBiasPercentage;
    private double mediumBiasPercentage;
    private double highBiasPercentage;
}
