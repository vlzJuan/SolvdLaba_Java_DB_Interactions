package solvd.laba.services;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.dao.*;

import java.io.IOException;
import java.util.ArrayList;

public class DBInteractionLayer {

    private static ArrayList<Object> daoInstances;

    public DBInteractionLayer() throws IOException {
        ConnectionPool pool = new ConnectionPool(5);
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


    public static void execute(){
        System.out.println("DB Interaction menu TBI");
    }



}
