package ogorek.wojciech.persistence.domain.car;

import ogorek.wojciech.persistence.domain.engine.EngineUtils;
import ogorek.wojciech.persistence.domain.wheel.WheelUtils;
import ogorek.wojciech.persistence.enums.TyreType;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public interface CarUtils {
    Comparator<Car> compareByEnginePower = (car1, car2) -> car1.engine.compareByPower(car2.engine);
    Comparator<Car> compareByWheelSize = (car1, car2) -> car1.wheel.compareBySize(car2.wheel);
    Comparator<Car> compareByComponentsSize = (car1, car2) -> car1.carBody.compareByComponentsSize(car2.carBody);

    Function<Car, TyreType> toTyreType = car -> WheelUtils.toTyreType.apply(car.wheel);
    Function<Car, String> toModel = car -> car.model;
    org.eclipse.collections.api.block.function.Function<Car, BigDecimal> toPrice = car -> car.price;


    Function<Car, Double> toMileage = car -> car.mileage;
    ToDoubleFunction<Car> toMileageStats = car -> car.mileage;

    ToDoubleFunction<Car> toPowerStats = car -> EngineUtils.toPower.apply(car.engine);


}
