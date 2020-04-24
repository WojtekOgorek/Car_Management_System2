package ogorek.wojciech.persistence.converter;

import ogorek.wojciech.persistence.model.Cars;

public class CarsConverter extends JsonConverter<Cars> {
    public CarsConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
