package ogorek.wojciech.persistence.converter;

public class CarsConverter extends JsonConverter<Cars> {
    public CarsConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
