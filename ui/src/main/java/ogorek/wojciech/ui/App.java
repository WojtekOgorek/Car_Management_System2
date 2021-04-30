package ogorek.wojciech.ui;

import ogorek.wojciech.service.CarsService;

import java.util.Set;

public class App {
    public static void main(String[] args) {
        final Set<String> collectionOfJsonFilename = Set.of(
                "C:\\Work\\KmPrograms\\Java\\Coding\\Projekty\\Car_Management_System2_New_Era\\ui\\src\\main\\resources\\cars.json");

        CarsService carsService = new CarsService(collectionOfJsonFilename);
        MenuService menuService = new MenuService(carsService);
        menuService.mainMenu();

    }
}
