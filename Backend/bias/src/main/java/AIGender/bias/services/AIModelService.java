package AIGender.bias.services;

import AIGender.bias.dtos.RequestDtos.BiasCheckRequestDto;
import AIGender.bias.dtos.ResponseDtos.VariantQuestionDto;
import AIGender.bias.dtos.ScorePair;
import AIGender.bias.entities.AIModel;
import AIGender.bias.entities.AIModelAnswear;
import AIGender.bias.entities.Question;
import AIGender.bias.repos.AIModelRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@Slf4j
public class AIModelService {

    private final AIModelRepo aiModelRepo;
    private final QuestionsGeneratorService questionsGeneratorService;
    private final QuestionVaiantService questionVaiantService;
    private final AIModelAnswearService aiModelAnswearService;

    public AIModelService(AIModelRepo aiModelRepo, QuestionsGeneratorService questionsGeneratorService, QuestionVaiantService questionVaiantService, AIModelAnswearService aiModelAnswearService) {
        this.aiModelRepo = aiModelRepo;
        this.questionsGeneratorService = questionsGeneratorService;
        this.questionVaiantService = questionVaiantService;
        this.aiModelAnswearService = aiModelAnswearService;
    }

    public Mono<Long> initializationOfModel(BiasCheckRequestDto requestDto){
        Optional<AIModel> model = aiModelRepo.findBymodelName(requestDto.getModelName());
        AIModel aiModel = (model.isPresent())? model.get() : AIModel.builder()
                .modelName(requestDto.getModelName())
                .build();
        aiModel.setVariants(requestDto.getVariants());
        aiModel.setNumQuestions(requestDto.getNumQuestions());

        aiModelRepo.save(aiModel);
        aiModelRepo.flush();

        return questionsGeneratorService.generateQuestions(requestDto)
                .collectList()
                .map(s -> aiModel.getId());
    }

    AIModel getModel(long id){
        Optional<AIModel> aiModel = aiModelRepo.findById(id);
        if(aiModel.isEmpty())throw new RuntimeException("Invalid model id");
        return aiModel.get();
    }



    public List<VariantQuestionDto> getVarientQuestions(long modelId){
        log.info("Model id = "+modelId);
        Optional<AIModel> aiModel = aiModelRepo.findById(modelId);
        if(aiModel.isEmpty())throw new RuntimeException("Model ID is invalid");

        return questionVaiantService.getVariantsQuestion(aiModel.get());
    }

    List<ScorePair> getModelScore(long modelId,String variation1,String variation2){
        AIModel aiModel = getModel(modelId);
        List<AIModelAnswear> answears = aiModelAnswearService.getAIModelAnswear(1,aiModel.getNumQuestions(),modelId);


        Map<Question,ScorePair> scorePairMap = new HashMap<>();
        for(AIModelAnswear aiModelAnswear : answears){
            if(!scorePairMap.containsKey(aiModelAnswear.getGeneralQuestion())){
                scorePairMap.put(aiModelAnswear.getGeneralQuestion(),ScorePair.builder()
                                .variant1(variation1)
                                .variant2(variation2)
                        .build());
                
            }
            ScorePair sp = scorePairMap.get(aiModelAnswear.getGeneralQuestion());
            if(aiModelAnswear.getVariantType().equals(sp.getVariant1())){
                sp.setScore1(aiModelAnswear.getScore());
            }else if(aiModelAnswear.getVariantType().equals(sp.getVariant2())){
                sp.setScore2(aiModelAnswear.getScore());
            }
            
        }
        List<ScorePair> scorePairs = new ArrayList<>();
        for(Question que : scorePairMap.keySet()){
            scorePairs.add(scorePairMap.get(que));
        }
        return scorePairs;
    }
}
