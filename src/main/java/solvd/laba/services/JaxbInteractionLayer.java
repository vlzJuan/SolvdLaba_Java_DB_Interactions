package solvd.laba.services;

import solvd.laba.dao.JaxbAbstractDAO;
import solvd.laba.jaxb.daos.JaxbStudentDAO;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JaxbInteractionLayer {

    protected final File file;
    private final ArrayList<Object> daoInstances;

    public JaxbInteractionLayer(String filePath) {
        this.file = new File(filePath);
        this.daoInstances = new ArrayList<>();
        this.daoInstances.add(new JaxbStudentDAO(filePath));
        // Add support here for new dao instances, when implemented.
    }
    // Should implement entity-specific daos for these, as I did for the other methods.

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
                    if(daoInstances.get(daoNum) instanceof JaxbAbstractDAO<?,?> dao){
                        ServiceLayer.currentDao(scanner, dao);
                    }
                }
            } catch (InputMismatchException exc) {
                System.out.println("Please enter an integer.");
            }
        }
    }



}
