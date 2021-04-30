package ogorek.wojciech.persistence.domain.car;


import ogorek.wojciech.persistence.domain.car_body.CarBodyValidator;
import ogorek.wojciech.persistence.domain.configs.validator.Validator;
import ogorek.wojciech.persistence.domain.engine.EngineValidator;
import ogorek.wojciech.persistence.domain.wheel.WheelValidator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarValidator implements Validator<Car> {

    @Override
    public Map<String, String> validate(Car car) {
        var errors = new HashMap<String, String>();

        if (car == null) {
            errors.put("car object", "is null");
            return errors;
        }

        var carModel = car.model;
        if (isCarModelNotValid(carModel)) {
            errors.put("car model", "is not valid");
        }

        var carPrice = car.price;
        if(isCarPriceNotValid(carPrice)){
            errors.put("car price", "is not valid");
        }

        var carMileage = car.mileage;
        if(isCarMileageNotValid(carMileage)){
            errors.put("car mileage", "is not valid");
        }

        // .... POZOSTALE WALIDACJE DLA POL

        // Podpinamy inne walidatory
        var carBodyValidator = new CarBodyValidator();
        var carBodyErrors = carBodyValidator.validate(car.carBody);
        errors.putAll(carBodyErrors);

        var engineValidator = new EngineValidator();
        var engineErrors = engineValidator.validate(car.engine);
        errors.putAll(engineErrors);

        var wheelValidator = new WheelValidator();
        var wheelErrors = wheelValidator.validate(car.wheel);
        errors.putAll(wheelErrors);

        return errors;
    }

    private boolean isCarModelNotValid(String carModel){return carModel == null || !carModel.matches("[A-Z\\s]+");}

    private boolean isCarPriceNotValid(BigDecimal carPrice) {return carPrice == null || carPrice.compareTo(BigDecimal.ZERO) <= 0;}

    private boolean isCarMileageNotValid(double carMileage) {return carMileage <= 0;}

}
