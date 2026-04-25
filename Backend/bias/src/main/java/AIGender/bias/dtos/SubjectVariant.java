package AIGender.bias.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectVariant {
    private String subject;
    private String pronoun;
    private String possessive;
}
