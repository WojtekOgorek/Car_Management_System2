package ogorek.wojciech.persistence.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistic<T> {
    private T min;
    private T max;
    private T avg;

}
