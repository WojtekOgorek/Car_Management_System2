package ogorek.wojciech.persistence.domain.engine;

import lombok.*;
import ogorek.wojciech.persistence.enums.EngineType;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Engine {
    EngineType type;
    double power;

    public int compareByPower(Engine engine) {
        return Double.compare(power, engine.power);
    }

    public boolean hasEngineType(EngineType engineType) {
        return engineType.equals(type);
    }




}
