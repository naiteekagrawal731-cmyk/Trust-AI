package AIGender.bias.dtos.ResponseDtos.ModelReport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Classification {
    private String overallLevel;
    private String status;
    private String deploymentRecommendation;
}
