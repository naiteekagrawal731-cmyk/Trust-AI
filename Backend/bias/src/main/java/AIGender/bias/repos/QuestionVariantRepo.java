package AIGender.bias.repos;

import AIGender.bias.entities.QuestionVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionVariantRepo extends JpaRepository<QuestionVariant,Long> {
}
