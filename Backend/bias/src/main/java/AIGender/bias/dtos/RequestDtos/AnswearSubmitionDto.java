package AIGender.bias.dtos.RequestDtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class AnswearSubmitionDto {

    private long modelId;
    private long questionId;
    private long generalQuestionId;
    private String answer;
}
