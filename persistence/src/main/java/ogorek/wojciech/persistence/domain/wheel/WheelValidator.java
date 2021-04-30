package ogorek.wojciech.persistence.domain.wheel;

import ogorek.wojciech.persistence.domain.configs.validator.Validator;
import ogorek.wojciech.persistence.enums.TyreType;

import java.util.HashMap;
import java.util.Map;

public class WheelValidator implements Validator<Wheel> {
    @Override
    public Map<String, String> validate(Wheel wheel) {
        var errors = new HashMap<String, String>();

        if(wheel == null){
            errors.put("wheel", "object is null");
            return errors;
        }

        var wheelModel = wheel.model;
        if(isWheelModel(wheelModel)){
            errors.put("wheel model is invalid", "model should contain upper case or white spaces: " + wheelModel);
        }
        var wheelSize = wheel.size;
        if(isWheelSize(wheelSize)){
            errors.put("wheel size is invalid", "size should have positive value: " + wheelSize);
        }
        var wheelType = wheel.type;
        if(isTyreType(wheelType)){
            errors.put("tyre type is invalid", "it cannot be null");
        }

        return errors;
    }

    private boolean isWheelModel(String wheelModel){return wheelModel == null || !wheelModel.matches("[A-Z\\s]+");}

    private boolean isWheelSize(int wheelSize){return wheelSize <= 0;}

    private boolean isTyreType(TyreType tyreType){return tyreType == null;}


}
