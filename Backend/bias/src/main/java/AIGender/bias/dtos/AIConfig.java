package AIGender.bias.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AIConfig {
    private long modelId;
    private String apiUrl;
    private String method;
    private Map<String, String> headers;
    private String requestTemplate;
    private String responsePath;

    // getters & setters
}