# Car Management System 2

Car management system 2 is a simple multi-module java application to show how you can use java streams. Part two
differs from its predecessor because it has been made strictly in DDD convention. The persistence/domain module is fully
encapsulated from rest of the application.

## Installation

Use maven -> [link](https://maven.apache.org/download.cgi) <- to install car management system 2. You need to add file paths to json paths to the filename variables.

```bash
#main folder
mvn clean install
#go to ui folder 
cd ui
#go to target folder
cd target
#start app
java -jar --enable-preview ui.jar
```

## Usage

```java
/*
 *
 *    ----------  APP MENU ----------
 *
 */

public class MenuService {

    private final CarsService carsService;

    public void mainMenu() {
        while (true) {
            try {
                int option = chooseOptionFromMainMenu();
                switch (option) {
                    case 1 -> option1();
                    case 2 -> option2();
                    case 3 -> option3();
                    case 4 -> option4();
                    case 5 -> option5();
                    case 6 -> option6();
                    case 7 -> option7();
                    case 8 -> option8();
                    case 9 -> {
                        UserDataService.close();
                        System.out.println("Have a nice day !");
                        return;
                    }
                    default -> System.out.println("\nWrong option number\n");
                }
            } catch (AppException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int chooseOptionFromMainMenu() {
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




```
