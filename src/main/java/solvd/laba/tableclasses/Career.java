package solvd.laba.tableclasses;

public class Career {

    public int careerId;
    public String name;
    public String level;    // Should validate only specific levels using enums
    public int departmentId;

    public Career(int careerId, String name, String level, int departmentId){
        this.careerId = careerId;
        this.name = name;
        this.level = level;
        this.departmentId = departmentId;
    }

    @Override
    public String toString(){
        return "Career " + careerId + ", \"" + name + "\"\n"
                + level + ", belonging to department " + careerId + "\n";
    }

    // Getters and Setters TBI

}
