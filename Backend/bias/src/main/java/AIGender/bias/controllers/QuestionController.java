package AIGender.bias.controllers;

import AIGender.bias.dtos.RequestDtos.GetQuestionDto;
import AIGender.bias.dtos.ResponseDtos.VariantQuestionDto;
import AIGender.bias.services.AIModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final AIModelService aiModelService;

    public QuestionController(AIModelService aiModelService) {
        this.aiModelService = aiModelService;
    }

    @PostMapping
    public List<VariantQuestionDto> getQuestions(@RequestBody GetQuestionDto questionDto){
        return aiModelService.getVarientQuestions(questionDto.getModelId());
    }
}
