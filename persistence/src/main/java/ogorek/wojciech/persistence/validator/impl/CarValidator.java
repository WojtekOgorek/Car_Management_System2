package ogorek.wojciech.persistence.validator.impl;

import ogorek.wojciech.persistence.model.Car;
import ogorek.wojciech.persistence.model.CarBody;
import ogorek.wojciech.persistence.model.Engine;
import ogorek.wojciech.persistence.model.Wheel;
import ogorek.wojciech.persistence.validator.generic.AbstractValidator;

import java.math.BigDecimal;
import java.util.Map;

public class CarValidator extends AbstractValidator<Car> {

    @Override
    public Map<String, String> validate(Car car) {
        errors.clear();
        if(car == null){
            errors.put("car", "object is null");
            return errors;
        }

        if(!isCarModelValid(car.getModel())){
            errors.put("car model", "model should only contain upper cases and white spaces: " + car.getModel());
        }
        if(!isCarPriceValid(car.getPrice())){
            errors.put("car price", "price should have positive value" + car.getPrice());
        }
        if(!isCarMileageValid(car.getMileage())) {
            errors.put("car mileage", "mileage should have positive value" + car.getMileage());
        }
        if(!isCarEngineValid(car.getEngine())){
            errors.put("car engine", "engine cannot be null" + car.getEngine());
        }
        if(!isCarBodyValid(car.getCarbody())){
            errors.put("car body", "body cannot be null" + car.getCarbody());
        }
        if(!isCarWheelValid(car.getWheel())){
            errors.put("car wheel", "wheel cannot be null" + car.getWheel());
        }

        return errors;
    }

    private boolean isCarModelValid(String carModel){return carModel != null && carModel.matches("[A-Z\\s]+");}

    private boolean isCarPriceValid(BigDecimal carPrice) {return carPrice != null && carPrice.compareTo(BigDecimal.ZERO) > 0;}

    private boolean isCarMileageValid(double carMileage) {return carMileage > 0;}

    private boolean isCarEngineValid(Engine carEngine){return carEngine != null;}

    private boolean isCarBodyValid(CarBody carBody){return carBody != null;}

    private boolean isCarWheelValid(Wheel carWheel) {return carWheel != null;}
}
