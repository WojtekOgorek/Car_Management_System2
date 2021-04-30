package ogorek.wojciech.persistence.domain.car;

import lombok.*;
import ogorek.wojciech.persistence.domain.car_body.CarBody;
import ogorek.wojciech.persistence.domain.engine.Engine;
import ogorek.wojciech.persistence.domain.wheel.Wheel;
import ogorek.wojciech.persistence.enums.CarBodyType;
import ogorek.wojciech.persistence.enums.EngineType;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Car {
    String model;
    BigDecimal price;
    double mileage;
    Engine engine;
    CarBody carBody;
    Wheel wheel;

    public boolean hasCarBodyType(CarBodyType carBodyType) {
        return carBody.hasType(carBodyType);
    }

    public boolean carHasEngineType(EngineType engineType){
        return engine.hasEngineType(engineType);}

    public boolean hasPriceInRange(BigDecimal priceFrom, BigDecimal priceTo) {
        return price.compareTo(priceFrom) >= 0 && price.compareTo(priceTo) <= 0;
    }

    public boolean hasCarComponents(List<String> components){
        return carBody.compareComponents(components);
    }


}
