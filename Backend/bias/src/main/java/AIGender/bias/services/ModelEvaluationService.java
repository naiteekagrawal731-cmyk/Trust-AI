package AIGender.bias.services;

import AIGender.bias.dtos.RequestDtos.ModelEvaluationRequest;
import AIGender.bias.dtos.ResponseDtos.ModelReport.*;
import AIGender.bias.dtos.ScorePair;
import AIGender.bias.services.resultCalculationServices.BiasAggregationService;
import AIGender.bias.services.resultCalculationServices.ModelClassificationService;
import AIGender.bias.services.resultCalculationServices.StabilityAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ModelEvaluationService {

    private final AIModelService aiModelService;
    private final BiasAggregationService biasAggregationService;
    private final ModelClassificationService modelClassificationService;
    private final StabilityAnalysisService stabilityAnalysisService;

    public ModelEvaluationService(AIModelService aiModelService, BiasAggregationService biasAggregationService, ModelClassificationService modelClassificationService, StabilityAnalysisService stabilityAnalysisService) {
        this.aiModelService = aiModelService;
        this.biasAggregationService = biasAggregationService;
        this.modelClassificationService = modelClassificationService;
        this.stabilityAnalysisService = stabilityAnalysisService;
    }

    public ModelEvaluationReport evaluate(ModelEvaluationRequest request){
        List<ScorePair> data = aiModelService.getModelScore(request.getModelId(),request.getVariation1(),request.getVariation2());


        BiasDistribution biasDistribution = biasAggregationService.buildDistribution(data);
        OverallMetrics overallMetrics = biasAggregationService.buildMetrics(data);
        Classification classification = modelClassificationService.classify(overallMetrics,biasDistribution);
        StabilityMetrics stabilityMetrics = stabilityAnalysisService.analyze(data);

        return ModelEvaluationReport.builder()
                .modelId(request.getModelId())
                .biasDistribution(biasDistribution)
                .overallMetrics(overallMetrics)
                .classification(classification)
                .stabilityMetrics(stabilityMetrics)
                .build();
    }
}
