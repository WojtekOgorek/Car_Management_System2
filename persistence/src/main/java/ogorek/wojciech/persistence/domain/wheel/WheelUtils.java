package ogorek.wojciech.persistence.domain.wheel;

import ogorek.wojciech.persistence.enums.TyreType;

import java.util.function.Function;

public interface WheelUtils {
    Function<Wheel, TyreType> toTyreType = wheel -> wheel.type;
}
