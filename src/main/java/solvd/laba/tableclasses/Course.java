package solvd.laba.tableclasses;

public class Course {


    public int courseId;
    public String name;
    public int credits;
    public int prerequisite;


    public Course(int courseId, String name, int credits){
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
    }

    public Course(int courseId, String name, int credits, int prerequisite){
        this(courseId, name, credits);
        this.prerequisite = prerequisite;

    }

    @Override
    public String toString(){
        return "Course " + courseId + ", \"" + name + "\" (worth " + credits + " credits\n";
    }

    // Getters and setters TBI


}
