package ogorek.wojciech.persistence.domain.car_body;

import lombok.*;
import ogorek.wojciech.persistence.enums.CarBodyColor;
import ogorek.wojciech.persistence.enums.CarBodyType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class CarBody {
    CarBodyColor color;
    CarBodyType type;
    List<String> components;

    public int compareByComponentsSize(CarBody carBody) {
        return Integer.compare(components.size(), carBody.components.size());
    }

    public boolean hasType(CarBodyType carBodyType) {
        return carBodyType.equals(type);
    }

    public boolean compareComponents(List<String> carBodyComponents){
        return components.containsAll(carBodyComponents);
    }

}
