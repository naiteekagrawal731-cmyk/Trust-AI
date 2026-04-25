package AIGender.bias.repos;

import AIGender.bias.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {

    Optional<Question> findByBaseQuestion(String baseQuestion);
}
