package solvd.laba.tableclasses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="careers")
@XmlType(propOrder = {"careerId", "name", "level", "departmentId"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Career {

    @XmlElement(name="careerId")
    public int careerId;
    @XmlElement(name="name")
    public String name;
    @XmlElement(name="level")
    public String level;    // Should validate only specific levels using enums
    @XmlElement(name="departmentId")
    public int departmentId;

    public Career(int careerId, String name, String level, int departmentId){
        this.careerId = careerId;
        this.name = name;
        this.level = level;
        this.departmentId = departmentId;
    }

    //  No-arg constructor for JAXB
    @SuppressWarnings("unused")
    public Career(){}

    @Override
    public String toString(){
        return "Career " + careerId + ", \"" + name + "\"\n"
                + level + ", belonging to department " + careerId + "\n";
    }

    // Getters and Setters TBI

}
