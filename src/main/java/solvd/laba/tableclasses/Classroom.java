package solvd.laba.tableclasses;

public class Classroom {

    public int classroomId;
    public String building;
    public String room;

    public Classroom(int classroomId, String building, String room){
        this.classroomId = classroomId;
        this.building = building;
        this.room = room;
    }


    @Override
    public String toString(){
        return "Classroom " + classroomId + " (" + building + ", " + room + ")\n";
    }

    // Getters and setters TBI

}
