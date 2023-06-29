package com.sharetreats.controller;

import com.sharetreats.factory.Factory;

import java.util.Scanner;

public class EmployeeController {
    Factory factory = Factory.getInstance();

    public void displayInfo(Scanner scanner) {
        while (true) {
            System.out.println("1:부서 추가, 2:전체 부서 출력, 3:종료");

            switch (scanner.nextLine()) {
                case "1":
                    System.out.println("추가할 회사 정보를 입력하세요.");
                    System.out.println("ex)PARENT,100");

                    String[] strings = scanner.nextLine().split(",");

                    String name = strings[0];
                    int numberOfEmployees = Integer.parseInt(strings[1]);

                    factory.getCreator().crate(name, numberOfEmployees);

                    break;
                case "2":
                    System.out.println(factory.getPrinter().printByAll());

                    break;
                case "3":
                    return;

                default:
                    System.out.println("잘못된 입력입니다");
                    break;
            }
        }
    }
}
