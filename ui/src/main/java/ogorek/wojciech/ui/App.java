package ogorek.wojciech.ui;

import ogorek.wojciech.persistence.converter.CarConverter;
import ogorek.wojciech.persistence.converter.CarsConverter;
import ogorek.wojciech.persistence.enums.CarBodyColor;
import ogorek.wojciech.persistence.enums.CarBodyType;
import ogorek.wojciech.persistence.enums.EngineType;
import ogorek.wojciech.persistence.enums.TyreType;
import ogorek.wojciech.persistence.model.Car;
import ogorek.wojciech.persistence.model.CarBody;
import ogorek.wojciech.persistence.model.Engine;
import ogorek.wojciech.persistence.model.Wheel;
import ogorek.wojciech.service.CarsService;
import ogorek.wojciech.service.enums.PickStatistic;
import ogorek.wojciech.service.enums.SortItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class App {
    public static void main(String[] args) {

        Engine e1 = Engine.builder()
                .power(100.0)
                .type(EngineType.GASOLINE)
                .build();
        Engine e2 = Engine.builder()
                .power(50.0)
                .type(EngineType.LPG)
                .build();
        Engine e3 = Engine.builder()
                .power(150.0)
                .type(EngineType.DIESEL)
                .build();

        Wheel w1 = Wheel.builder()
                .model("Pirrelli")
                .size(18)
                .type(TyreType.SUMMER)
                .build();
        Wheel w2 = Wheel.builder()
                .model("Michellin")
                .size(16)
                .type(TyreType.WINTER)
                .build();

        CarBody cb1 = CarBody.builder()
                .color(CarBodyColor.BLACK)
                .type(CarBodyType.COMBI)
                .components(List.of("abs", "ebd", "ac"))
                .build();
        CarBody cb2 = CarBody.builder()
                .color(CarBodyColor.BLUE)
                .type(CarBodyType.HATCHBACK)
                .components(List.of("abs", "ac"))
                .build();
        CarBody cb3 = CarBody.builder()
                .color(CarBodyColor.SILVER)
                .type(CarBodyType.SEDAN)
                .components(List.of("abs", "ebd", "ac", "dvd"))
                .build();


        Car c1 = Car.builder()
                .model("Ferarri")
                .price(new BigDecimal("10000000"))
                .mileage(100)
                .carbody(cb3)
                .engine(e1)
                .wheel(w2)
                .build();

        Car c2 = Car.builder()
                .model("Lambo")
                .price(new BigDecimal("1550000"))
                .mileage(150)
                .carbody(cb2)
                .engine(e2)
                .wheel(w1)
                .build();

        Car c3 = Car.builder()
                .model("Porsche")
                .price(new BigDecimal("500000"))
                .mileage(300)
                .carbody(cb3)
                .engine(e3)
                .wheel(w1)
                .build();


        final String carJsonFilename = "car.json";
        var carConverter = new CarConverter(carJsonFilename);
        carConverter.toJson(c1);
        carConverter.fromJson().ifPresent(System.out::println);

        Set<Car> cars = Set.of(c1, c2, c3);

        final String carsOfJsonFilename = "cars.json";
        var carsConverter = new CarsConverter(carsOfJsonFilename);
        carsConverter.toJson(cars);
        carsConverter.fromJson().ifPresent(System.out::println);

        final Set<String> collectionOfJsonFilename = Set.of(carJsonFilename, carsOfJsonFilename);
        CarsService carsService = new CarsService(collectionOfJsonFilename);
        //1st method

        carsService.sort(SortItem.COMPONENTS, false);
        carsService.sort(SortItem.POWER, false);
        carsService.sort(SortItem.SIZE, false);

        //2nd method

        carsService.specyficCarBodyTypeWithinSelectedPriceRange(cb1.getType(), 10000, 500000);


        //3rd method

        carsService.carsWithPickedEngineType(e1.getType());

        //4th method

        carsService.summarizeMileagePowerAndPrice(PickStatistic.MILEAGE);
        carsService.summarizeMileagePowerAndPrice(PickStatistic.POWER);
        carsService.summarizeMileagePowerAndPrice(PickStatistic.PRICE);

        //5th method

        carsService.carsMileageRecord();

        //6th method

        carsService.listOfCarsWithSpecyficTyres();

        //7th method

        List<String> components = List.of("abs", "ac");
        carsService.carsWithAllPickedComponents(components);
    }
}
