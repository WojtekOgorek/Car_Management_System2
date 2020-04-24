package ogorek.wojciech.persistence.converter;

import ogorek.wojciech.persistence.model.Car;


public class CarConverter extends JsonConverter<Car> {
    public CarConverter(String jsonFilename) {
        super(jsonFilename);
    }
}


