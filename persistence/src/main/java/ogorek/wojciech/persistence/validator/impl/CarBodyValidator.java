package ogorek.wojciech.persistence.validator.impl;

import ogorek.wojciech.persistence.enums.CarBodyColor;
import ogorek.wojciech.persistence.enums.CarBodyType;
import ogorek.wojciech.persistence.model.CarBody;
import ogorek.wojciech.persistence.validator.generic.AbstractValidator;

import java.util.List;
import java.util.Map;

public class CarBodyValidator extends AbstractValidator<CarBody> {

    @Override
    public Map<String, String> validate(CarBody carBody) {

        errors.clear();

        if(carBody == null){
            errors.put("carbody", "object is null");
            return errors;
        }
        if(!isCarBodyColorValid(carBody.getColor())){
            errors.put("carbody color", "color object cannot be null" + carBody.getColor());
        }
        if(!isCarBodyTypeValid(carBody.getType())){
            errors.put("carbody type" , "type object cannot be null" + carBody.getType());
        }
        if(!areCarBodyComponentsValid(carBody.getComponents())){
            errors.put("carbody components", "components should contain only upper case and white spaces" + carBody.getComponents());
        }

        return errors;

    }


    private boolean isCarBodyColorValid(CarBodyColor carBodyColor){return carBodyColor != null;}

    private boolean isCarBodyTypeValid(CarBodyType carBodyType){return carBodyType != null;}

    private boolean areCarBodyComponentsValid(List<String> carComponents ){
        return carComponents != null && carComponents.stream().allMatch(component -> component.matches("[A-Z\\s]+"));
    }
}
