package ogorek.wojciech.service;

import ogorek.wojciech.persistence.converter.CarsConverter;
import ogorek.wojciech.persistence.exception.JsonException;
import ogorek.wojciech.persistence.model.Car;
import ogorek.wojciech.persistence.validator.impl.CarValidator;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
}
