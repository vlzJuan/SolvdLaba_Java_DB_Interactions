package solvd.laba.jackson.adapters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.sql.Date;

public class SqlDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Date.valueOf(p.getText()); // Parses String to java.sql.Date
    }
}