package ogorek.wojciech.persistence.domain.wheel;

import lombok.*;
import ogorek.wojciech.persistence.enums.TyreType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Wheel {
    String model;
    int size;
    TyreType type;

    public int compareBySize(Wheel wheel) {
        return Integer.compare(size, wheel.size);
    }

}
