package solvd.laba;

import solvd.laba.services.ServiceLayer;

import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        ServiceLayer.selectMenuOption(scanner, ServiceLayer.startMenuPrompt(scanner));
        scanner.close();
    }

}
