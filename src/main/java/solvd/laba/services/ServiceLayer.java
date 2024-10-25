package solvd.laba.services;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ServiceLayer {


    public static int startMenuPrompt(Scanner scanner){
        int ret = 0;
        boolean continueMenu = true;
        do {
            System.out.println("Select one of the following options to begin the program:");
            System.out.println("1 - Use the default program to show usage of StudentDAO");
            System.out.println("2 - Execute the DB Interaction menu, for the mySQL database.");
            System.out.println("3 - Execute the XML interaction menu, for data stored as XML in this repo.");
            System.out.println("Exit menu by inputting any other integer.");
            try {
                ret = scanner.nextInt();
                scanner.nextLine();
                continueMenu = false;
            }
            catch (InputMismatchException exc){
                System.out.println("Error: Invalid type of input. Please, put an integer.");
            }
        }while (continueMenu);
        return ret;
    }


    public static void selectMenuOption(Scanner scan, int option){

        switch(option){
            case 1:
                TestStudentDAO.execute();
                break;
            case 2:
                try{
                    DBInteractionLayer menu = new DBInteractionLayer();
                    menu.execute(scan);
                }
                catch (IOException exc){
                    System.out.println("DB menu unable to be instantiated.");
                }
                break;
            case 3:
                if(XMLInteractionLayer.validate(scan)){
                    XMLInteractionLayer.execute(scan);
                }

                break;
            default:
                    System.out.println("No valid interaction option selected, operation aborted.");
        }
    }

}
