package solvd.laba.services;


import solvd.laba.dao.CoreDAO;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ServiceLayer {


    public static int startMenuPrompt(Scanner scanner){
        int ret = 0;
        boolean continueMenu = true;
        do {
            System.out.println("Select one of the following options to begin the program:");
            System.out.println("1 - Execute the MyBatis interaction menu with the SQL database");
            System.out.println("2 - Execute the DB Interaction menu, for the mySQL database.");
            System.out.println("3 - Execute the XML interaction menu, for data stored as XML in this repo.");
            System.out.println("4 - Execute the JAXB interaction menu, for data stored as XML in this repo.");
            System.out.println("5 - Execute the Jackson interaction menu, for data stored in the 'university.json' file");
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
                try {
                    MyBatisSqlLayer batisMenu = new MyBatisSqlLayer();
                    batisMenu.execute(scan);
                }
                catch (IOException exc){
                    System.out.println("Error: Couldn't open files required for MyBatis Interaction");
                }
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
            case 4:
                JaxbInteractionLayer jaxbMenu = new JaxbInteractionLayer("src/main/resources/university.xml");
                jaxbMenu.execute(scan);
                break;

            case 5:
                JacksonInteractionLayer jacksonMenu = new JacksonInteractionLayer(
                        "src/main/resources/university.json");
                jacksonMenu.execute(scan);
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
            System.out.println("Exception thrown: " + e.toString());
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

    @SuppressWarnings("unchecked")
    public static <T, ID> void currentDao(Scanner scan, CoreDAO<T, ID> daoInstance){

        boolean cont = true;
        while(cont){
            System.out.println("Select a method to use:");
            System.out.println("1 - Insert a row of data");
            System.out.println("2 - Read data by ID");
            System.out.println("3 - Update a row");
            System.out.println("4 - Delete a row by ID");
            System.out.println("5 - Retrieve all data");
            System.out.println("6 - Query data by conditions");
            System.out.println("-1 - exit.");

            try{
                int option = scan.nextInt();
                String aux;
                scan.nextLine();
                switch(option){
                    case 1: daoInstance.insert((T) ServiceLayer.createObjectForDAO(scan, daoInstance));
                        break;
                    case 2: System.out.println("Enter the key required to read: ");
                        aux = scan.nextLine();
                        System.out.println(daoInstance.read((ID)parseInput(aux,Integer.class)));
                        break;
                    case 3:
                        daoInstance.update((T) ServiceLayer.createObjectForDAO(scan, daoInstance));
                        break;
                    case 4:
                        System.out.println("Enter the key required to delete: ");
                        aux = scan.nextLine();
                        daoInstance.delete((ID)parseInput(aux,Integer.class));
                        break;
                    case 5:
                        System.out.println("The complete result set is:");
                        for (T value: daoInstance.findAll()) {
                            System.out.println(value);
                        }
                        break;
                    case 6:
                        System.out.println("TBI");
                        break;
                    case -1:
                        cont = false;
                        break;
                    default:
                        break;
                }
            }
            catch(InputMismatchException exc){
                System.out.println("Error. Please enter an integer as input.");
            }
        }
    }


    //  CURRENTLY NOT WORKING PROPERLY

    public static Object createObjectForClass(Scanner scanner, Class<?> daoClass) {
        Object ret = null;
        try {
            // Retrieve the entity type (T) from the implemented CoreDAO or Mapper interface
            Class<?> entityType = null;
            Type[] interfaces = daoClass.getGenericInterfaces();

            for (Type iface : interfaces) {
                if (iface instanceof ParameterizedType) {
                    ParameterizedType paramType = (ParameterizedType) iface;
                    if (paramType.getRawType().equals(CoreDAO.class)
                            //|| paramType.getRawType().equals(mapperClass)
                    ) {
                        entityType = (Class<?>) paramType.getActualTypeArguments()[0];
                        break;
                    }
                }
            }

            if (entityType == null) {
                throw new IllegalStateException("Could not determine entity type.");
            }

            // Locate the constructor of the entity class (assuming it has parameters)
            Constructor<?> constructor = entityType.getConstructors()[0];
            Parameter[] params = constructor.getParameters();
            Object[] paramValues = new Object[params.length];

            System.out.println("Creating a new " + entityType.getSimpleName() + " object:");

            // Prompt the user for each parameter and gather input values
            for (int i = 0; i < params.length; i++) {
                Class<?> paramType = params[i].getType();
                System.out.print("Enter value for " + params[i].getName() + " (" + paramType.getSimpleName() + "): ");
                String input = scanner.nextLine();
                paramValues[i] = parseInput(input, paramType);  // Use a helper function to parse input correctly
            }

            // Instantiate the entity using the collected parameter values
            ret = constructor.newInstance(paramValues);

        } catch (Exception e) {
            System.out.println("Error creating the object. Operation aborted.");
            System.out.println("Exception thrown: " + e.toString());
        }
        return ret;
    }


    public static <T> T createObjectForEntity(Scanner scanner, T dummyTypeInstance) {
        T ret = null;
        try {
            // Get the class of the T object passed as parameter
            Class<?> entityClass = dummyTypeInstance.getClass();

            // Get the first constructor of the class (primary constructor)
            Constructor<?> constructor = entityClass.getConstructors()[0];

            // Get the parameters of the constructor
            Parameter[] params = constructor.getParameters();
            Object[] paramValues = new Object[params.length];

            System.out.println("Creating a new " + entityClass.getSimpleName() + " object:");

            // Loop through each parameter of the constructor and prompt user for input
            for (int i = 0; i < params.length; i++) {
                Class<?> paramType = params[i].getType();
                System.out.print("Enter value for " + params[i].getName() + " (" + paramType.getSimpleName() + "): ");
                String input = scanner.nextLine();
                paramValues[i] = parseInput(input, paramType); // Helper function for parsing the input
            }

            // Instantiate the entity using the constructor and collected parameters
            ret = (T) constructor.newInstance(paramValues);

        } catch (Exception e) {
            System.out.println("Error creating the object. Operation aborted.");
            System.out.println("Exception thrown: " + e.toString());
        }
        return ret;
    }







}
