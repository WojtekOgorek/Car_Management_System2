package ogorek.wojciech.persistence.domain.engine;

import java.util.function.Function;

public interface EngineUtils {
    Function<Engine, Double> toPower = engine -> engine.power;

}
