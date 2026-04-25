package AIGender.bias.repos;

import AIGender.bias.entities.AIModel;
import AIGender.bias.entities.AIModelAnswear;
import AIGender.bias.entities.Question;
import AIGender.bias.entities.QuestionVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AIModelAnswearRepo extends JpaRepository<AIModelAnswear,Long> {


    Optional<AIModelAnswear> findByAiModelAndGeneralQuestionAndQuestionVariant(
            AIModel aiModel,
            Question generalQuestion,
            QuestionVariant questionVariant
    );

    @Query("""
    SELECT a FROM AIModelAnswear a
    WHERE a.aiModel.id = :modelId
    AND a.generalQuestion.id BETWEEN :start AND :end
""")
    List<AIModelAnswear> findByAiModel_IdAndGeneralQuestion_IdBetween(
            @Param("modelId") Long modelId,
            @Param("start") Long start,
            @Param("end") Long end
    );
}
