package AIGender.bias.dtos.RequestDtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BiasCheckRequestDto {

    private String modelName;
    private int numQuestions;
    private List<String> variants;
}
