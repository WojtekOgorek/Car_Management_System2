package ogorek.wojciech.service;

import ogorek.wojciech.persistence.converter.CarsConverter;
import ogorek.wojciech.persistence.exception.AppException;
import ogorek.wojciech.persistence.exception.JsonException;
import ogorek.wojciech.persistence.model.Car;
import ogorek.wojciech.persistence.validator.impl.CarValidator;
import ogorek.wojciech.service.enums.SortItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
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
                            if(carValidator.hasErrors()){
                                System.out.println("---------- validation errors for car no." + counter.get() + " in file: "  + jsonFilename + " ------------");
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

    public List<Car> sort(SortItem sortItem, boolean descending){
        if(sortItem == null){
            throw new AppException("sort item object is null");
        }

        Stream<Car> carsStream = switch (sortItem){
            case COMPONENTS -> cars.stream().sorted(Comparator.comparing(components -> components.getCarbody().getComponents().size()));
            case POWER -> cars.stream().sorted(Comparator.comparing(power -> power.getEngine().getPower()));
            case SIZE -> cars.stream().sorted(Comparator.comparing(wheel -> wheel.getWheel().getSize()));
        };
        List<Car> sortedCars = carsStream.collect(Collectors.toList());
        if(descending){
            Collections.reverse(sortedCars);
        }
        return sortedCars;
    }





}
