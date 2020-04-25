package ogorek.wojciech.persistence.converter;

import ogorek.wojciech.persistence.model.Car;

import java.util.Set;

public class CarsConverter extends JsonConverter<Set<Car>> {
    public CarsConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
