package ogorek.wojciech.persistence.validator.impl;

import ogorek.wojciech.persistence.enums.TyreType;
import ogorek.wojciech.persistence.model.Wheel;
import ogorek.wojciech.persistence.validator.generic.AbstractValidator;

import java.util.Map;

public class WheelValidator extends AbstractValidator<Wheel> {
    @Override
    public Map<String, String> validate(Wheel wheel) {
        errors.clear();

        if(wheel == null){
            errors.put("wheel", "object is null");
            return errors;
        }
        if(!isWheelModel(wheel.getModel())){
            errors.put("wheel model", "model should contain upper case or white spaces: " + wheel.getModel());
        }
        if(!isWheelSize(wheel.getSize())){
            errors.put("wheel size", "size should have positive value" + wheel.getSize());
        }
        if(!isTyreType(wheel.getType())){
            errors.put("tyre type", "type object cannot be null" + wheel.getType());
        }

        return errors;
    }

    private boolean isWheelModel(String wheelModel){return wheelModel != null && wheelModel.matches("[A-Z\\s]+");}

    private boolean isWheelSize(int wheelSize){return wheelSize > 0;}

    private boolean isTyreType(TyreType tyreType){return tyreType != null;}


}
