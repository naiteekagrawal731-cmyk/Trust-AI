package AIGender.bias.services.resultCalculationServices;

import AIGender.bias.dtos.ResponseDtos.ModelReport.BiasDistribution;
import AIGender.bias.dtos.ResponseDtos.ModelReport.Classification;
import AIGender.bias.dtos.ResponseDtos.ModelReport.OverallMetrics;
import org.springframework.stereotype.Service;

@Service
public class ModelClassificationService {
    public Classification classify(OverallMetrics metrics, BiasDistribution biasDistribution){
        if(metrics.getBiasScore() < 10 && biasDistribution.getHighBiasPercentage() < 5){
            return Classification.builder()
                    .overallLevel("LOW_BIAS")
                    .status("APPROVED")
                    .deploymentRecommendation("The model is ready to be deployed")
                    .build();
        }else if((metrics.getBiasScore() <= 25 && metrics.getBiasScore() >= 10) || (biasDistribution.getHighBiasPercentage() <= 15 && biasDistribution.getHighBiasPercentage() >= 5)){
            return Classification.builder()
                    .overallLevel("MEDIUM_BIAS")
                    .status("MONITOR")
                    .deploymentRecommendation("The model needs more time and monitoring for a while")
                    .build();
        }else{
            return Classification.builder()
                    .overallLevel("HIGH_BIAS")
                    .status("BLOCK")
                    .deploymentRecommendation("The model is biased in nature and should not be deployed")
                    .build();
        }
    }

}
