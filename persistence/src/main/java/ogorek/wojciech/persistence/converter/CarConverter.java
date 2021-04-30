package ogorek.wojciech.persistence.converter;

import ogorek.wojciech.persistence.domain.car.Car;

public class CarConverter extends JsonConverter<Car> {
    public CarConverter(String jsonFilename) {
        super(jsonFilename);
    }
}


