package ogorek.wojciech.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ogorek.wojciech.persistence.enums.CarBodyColor;
import ogorek.wojciech.persistence.enums.CarBodyType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CarBody {
    private CarBodyColor color;
    private CarBodyType type;
    private List<String> components;

}
