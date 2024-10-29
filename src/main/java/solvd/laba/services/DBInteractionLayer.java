package solvd.laba.services;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.dao.SqlAbstractDAO;
import solvd.laba.mysqldaos.*;

import java.io.IOException;
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
                    if(daoInstances.get(daoNum) instanceof SqlAbstractDAO<?,?> dao){
                        ServiceLayer.currentDao(scanner, dao);
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


}
