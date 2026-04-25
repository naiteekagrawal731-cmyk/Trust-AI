package AIGender.bias.controllers;

import AIGender.bias.dtos.RequestDtos.AnswearSubmitionDto;
import AIGender.bias.services.SubmitionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/submit")
public class AnswearSubmitController {

    private final SubmitionService submitionService;

    public AnswearSubmitController(SubmitionService submitionService) {
        this.submitionService = submitionService;
    }

    @PostMapping
    public Mono<Integer> submitAnswear(@RequestBody AnswearSubmitionDto answearSubmitionDto){
        return submitionService.submitAnswear(answearSubmitionDto);
    }
}
