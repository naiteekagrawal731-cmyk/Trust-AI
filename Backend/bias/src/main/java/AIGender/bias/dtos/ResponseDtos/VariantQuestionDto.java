package AIGender.bias.dtos.ResponseDtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariantQuestionDto {

    private long questionId;
    private long generalQuestionId;
    private String question;
}
