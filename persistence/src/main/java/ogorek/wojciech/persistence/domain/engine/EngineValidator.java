package ogorek.wojciech.persistence.domain.engine;

import ogorek.wojciech.persistence.domain.configs.validator.Validator;
import ogorek.wojciech.persistence.enums.EngineType;

import java.util.HashMap;
import java.util.Map;

public class EngineValidator implements Validator<Engine> {
    @Override
    public Map<String, String> validate(Engine engine) {

        var errors = new HashMap<String, String>();

        if(engine == null){
            errors.put("engine object", "is null");
            return errors;
        }

        var engineType = engine.type;
        if(isEngineType(engineType)){
            errors.put("engine type", "type object cannot be null");
        }

        var enginePower = engine.power;
        if(isEnginePowerValid(enginePower)){
            errors.put("engine power", "power should have positive value" + enginePower);
        }

        return errors;
    }

    private boolean isEngineType(EngineType engineType){return engineType == null;}

    private boolean isEnginePowerValid(double enginePower){return enginePower < 0;}

}
