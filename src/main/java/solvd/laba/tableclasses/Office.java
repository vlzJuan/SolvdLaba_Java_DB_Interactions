package solvd.laba.tableclasses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="office")
@XmlType(propOrder = {"officeId", "building", "room"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Office {

    @XmlElement(name="officeId")
    public int officeId;
    @XmlElement(name="building")
    public String building;
    @XmlElement(name="room")
    public String room;

    public Office(int officeId, String building, String room){
        this.officeId = officeId;
        this.building = building;
        this.room = room;
    }

    //  No-arg constructor for use with JAXB
    @SuppressWarnings("unused")
    public Office(){}

    @Override
    public String toString(){
        return "Office " + officeId + " (" + building + ", " + room + ")\n";
    }

    //Getters and setters TBI

}
