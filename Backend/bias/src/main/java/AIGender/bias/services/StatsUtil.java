package AIGender.bias.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsUtil {

    public double variance(List<Double> data) {
        if(data.size() <= 1)return 0;
        double mean = data.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double sqDiffSum = data.stream()
                .mapToDouble(x -> Math.pow(x - mean, 2))
                .sum();

        return (sqDiffSum / (data.size() - 1)); // sample variance
    }
}