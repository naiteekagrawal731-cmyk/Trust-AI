package AIGender.bias.dtos.ResponseDtos.GroqDtos;

import lombok.Data;

@Data
public class Choice {

    private long index;
    private Message message;
    private String finish_reason;
}
