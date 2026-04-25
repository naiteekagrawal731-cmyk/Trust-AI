package AIGender.bias.controllers;

import AIGender.bias.dtos.RequestDtos.BiasCheckRequestDto;
import AIGender.bias.services.AIModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/initialization")
@Slf4j
public class ModelInitilizationController {
    private final AIModelService aiModelService;

    public ModelInitilizationController(AIModelService aiModelService) {
        this.aiModelService = aiModelService;
    }

    @PostMapping
    public Mono<Long> modelInitilization(@RequestBody BiasCheckRequestDto biasCheckRequestDto){
        log.info("Inilizing model");
        return aiModelService.initializationOfModel(biasCheckRequestDto);
    }
}
