package solvd.laba.services;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
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


                XMLInteractionLayer menu = new XMLInteractionLayer();

                if(XMLInteractionLayer.validate(scan)){
                        menu.execute(scan);
                    }


                break;
            default:
                    System.out.println("No valid interaction option selected, operation aborted.");
        }
    }




    //Useful functions using reflection


    public static Object createObjectForDAO(Scanner scanner, Object daoInstance) {
        Object ret = null;
        try {
            Class<?> daoClass = daoInstance.getClass(); // Get the actual class from daoInstance param
            Class<?> entityType = (Class<?>) ((java.lang.reflect.ParameterizedType) daoClass
                    .getGenericSuperclass()).getActualTypeArguments()[0]; // Get the explicit argument from T.

            Constructor<?> constructor = entityType.getConstructors()[0]; // The no-weird-stuff-constructor

            Parameter[] params = constructor.getParameters();
            Object[] paramValues = new Object[params.length];

            System.out.println("Creating a new " + entityType.getSimpleName() + " object:");
            for (int i = 0; i < params.length; i++) {
                Class<?> paramType = params[i].getType();
                System.out.print("Enter value for " + params[i].getName() + " (" + paramType.getSimpleName() + "): ");
                String input = scanner.nextLine();
                paramValues[i] = parseInput(input, paramType);  // Helper function for parsing
            }

            // Instantiate the entity using the collected parameter values
            ret = constructor.newInstance(paramValues);

        } catch (Exception e) {
            System.out.println("Error creating the object. Operation aborted.");
        }
        return ret;
    }

    // Helper method to parse the user input based on the type
    public static Object parseInput(String input, Class<?> paramType) {

        if (paramType == int.class || paramType == Integer.class) {
            return Integer.parseInt(input);
        } else if (paramType == float.class || paramType == Float.class) {
            return Float.parseFloat(input);
        } else {
            return input; // For String or other unsupported types
        }
    }








}
