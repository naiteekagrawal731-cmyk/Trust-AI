package AIGender.bias.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "question_variant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String variantType;
    @ManyToOne
    private Question generalQuestion;
    @Lob
    @Column(unique = true,name = "content", columnDefinition = "LONGTEXT")
    private String question;

}
