package AIGender.bias.services.resultCalculationServices;

import AIGender.bias.dtos.ScorePair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BiasMetricsCalculator {

    public double averageDelta(List<ScorePair> data){
        double avg = 0;
        for(ScorePair sp : data){
            avg += sp.getScore1()-sp.getScore2();
        }
        avg /= data.size();

        return avg;
    }
    public double biasScore(List<ScorePair> data){
        double score = 0;
        for(ScorePair sp : data){
            score += Math.abs(sp.getScore1()-sp.getScore2());
        }
        score /= data.size();

        return score;
    }
    public double maxDelta(List<ScorePair> data){
        double maxDelta = 0;
        for(ScorePair sp : data){
            double delta = Math.abs(sp.getScore1()-sp.getScore2());
            if(delta > maxDelta)maxDelta = delta;
        }
        return maxDelta;
    }
}
