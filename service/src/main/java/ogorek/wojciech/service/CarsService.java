package ogorek.wojciech.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ogorek.wojciech.persistence.converter.CarsConverter;
import ogorek.wojciech.persistence.data.Statistic;
import ogorek.wojciech.persistence.data.Statistics;
import ogorek.wojciech.persistence.domain.car.Car;
import ogorek.wojciech.persistence.domain.car.CarUtils;
import ogorek.wojciech.persistence.domain.car.CarValidator;
import ogorek.wojciech.persistence.domain.configs.validator.Validator;
import ogorek.wojciech.persistence.enums.CarBodyType;
import ogorek.wojciech.persistence.enums.EngineType;
import ogorek.wojciech.persistence.enums.TyreType;
import ogorek.wojciech.persistence.exception.AppException;
import ogorek.wojciech.persistence.exception.JsonException;
import ogorek.wojciech.service.enums.PickStatistic;
import ogorek.wojciech.service.enums.SortItem;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsService {

    private final Set<Car> cars;

    public CarsService(Set<String> jsonFilenames) {
        cars = readCarsFromJsonCollection(jsonFilenames);
    }

    private Set<Car> readCarsFromJsonCollection(Set<String> jsonFilenames) {
        return jsonFilenames
                .stream()
                .flatMap(jsonFilename ->
                        new CarsConverter(jsonFilename)
                                .fromJson()
                                .orElseThrow(() -> new JsonException("cars service - cannot read data from json file: "))
                                .stream()
                                .peek(car -> Validator.validate(new CarValidator(), car)))
                .collect(Collectors.toSet());
    }

    //method 1. Returns all cars from json file
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return cars
                .stream()
                .map(gson::toJson)
                .collect(Collectors.joining("\n"));
    }

    //method 2. Returns car collection sorted in a picked way.
    //It can be sorted by: number of components, engine power, wheel size. Sort can be
    //ascending or descending.

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

    //method 3. Return car collection of specified car body type that is
    //passed as an argument and price from <a,b> range where a and b are also
    //passed as arguments.

    public List<Car> findCarsWithCarBodyTypeAndPriceInRange(CarBodyType carBodyType, BigDecimal priceFrom, BigDecimal priceTo) {

        if (carBodyType == null) {
            throw new AppException("car body type is null");
        }

        if (priceFrom == null) {
            throw new AppException("Price from is null");
        }

        if (priceTo == null) {
            throw new AppException("Price to is null");
        }

        if (priceFrom.compareTo(priceTo) > 0) {
            throw new AppException("Incorrect price range");
        }

        return cars
                .stream()
                .filter(car -> car.hasCarBodyType(carBodyType) && car.hasPriceInRange(priceFrom, priceTo))
                .collect(Collectors.toList());
    }

    //Method 4. Returns car that have specific engin type passed as an argument.
    // Collection must be sorted alphabetically by car model

    public List<Car> carsWithPickedEngineType(EngineType engineType){
        if(engineType == null){
            throw new AppException("engine type is null");
        }

        Stream<Car> carsStream = switch (engineType){
            case DIESEL -> cars.stream().filter(car -> car.carHasEngineType(EngineType.DIESEL)).sorted(Comparator.comparing(CarUtils.toModel));
            case GASOLINE -> cars.stream().filter(car -> car.carHasEngineType(EngineType.GASOLINE)).sorted(Comparator.comparing(CarUtils.toModel));
            case LPG -> cars.stream().filter(car -> car.carHasEngineType(EngineType.LPG)).sorted(Comparator.comparing(CarUtils.toModel));
            default -> cars.stream().sorted(Comparator.comparing(CarUtils.toModel));
        };

        return carsStream.collect(Collectors.toList());
    }

    //method 5. Return statistics data for value that is passed as the method's argument
    //It can be: price, mileage, engine power. Statistics must includes min, max and avg values.

    public Statistics.StatisticsBuilder summarizeMileagePowerAndPrice(PickStatistic pickStatistic) {
        if (pickStatistic == null) {
            throw new AppException("pick statistic is null");
        }

        DoubleSummaryStatistics mileageStatistics = cars.stream().collect(Collectors.summarizingDouble(CarUtils.toMileageStats));
        DoubleSummaryStatistics powerStatistics = cars.stream().collect(Collectors.summarizingDouble(CarUtils.toPowerStats));
        BigDecimalSummaryStatistics priceStatistics = cars.stream().collect(Collectors2.summarizingBigDecimal(CarUtils.toPrice));


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
        //method 6. Return Map where key is Car object and value is number of km that car has done
        //Pairs are sorted descending by the value;

        public Map<Car, Double> carsMileageRecord() {
            return cars
                    .stream()
                    .collect(Collectors.toMap(Function.identity(), CarUtils.toMileage))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Double::max, LinkedHashMap::new));

        }

        //method 7. Returns map where key is tyre type and value is list of cars with those type of tyres
        //Map is sorted descending by number of elements in collection

        public Map<TyreType, List<Car>> listOfCarsWithSpecificTyres() {

        return cars
                .stream()
                .collect(Collectors.groupingBy(CarUtils.toTyreType))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        //method 8. Returns car collection that contains all components that are passed as a collection
        // in method's signature.

        public List<Car> carsWithAllPickedComponents(List<String> carComponents){
            if(carComponents == null){
                throw new AppException("car components object is null");
            }

            return cars
                    .stream()
                    .filter(car -> car.hasCarComponents(carComponents))
                    .collect(Collectors.toList());
        }

}
