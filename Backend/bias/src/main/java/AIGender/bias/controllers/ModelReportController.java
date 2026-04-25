package AIGender.bias.controllers;

import AIGender.bias.dtos.RequestDtos.ModelEvaluationRequest;
import AIGender.bias.dtos.ResponseDtos.ModelReport.ModelEvaluationReport;
import AIGender.bias.services.ModelEvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evaluation/report")
@Slf4j
public class ModelReportController {

    private final ModelEvaluationService modelEvaluationService;

    public ModelReportController(ModelEvaluationService modelEvaluationService) {
        this.modelEvaluationService = modelEvaluationService;
    }

    @PostMapping
    public ModelEvaluationReport getModelEvalutionReport(@RequestBody ModelEvaluationRequest modelEvaluationRequest){
        log.info("Getting model report");
        return modelEvaluationService.evaluate(modelEvaluationRequest);
    }
}
