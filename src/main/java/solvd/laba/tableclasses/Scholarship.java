package solvd.laba.tableclasses;

public class Scholarship {

    public int scholarshipId;
    public String name;
    public String description;
    public int sponsoredPercentage;

    public Scholarship(int scholarshipId, String name, String description, int sponsoredPercentage){
        this.scholarshipId = scholarshipId;
        this.name = name;
        this.description = description;
        this.sponsoredPercentage = sponsoredPercentage;
    }

    @Override
    public String toString(){
        return "Scholarship: " + name + ", percentage sponsored: " + sponsoredPercentage + "\n"
                + description + "\n";
    }

    //Getters and setters TBI

}
