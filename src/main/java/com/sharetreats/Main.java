package com.sharetreats;

import com.sharetreats.controller.MainController;
import com.sharetreats.factory.Factory;

import java.util.Scanner;

public class Main {
    private static final Factory factory = Factory.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.start();
    }
}