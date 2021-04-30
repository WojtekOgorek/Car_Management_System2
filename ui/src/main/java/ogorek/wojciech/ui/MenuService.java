package ogorek.wojciech.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import ogorek.wojciech.persistence.domain.car.Car;
import ogorek.wojciech.persistence.enums.TyreType;
import ogorek.wojciech.persistence.exception.AppException;
import ogorek.wojciech.service.CarsService;
import ogorek.wojciech.service.utils.UserDataService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MenuService {

    private final CarsService carsService;

    public void mainMenu(){
        while(true){
            try{
                int option = chooseOptionFromMainMenu();
                    switch(option) {
                        case 1 -> option1();
                        case 2 -> option2();
                        case 3 -> option3();
                        case 4 -> option4();
                        case 5 -> option5();
                        case 6 -> option6();
                        case 7 -> option7();
                        case 8 -> option8();
                        case 9 ->{
                                UserDataService.close();
                        System.out.println("Have a nice day !");
                        return;
                    }
                        default -> System.out.println("\nWrong option number\n");
                }
            }catch(AppException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private int chooseOptionFromMainMenu(){
        System.out.println("1. Show all cars");
        System.out.println("2. Sort cars");
        System.out.println("3. Get cars specified body type and price between");
        System.out.println("4. Get list of cars grouped by engine type");
        System.out.println("5. Get price and mileage or engine power statistics of cars");
        System.out.println("6. Get cars mileage record");
        System.out.println("7. Get cars with specified tyre types");
        System.out.println("8. Get cars with all specified components");
        System.out.println("9. End of app");
        return UserDataService.getInt("Choose option:");
    }

    private void option1(){ System.out.println(carsService.toString());}

    private void option2() {
        var sortItem = UserDataService.getSortItem();
        boolean descending = UserDataService.getBoolean("Sort descending? Enter y / n");
        List<Car> sortedCars = carsService.sort(sortItem, descending);
        System.out.println(toJson(sortedCars));
    }

    private void option3(){
        var carBodyType = UserDataService.getCarBodyType("Pick car body type");
        var priceFrom = UserDataService.getBigDecimal("Type price from");
        var priceTo = UserDataService.getBigDecimal("Type price to");

        List<Car> carsWithGraterMileage = carsService.findCarsWithCarBodyTypeAndPriceInRange(carBodyType,priceFrom,priceTo);
        System.out.println(toJson(carsWithGraterMileage));
    }

    private void option4(){
        var engineType = UserDataService.getEngineType("Pick engine type");
        var carsByEngineType = carsService.carsWithPickedEngineType(engineType);
        System.out.println(toJson(carsByEngineType));
    }

    private void option5(){
        var statistic = UserDataService.getStatistic("Pick statistic");
        var statistics = carsService.summarizeMileagePowerAndPrice(statistic);
        System.out.println(toJson(statistics));
    }

    private void option6(){
        Map<Car, Double> carMileageRecord = carsService.carsMileageRecord();
        System.out.println(toJson(carMileageRecord));
    }

    private void option7(){
        Map<TyreType, List<Car>> carsWithSpecificTyres = carsService.listOfCarsWithSpecificTyres();
        System.out.println(carsWithSpecificTyres);
    }


    private void option8(){
        var components = UserDataService.getComponents("Components: ABS,EBD,AC,DVD");
        List<Car> carsWithComponents = carsService.carsWithAllPickedComponents(components);
        System.out.println(toJson(carsWithComponents));
    }

    private static <T> String toJson(T t){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(t);
    }


}
