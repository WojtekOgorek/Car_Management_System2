package ogorek.wojciech.persistence.validator.impl;

import ogorek.wojciech.persistence.enums.EngineType;
import ogorek.wojciech.persistence.model.Engine;
import ogorek.wojciech.persistence.validator.generic.AbstractValidator;

import java.util.Map;

public class EngineValidator extends AbstractValidator<Engine> {
    @Override
    public Map<String, String> validate(Engine engine) {

        errors.clear();

        if(engine == null){
            errors.put("engine", "object is null");
            return errors;
        }
        if(!isEngineType(engine.getType())){
            errors.put("engine type", "type object cannot be null" + engine.getType());
        }

        if(!isEnginePowerValid(engine.getPower())){
            errors.put("engine power", "power should have positive value" + engine.getPower());
        }

        return errors;
    }

    private boolean isEngineType(EngineType engineType){return engineType != null;}

    private boolean isEnginePowerValid(double enginePower){return enginePower > 0;}

}
