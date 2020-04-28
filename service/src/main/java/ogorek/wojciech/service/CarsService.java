package ogorek.wojciech.service;

import ogorek.wojciech.persistence.converter.CarsConverter;
import ogorek.wojciech.persistence.data.Statistic;
import ogorek.wojciech.persistence.data.Statistics;
import ogorek.wojciech.persistence.enums.CarBodyType;
import ogorek.wojciech.persistence.enums.EngineType;
import ogorek.wojciech.persistence.exception.AppException;
import ogorek.wojciech.persistence.exception.JsonException;
import ogorek.wojciech.persistence.model.Car;
import ogorek.wojciech.persistence.validator.impl.CarValidator;
import ogorek.wojciech.service.enums.PickStatistic;
import ogorek.wojciech.service.enums.SortItem;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsService {

    private final Set<Car> cars;

    public CarsService(Set<String> jsonFilenames) {
        cars = readCarsFromJsonCollection(jsonFilenames);
    }

    private Set<Car> readCarsFromJsonCollection(Set<String> jsonFilenames) {

        var carValidator = new CarValidator();
        var counter = new AtomicInteger(1);

        return jsonFilenames
                .stream()
                .flatMap(jsonFilename ->
                        new CarsConverter(jsonFilename)
                                .fromJson()
                                .orElseThrow(() -> new JsonException("cars service - cannot read data from json file: "))
                                .stream()
                                .filter(car -> {
                                    var errors = carValidator.validate(car);
                                    if (carValidator.hasErrors()) {
                                        System.out.println("---------- validation errors for car no." + counter.get() + " in file: " + jsonFilename + " ------------");
                                        errors.forEach((k, v) -> System.out.println(k + " " + v));
                                        System.out.println("\n\n");
                                    }
                                    counter.incrementAndGet();
                                    return !carValidator.hasErrors();
                                }))
                .collect(Collectors.toSet());

    }

    //method 1. Returns car collection sorted in a way passed as an argument.
    //It can be sorted by: number of components, engine power, wheel size. Sort can be
    //ascending or descending.

    public List<Car> sort(SortItem sortItem, boolean descending) {
        if (sortItem == null) {
            throw new AppException("sort item object is null");
        }

        Stream<Car> carsStream = switch (sortItem) {
            case COMPONENTS -> cars.stream().sorted(Comparator.comparing(components -> components.getCarbody().getComponents().size()));
            case POWER -> cars.stream().sorted(Comparator.comparing(power -> power.getEngine().getPower()));
            case SIZE -> cars.stream().sorted(Comparator.comparing(wheel -> wheel.getWheel().getSize()));
            default -> cars.stream().sorted(Comparator.comparing(Car::getModel));
        };
        List<Car> sortedCars = carsStream.collect(Collectors.toList());
        if (descending) {
            Collections.reverse(sortedCars);
        }
        return sortedCars;
    }

    //method 2. Return car collection of specified car body type that is
    //passed as an argument and price from <a,b> range where a and b are also
    //passed as arguments.

    public List<Car> specyficCarBodyTypeWithinSelectedPriceRange(CarBodyType carBodyType, double a, double b) {

        if (carBodyType == null) {
            throw new AppException("car body type is null");
        }

        if (a < 0 && b < 0 || a > b) {
            throw new AppException("cars with picked car body type price range is invalid");
        }
        Stream<Car> carsStream = switch (carBodyType) {
            case COMBI -> cars.stream().filter(car -> car.getCarbody().getType() == CarBodyType.COMBI
                    && car.getPrice().compareTo(BigDecimal.valueOf(a)) > 0
                    && car.getPrice().compareTo(BigDecimal.valueOf(b)) < 0)
                    .sorted(Comparator.comparing(Car::getModel));
            case HATCHBACK -> cars.stream().filter(car -> car.getCarbody().getType() == CarBodyType.HATCHBACK
                    && car.getPrice().compareTo(BigDecimal.valueOf(a)) > 0
                    && car.getPrice().compareTo(BigDecimal.valueOf(b)) < 0)
                    .sorted(Comparator.comparing(Car::getModel));
            case SEDAN -> cars.stream().filter(car -> car.getCarbody().getType() == CarBodyType.SEDAN
                    && car.getPrice().compareTo(BigDecimal.valueOf(a)) > 0
                    && car.getPrice().compareTo(BigDecimal.valueOf(b)) < 0)
                    .sorted(Comparator.comparing(Car::getModel));
            default -> cars.stream().sorted(Comparator.comparing(Car::getModel));
        };
        List<Car> picedCars = carsStream.collect(Collectors.toList());

        return picedCars;
    }

    //Method 3. Returns car that have specyfic engin type passed as an argument.
    // Collection must be sorted alphabetically by car model

    public List<Car> carsWithPickedEngineType(EngineType engineType){
        if(engineType == null){
            throw new AppException("engine type is null");
        }

        Stream<Car> carsStream = switch (engineType){
            case DIESEL -> cars.stream().filter(car -> car.getEngine().getType() == EngineType.DIESEL).sorted(Comparator.comparing(Car::getModel));
            case GASOLINE -> cars.stream().filter(t -> t.getEngine().getType() == EngineType.GASOLINE).sorted(Comparator.comparing(Car::getModel));
            case LPG -> cars.stream().filter(t -> t.getEngine().getType() == EngineType.LPG).sorted(Comparator.comparing(Car::getModel));
            default -> cars.stream().sorted(Comparator.comparing(Car::getModel));
        };

        List<Car> pickedCars = carsStream.collect(Collectors.toList());
        return pickedCars;
    }

    //method 4. Return statistics data for value that is passed as the method's argument
    //It can be: price, mileage, engine power. Statistics must includes min, max and avg values.

    public Statistics.StatisticsBuilder summarizeMileagePowerAndPrice(PickStatistic pickStatistic) {
        if (pickStatistic == null) {
            throw new AppException("pick statistic is null");
        }

        DoubleSummaryStatistics mileageStatistics = cars.stream().collect(Collectors.summarizingDouble(Car::getMileage));
        DoubleSummaryStatistics powerStatistics = cars.stream().collect(Collectors.summarizingDouble(c -> c.getEngine().getPower()));
        BigDecimalSummaryStatistics priceStatistics = cars.stream().collect(Collectors2.summarizingBigDecimal(Car::getPrice));


        Statistic<Double> mileageStatistic = Statistic.<Double>builder()
                .min(mileageStatistics.getMin())
                .max(mileageStatistics.getMax())
                .avg(mileageStatistics.getAverage())
                .build();

        Statistic<Double> powerStatistic = Statistic.<Double>builder()
                .min(powerStatistics.getMin())
                .max(powerStatistics.getMax())
                .avg(powerStatistics.getAverage())
                .build();

        Statistic<BigDecimal> priceStatistic = Statistic.<BigDecimal>builder()
                .min(priceStatistics.getMin())
                .max(priceStatistics.getMax())
                .avg(priceStatistics.getAverage())
                .build();


        switch (pickStatistic) {
            case MILEAGE -> {
                return Statistics.builder().mileageStatistics(mileageStatistic);
            }
            case POWER -> {
                return Statistics.builder().powerStatistics(powerStatistic);
            }
            case PRICE -> {
                return Statistics.builder().priceStatistics(priceStatistic);
            }
            default -> {
                return Statistics.builder().mileageStatistics(mileageStatistic)
                        .powerStatistics(powerStatistic).priceStatistics(priceStatistic);
            }
        }


    }


}
