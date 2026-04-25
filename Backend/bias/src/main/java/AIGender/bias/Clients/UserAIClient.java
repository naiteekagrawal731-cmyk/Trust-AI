package AIGender.bias.Clients;

import AIGender.bias.dtos.AIConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service
public class UserAIClient {
    private final WebClient webClient;

    public UserAIClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<String> callAI(AIConfig config, String question) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // 1. Parse JSON template → safe structure
            JsonNode root = mapper.readTree(config.getRequestTemplate());

            // 2. Replace placeholders safely
            replacePlaceholders(root, Map.of(
                    "question", question
            ));

            WebClient.RequestBodySpec request = webClient
                    .method(HttpMethod.valueOf(config.getMethod()))
                    .uri(config.getApiUrl());

            // 3. Add headers
            if (config.getHeaders() != null) {
                config.getHeaders().forEach(request::header);
            }

            // 4. Send request
            return request
                    .bodyValue(root) // ✅ SAFE (no JSON breaking)
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(body -> {
                                        System.out.println("ERROR BODY: " + body);
                                        return Mono.error(new RuntimeException("Groq 4xx Error: " + body));
                                    })
                    )
                    .bodyToMono(String.class)

                    // 5. Extract response dynamically
                    .map(responseBody -> {
                        try {
                            Object result = JsonPath.read(responseBody, "$." + config.getResponsePath());

                            if (result == null) {
                                throw new RuntimeException("No data at responsePath");
                            }

                            return result.toString();

                        } catch (Exception e) {
                            throw new RuntimeException("Extraction failed: " + e.getMessage());
                        }
                    });

        } catch (Exception e) {
            return Mono.error(new RuntimeException("Invalid JSON template: " + e.getMessage()));
        }
    }
    private void replacePlaceholders(JsonNode node, Map<String, String> variables) {

        if (node.isObject()) {
            ObjectNode obj = (ObjectNode) node;

            obj.fields().forEachRemaining(entry -> {
                JsonNode child = entry.getValue();

                if (child.isTextual()) {
                    String text = child.asText();

                    for (Map.Entry<String, String> var : variables.entrySet()) {
                        text = text.replace("{{" + var.getKey() + "}}", var.getValue());
                    }

                    obj.put(entry.getKey(), text);

                } else {
                    replacePlaceholders(child, variables);
                }
            });

        } else if (node.isArray()) {
            for (JsonNode item : node) {
                replacePlaceholders(item, variables);
            }
        }
    }
}
