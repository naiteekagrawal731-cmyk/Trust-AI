package AIGender.bias.repos;

import AIGender.bias.entities.AIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AIModelRepo extends JpaRepository<AIModel,Long> {

    Optional<AIModel> findBymodelName(String modelName);


}
