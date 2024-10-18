package solvd.laba.tableclasses;

public class Office {

    public int officeId;
    public String building;
    public String room;

    public Office(int officeId, String building, String room){
        this.officeId = officeId;
        this.building = building;
        this.room = room;
    }

    @Override
    public String toString(){
        return "Office " + officeId + " (" + building + ", " + room + ")\n";
    }

    //Getters and setters TBI

}
