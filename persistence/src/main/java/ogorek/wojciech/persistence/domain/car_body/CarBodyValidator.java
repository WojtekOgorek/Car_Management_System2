package ogorek.wojciech.persistence.domain.car_body;

import ogorek.wojciech.persistence.domain.configs.validator.Validator;
import ogorek.wojciech.persistence.enums.CarBodyColor;
import ogorek.wojciech.persistence.enums.CarBodyType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarBodyValidator implements Validator<CarBody> {

    @Override
    public Map<String, String> validate(CarBody carBody) {

        var errors = new HashMap<String, String>();


        if(carBody == null){
            errors.put("car body object", "is null");
            return errors;
        }
        var carBodyColor = carBody.color;
        if(isCarBodyColorValid(carBodyColor)){
            errors.put("car body color", "color object cannot be null");
        }
        var carBodyType = carBody.type;
        if(isCarBodyTypeValid(carBodyType)){
            errors.put("car body type" , "type object cannot be null");
        }
        var carBodyComponents = carBody.components;
        if(areCarBodyComponentsValid(carBodyComponents)){
            errors.put("car body components", "components should contain only upper case and white spaces" + carBodyComponents);
        }

        return errors;

    }


    private boolean isCarBodyColorValid(CarBodyColor carBodyColor){return carBodyColor == null;}

    private boolean isCarBodyTypeValid(CarBodyType carBodyType){return carBodyType == null;}

    private boolean areCarBodyComponentsValid(List<String> carComponents ){
        return carComponents == null || !carComponents.stream().allMatch(component -> component.matches("[A-Z\\s]+"));
    }
}
