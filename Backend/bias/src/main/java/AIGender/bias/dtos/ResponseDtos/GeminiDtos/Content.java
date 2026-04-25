package AIGender.bias.dtos.ResponseDtos.GeminiDtos;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    private List<Part> parts;
}
