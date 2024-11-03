package solvd.laba.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import solvd.laba.wrappers.UniversityWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class JacksonAbstractDAO<T, ID> implements CoreDAO<T, ID> {
    protected final String filePath;
    protected final ObjectMapper objectMapper;
    protected UniversityWrapper universityWrapper; // Wrapper for all entities

    public JacksonAbstractDAO(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.universityWrapper = loadFromFile();
        if (this.universityWrapper == null) {
            this.universityWrapper = new UniversityWrapper(); // Create a new wrapper if none exists
        }
    }

    // Load university data from the JSON file
    protected UniversityWrapper loadFromFile() {
        try {
            return objectMapper.readValue(new File(filePath), UniversityWrapper.class);
        } catch (IOException exc) {
            return new UniversityWrapper(); // Return empty wrapper if unable to read
        }
    }

    protected void saveToFile() {
        try {
            objectMapper.writeValue(new File(filePath), universityWrapper);
        } catch (IOException exc) {
            // Handle error log here
        }
    }

    // Abstract method to get the specific list of entities
    protected abstract List<T> getEntityList();

}
