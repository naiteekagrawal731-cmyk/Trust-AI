package AIGender.bias.services;

import AIGender.bias.Clients.UserAIClient;
import AIGender.bias.dtos.AIConfig;
import AIGender.bias.dtos.RequestDtos.AnswearSubmitionDto;
import AIGender.bias.dtos.ResponseDtos.VariantQuestionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class AutoSubmitService {

    private final AIModelService aiModelService;
    private final UserAIClient userAIClient;
    private final SubmitionService submitionService;

    public AutoSubmitService(AIModelService aiModelService, UserAIClient userAIClient, SubmitionService submitionService) {
        this.aiModelService = aiModelService;
        this.userAIClient = userAIClient;
        this.submitionService = submitionService;
    }

    public Flux<Integer> autoSubmit(AIConfig aiConfig){
        System.out.println("Model id = "+aiConfig.getModelId());

        List<VariantQuestionDto> questions =  aiModelService.getVarientQuestions(aiConfig.getModelId());
        log.info("Question getting succesful");

        return Flux.fromIterable(questions)
                .concatMap(que ->
                        userAIClient.callAI(aiConfig, que.getQuestion())
                                .flatMap(ans -> {
                                    

                                    AnswearSubmitionDto dto = AnswearSubmitionDto.builder()
                                            .modelId(aiConfig.getModelId())
                                            .answer(ans)
                                            .generalQuestionId(que.getGeneralQuestionId())
                                            .questionId(que.getQuestionId())
                                            .build();

                                    return submitionService.submitAnswear(dto);

                                })
                );
    }
}
