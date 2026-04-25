package AIGender.bias.services;

import AIGender.bias.Clients.GroqClient;
import AIGender.bias.entities.AIModel;
import AIGender.bias.entities.AIModelAnswear;
import AIGender.bias.entities.Question;
import AIGender.bias.entities.QuestionVariant;
import AIGender.bias.repos.AIModelAnswearRepo;
import AIGender.bias.repos.AIModelRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AIModelAnswearService {
    private final AIModelAnswearRepo aiModelAnswearRepo;
    private final GroqClient groqClient;
    private final AIModelRepo aiModelRepo;


    public AIModelAnswearService(AIModelAnswearRepo aiModelAnswearRepo, GroqClient groqClient, AIModelRepo aiModelRepo) {
        this.aiModelAnswearRepo = aiModelAnswearRepo;
        this.groqClient = groqClient;
        this.aiModelRepo = aiModelRepo;
    }

    @Transactional
    Mono<Integer> createAIModelAnswear(long modelId, Question question, QuestionVariant questionVariant, String variationType, String answear){
        AIModel aiModel = aiModelRepo.findById(modelId).get();
        Optional<AIModelAnswear> temp = aiModelAnswearRepo.findByAiModelAndGeneralQuestionAndQuestionVariant(aiModel,question,questionVariant);
        AIModelAnswear aiModelAnswear;

        if(temp.isPresent()){
            aiModelAnswear = temp.get();
            aiModelAnswear.setAnswear(answear);
        }else{
            aiModelAnswear = AIModelAnswear.builder()
                    .aiModel(aiModel)
                    .generalQuestion(question)
                    .questionVariant(questionVariant)
                    .variantType(variationType)
                    .answear(answear)
                    .build();

        }
        AIModelAnswear finalAiModelAnswear = aiModelAnswear;

        return groqClient.rateAnwear(questionVariant.getQuestion(),answear)
                .map(score -> {
                    finalAiModelAnswear.setScore(score);
                    aiModelAnswearRepo.save(finalAiModelAnswear);
                    aiModelAnswearRepo.flush();


                    return score;
                });
    }

    List<AIModelAnswear> getAIModelAnswear(long start, long end, long modelId){
        return aiModelAnswearRepo.findByAiModel_IdAndGeneralQuestion_IdBetween(modelId,start,end);
    }

}
