# Car Management System 2

Car management system 2 is a simple multi-module java application to show how you can use java streams. Part two
differs from its predecessor because it has been made strictly in DDD convention. The persistence/domain module is fully
encapsulated from rest of the application.

## Installation

Use maven -> [link](https://maven.apache.org/download.cgi) <- to install car management system 1.

```bash
mvn clean install
#go to ui folder 
cd ui
#go to target folder
cd target
#start app
java -jar ui.jar
```

## Usage

```java
/*
 *
 *    ----------  CAR DOMAIN MODEL ----------
 *
 */

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

    // -------- access methods within car model --------
    public boolean hasCarBodyType(CarBodyType carBodyType) {
        return carBody.hasType(carBodyType);
    }

    public boolean carHasEngineType(EngineType engineType) {
        return engine.hasEngineType(engineType);
    }

    public boolean hasPriceInRange(BigDecimal priceFrom, BigDecimal priceTo) {
        return price.compareTo(priceFrom) >= 0 && price.compareTo(priceTo) <= 0;
    }

    public boolean hasCarComponents(List<String> components) {
        return carBody.compareComponents(components);
    }
}

/*
 *
 *    ----------  CAR FUNCTOR INTERFACE  ----------
 *
 */

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

/*
 *
 *    ----------  CAR SERVICE EXAMPLE  ----------
 *
 */
public class CarsService {
    public List<Car> sort(SortItem sortItem, boolean descending) {
        if (sortItem == null) {
            throw new AppException("sort item object is null");
        }

        Stream<Car> carsStream = switch (sortItem) {
            case COMPONENTS_SIZE -> cars.stream().sorted(CarUtils.compareByComponentsSize);
            case ENGINE_POWER -> cars.stream().sorted(CarUtils.compareByEnginePower);
            default -> cars.stream().sorted(CarUtils.compareByWheelSize);
        };
        
        List<Car> sortedCars = carsStream.collect(Collectors.toList());
        if (descending) {
            Collections.reverse(sortedCars);
        }
        return sortedCars;
    }

}




```
