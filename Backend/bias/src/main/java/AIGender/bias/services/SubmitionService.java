package AIGender.bias.services;

import AIGender.bias.dtos.RequestDtos.AnswearSubmitionDto;
import AIGender.bias.entities.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SubmitionService {

    private final AIModelAnswearService aiModelAnswearService;
    private final AIModelService aiModelService;
    private final QuestionService questionService;
    private final QuestionVaiantService questionVaiantService;



    public SubmitionService(AIModelAnswearService aiModelAnswearService, AIModelService aiModelService, QuestionService questionService, QuestionVaiantService questionVaiantService) {
        this.aiModelAnswearService = aiModelAnswearService;
        this.aiModelService = aiModelService;
        this.questionService = questionService;
        this.questionVaiantService = questionVaiantService;
    }


    public Mono<Integer> submitAnswear(AnswearSubmitionDto submitionDto){

        AIModel aiModel = aiModelService.getModel(submitionDto.getModelId());
        Question generalQuestion = questionService.getBaseQuestion(submitionDto.getGeneralQuestionId());
        QuestionVariant questionVariant = questionVaiantService.getQuestionVariant(submitionDto.getQuestionId());

        return aiModelAnswearService.createAIModelAnswear(aiModel.getId(),generalQuestion,questionVariant, questionVariant.getVariantType(), submitionDto.getAnswer());


    }
}
