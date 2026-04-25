package AIGender.bias.dtos.RequestDtos;

import lombok.Data;

@Data
public class ModelEvaluationRequest {

    private long modelId;
    private String variation1;
    private String variation2;
}
