package ogorek.wojciech.service.utils;

import lombok.RequiredArgsConstructor;
import ogorek.wojciech.persistence.exception.AppException;

import java.util.Scanner;

@RequiredArgsConstructor
public class UserDataService {

    private Scanner scanner = new Scanner(System.in);

    private String getString(String message){
        System.out.println(message);
        return scanner.nextLine();
    }

    private int getInt(String message){
        System.out.println(message);

        String value = scanner.nextLine();
        if(!value.matches("\\d+")){
            throw new AppException("value must be integer");
        }

        return Integer.parseInt(value);
    }

    public boolean getBoolean(String message){
        System.out.println(message);
        return scanner.nextLine().toLowerCase().equals("y");
    }
}
