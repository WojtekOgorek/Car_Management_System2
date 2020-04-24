package ogorek.wojciech.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ogorek.wojciech.persistence.enums.EngineType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Engine {
    private EngineType type;
    private double power;
}
