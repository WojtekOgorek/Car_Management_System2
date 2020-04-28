package ogorek.wojciech.persistence.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistics {
    private Statistic<BigDecimal> priceStatistics;
    private Statistic<Double> mileageStatistics;
    private Statistic<Double> powerStatistics;
}
