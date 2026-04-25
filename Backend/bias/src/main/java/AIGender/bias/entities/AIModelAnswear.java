package AIGender.bias.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "uk_model_question_variant",
                columnNames = {
                        "ai_model_id",
                        "general_question_id",
                        "question_variant_id"
                }
        )
        ,name = "answear"
)
public class AIModelAnswear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ai_model_id")
    private AIModel aiModel;

    @ManyToOne
    @JoinColumn(name = "general_question_id")
    private Question generalQuestion;

    @ManyToOne
    @JoinColumn(name = "question_variant_id")
    private QuestionVariant questionVariant;

    private String variantType;

    @Column(columnDefinition = "LONGTEXT")
    private String answear;

    private int score;

}
