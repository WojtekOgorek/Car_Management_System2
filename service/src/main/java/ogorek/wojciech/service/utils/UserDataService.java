package ogorek.wojciech.service.utils;

import lombok.experimental.UtilityClass;
import ogorek.wojciech.persistence.enums.CarBodyType;
import ogorek.wojciech.persistence.enums.EngineType;
import ogorek.wojciech.persistence.exception.AppException;
import ogorek.wojciech.service.enums.PickStatistic;
import ogorek.wojciech.service.enums.SortItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class UserDataService {

    private Scanner scanner = new Scanner(System.in);

    public int getInt(String message) {
        System.out.println(message);

        String value = scanner.nextLine();
        if (!value.matches("\\d+")) {
            throw new AppException("value must be integer");
        }
        return Integer.parseInt(value);
    }


    public boolean getBoolean(String message) {
        System.out.println(message);
        return scanner.nextLine().toLowerCase().equals("y");
    }

    public String getString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public BigDecimal getBigDecimal(String message) {
        System.out.println(message);

        String value = scanner.nextLine();
        if (!value.matches("(\\d+\\.)?\\d+")) {
            throw new AppException("value must be BigDecimal");
        }
        return new BigDecimal(value);
    }

    public SortItem getSortItem() {
        var counter = new AtomicInteger(1);

        Arrays
                .stream(SortItem.values())
                .forEach(sortItem -> System.out.println(counter.getAndIncrement() + ". " + sortItem));
        int option = getInt("Choose sort item");
        if (option < 1 || option > SortItem.values().length) {
            throw new AppException("incorrect sort item number");
        }

        return SortItem.values()[option - 1];
    }

    public CarBodyType getCarBodyType(String message) {
        System.out.println(message);

        var counter = new AtomicInteger(1);
        Arrays
                .stream(CarBodyType.values())
                .forEach(carBodyType -> System.out.println(counter.getAndIncrement() + ". " + carBodyType));
        var option = UserDataService.getInt("Pick car body type");
        if (option < 1 || option > CarBodyType.values().length) {
            throw new AppException("invalid car body option");
        }

        return CarBodyType.values()[option - 1];
    }

    public EngineType getEngineType(String message) {
        System.out.println(message);

        var counter = new AtomicInteger(1);
        Arrays
                .stream(EngineType.values())
                .forEach(engineType -> System.out.println(counter.getAndIncrement() + ". " + engineType));
        var option = UserDataService.getInt("Pick engineType");
        if (option < 1 || option > EngineType.values().length) {
            throw new AppException("invalid engine option");
        }

        return EngineType.values()[option - 1];
    }

    public PickStatistic getStatistic(String message) {
        System.out.println(message);

        var counter = new AtomicInteger(1);
        Arrays
                .stream(PickStatistic.values())
                .forEach(stat -> System.out.println(counter.getAndIncrement() + ". " + stat));
        var option = UserDataService.getInt("Pick stat");
        if (option <= 0 || option > PickStatistic.values().length) {
            throw new AppException("Invalid pick statistic option");
        }
        return PickStatistic.values()[option - 1];
    }

    public List<String> getComponents(String message) {
        System.out.println(message);
        var components = new ArrayList<String>();
        var bool = true;
        while (bool) {
            components
                    .add(UserDataService.getString("Type component"));

            bool = UserDataService.getBoolean("Wanna add more ? y/n");
        }
        return components;
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}
