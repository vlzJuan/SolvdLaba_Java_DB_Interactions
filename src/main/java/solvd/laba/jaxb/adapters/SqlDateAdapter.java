package solvd.laba.jaxb.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Date;

public class SqlDateAdapter extends XmlAdapter<String, Date> {

    @Override
    public Date unmarshal(String v) {
        return Date.valueOf(v); // Parses String to java.sql.Date
    }

    @Override
    public String marshal(Date v) {
        return v.toString(); // Converts java.sql.Date to String
    }
}
