package com.sharetreats.controller;

import com.sharetreats.factory.Factory;

import java.util.Scanner;

public class HierarchyController {
    private final Factory factory = Factory.getInstance();

    public void display(Scanner scanner) {
        while (true) {
            System.out.println("1:부서 구성, 2:전체 부서 구성 출력, 3:종료");

            switch (scanner.nextLine()) {
                case "1":
                    System.out.println("구성할 부서 구성을 입력하세요.");
                    System.out.println("ex)* > A, A > B");

                    String[] strings = scanner.nextLine().split(">");

                    String parentName = strings[0];
                    String childName = strings[1];

                    factory.getHierarchyManager().register(parentName, childName);

                    break;
                case "2":
                    System.out.println(factory.getPrinter().printByTopAndAllEmployees());

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
