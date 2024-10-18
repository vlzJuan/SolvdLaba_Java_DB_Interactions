package solvd.laba.tableclasses;

public class Department {

    public int departmentId;
    public String name;
    public int officeId;

    public Department(int departmentId, String name, int officeId){
        this.departmentId = departmentId;
        this.name = name;
        this.officeId = officeId;
    }

    @Override
    public String toString(){
        return "Department " + departmentId + ", \"" + name + ", at office " + officeId + "\n";
    }

    // Getters and setters TBI


}
