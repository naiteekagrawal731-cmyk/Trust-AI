package AIGender.bias.controllers;

import AIGender.bias.dtos.AIConfig;
import AIGender.bias.services.AutoSubmitService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AutoSubmitController {

    private final AutoSubmitService autoSubmitService;

    public AutoSubmitController(AutoSubmitService autoSubmitService) {
        this.autoSubmitService = autoSubmitService;
    }

    @PostMapping("/auto-submit")
    public Flux<Integer> autoSubmit(@RequestBody AIConfig config) {
        return autoSubmitService.autoSubmit(config);
    }
}
