package solvd.laba.tableclasses;

public class Course {


    public int courseId;
    public String name;
    public int credits;
    public Integer prerequisite;


    public Course(int courseId, String name, int credits, Integer prerequisite){
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.prerequisite = prerequisite;

    }

    @Override
    public String toString(){
        return "Course " + courseId + ", \"" + name + "\" (worth " + credits + " credits\n";
    }

    // Getters and setters TBI


}
