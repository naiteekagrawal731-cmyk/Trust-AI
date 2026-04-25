package AIGender.bias.dtos.ResponseDtos.GroqDtos;

import lombok.Data;

import java.util.List;

@Data
public class GroqResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;


}
