package solvd.laba.services;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.dao.AbstractDAO;
import solvd.laba.mysqldaos.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DBInteractionLayer {
    private final ArrayList<Object> daoInstances;

    public DBInteractionLayer() throws IOException {
        ConnectionPool pool = new ConnectionPool(5);
        daoInstances = new ArrayList<>();
        daoInstances.add(new CareerDAO(pool));
        daoInstances.add(new ClassroomDAO(pool));
        daoInstances.add(new CourseDAO(pool));
        daoInstances.add(new DepartmentDAO(pool));
        daoInstances.add(new EnrollmentDAO(pool));
        daoInstances.add(new ExamDAO(pool));
        daoInstances.add(new ExamScoreDAO(pool));
        daoInstances.add(new JoinedTableStudentScholarshipDAO(pool));
        daoInstances.add(new OfficeDAO(pool));
        daoInstances.add(new ProfessorDAO(pool));
        daoInstances.add(new ScholarshipDAO(pool));
        daoInstances.add(new StudentDAO(pool));
    }

    public void execute(Scanner scanner) {
        boolean processMenu = true;
        while (processMenu) {
            System.out.println("Select a DAO and method to execute (or '-1' to exit):");

            int index = 0;
            for (Object daoInstance : daoInstances) {
                System.out.println("DAO " + index + ": " + daoInstance.getClass().getSimpleName());
                index++;
            }

            System.out.print("Enter DAO number: ");
            try {
                int daoNum = scanner.nextInt();

                if(daoNum==-1){
                    processMenu = false;
                    System.out.println("DBInteraction menu exited. Have a good day!");
                }
                else if (daoNum < -1 || daoNum >= daoInstances.size()) {
                    System.out.println("Invalid selection. Please try again.");
                }
                else{
                    processMenu = false;
                    if(daoInstances.get(daoNum) instanceof AbstractDAO<?,?> dao){
                        currentDao(scanner, dao);
                    }
                    else{
                        System.out.println("Double-keyed DAO implementation TBI");
                    }
                }
            } catch (InputMismatchException exc) {
                System.out.println("Please enter an integer.");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T, ID> void currentDao(Scanner scan, AbstractDAO<T, ID> daoInstance){

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
                scan.nextLine();
                switch(option){
                    case 1: daoInstance.insert((T) ServiceLayer.createObjectForDAO(scan, daoInstance));
                            break;
                    case 2: System.out.println("Enter the key required to read: ");
                            System.out.println(daoInstance.read((ID)scan.nextLine()).toString());
                            break;
                    case 3:
                            daoInstance.update((T) ServiceLayer.createObjectForDAO(scan, daoInstance));
                            break;
                    case 4:
                            System.out.println("Enter the key required to delete: ");
                            System.out.println(daoInstance.read((ID)scan.nextLine()).toString());
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
                catch (SQLException exc){
                    System.out.println("Error executing the update/query");
                }
            }
        }




}
