package AIGender.bias.services.resultCalculationServices;

import AIGender.bias.dtos.ResponseDtos.ModelReport.StabilityMetrics;
import AIGender.bias.dtos.ScorePair;
import AIGender.bias.services.StatsUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StabilityAnalysisService {

    private final StatsUtil statsUtil;

    public StabilityAnalysisService(StatsUtil statsUtil) {
        this.statsUtil = statsUtil;
    }

    public StabilityMetrics analyze(List<ScorePair> scorePairs){
        List<Double> absDelta = new ArrayList<>();
        for(ScorePair sp : scorePairs){
            absDelta.add((double) Math.abs(sp.getScore1()-sp.getScore2()));
        }

        double variance = statsUtil.variance(absDelta);
        double stdDev = Math.sqrt(variance);

        double consistencyScore = 100 * (1 - (stdDev / 50));;//in percent
        consistencyScore = Math.max(0, Math.min(100, consistencyScore));

        return StabilityMetrics.builder()
                .consistencyScore(consistencyScore)
                .varianceAcrossQuestions(variance)
                .build();
    }
}
