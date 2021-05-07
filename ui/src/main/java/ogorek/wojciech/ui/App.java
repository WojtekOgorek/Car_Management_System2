package ogorek.wojciech.ui;

import ogorek.wojciech.service.CarsService;

import java.util.Set;

public class App {
    public static void main(String[] args) {
        final Set<String> collectionOfJsonFilename = Set.of(
                "<filepath_to_resources>cars.json");

        CarsService carsService = new CarsService(collectionOfJsonFilename);
        MenuService menuService = new MenuService(carsService);
        menuService.mainMenu();

    }
}
