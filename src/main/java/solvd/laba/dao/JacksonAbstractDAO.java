package solvd.laba.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public abstract class JacksonAbstractDAO<T, ID> implements CoreDAO<T, ID> {
    protected final String filePath;
    protected final ObjectMapper objectMapper;
    protected List<T> entities;

    public JacksonAbstractDAO(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.entities = loadFromFile();
    }

    // Load entities from the JSON file
    protected List<T> loadFromFile() {
        try {
            return objectMapper.readValue(new File(filePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, getEntityClass()));
        } catch (IOException exc) {
            return new ArrayList<>();
        }
    }

    protected void saveToFile() {
        try {
            objectMapper.writeValue(new File(filePath), entities);
        } catch (IOException exc) {
            // Handle error log here
        }
    }

    // Abstract method to get the class of the entity
    protected abstract Class<T> getEntityClass();
}
