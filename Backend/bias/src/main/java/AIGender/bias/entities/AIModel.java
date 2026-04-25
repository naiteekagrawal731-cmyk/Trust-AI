package AIGender.bias.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "model")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String modelName;
    private List<String> variants;
    private int numQuestions;
}
