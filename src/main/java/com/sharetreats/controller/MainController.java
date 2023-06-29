package com.sharetreats.controller;

import java.util.Scanner;

public class MainController {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("1:부서인원정보, 2:부서구성도, 3:종료");

            switch (scanner.nextLine()) {
                case "1":
                    EmployeeController employeeController = new EmployeeController();
                    employeeController.displayInfo(scanner);

                    break;
                case "2":
                    HierarchyController hierarchyController = new HierarchyController();
                    hierarchyController.display(scanner);

                    break;
                case "3":
                    scanner.close();
                    return;

                default:
                    System.out.println("잘못된 입력입니다");
                    break;
            }
        }
    }

}
