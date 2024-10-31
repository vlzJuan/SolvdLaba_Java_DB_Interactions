package solvd.laba.dao;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import solvd.laba.jaxb.wrappers.UniversityWrapper;

import java.io.File;

public abstract class JaxbAbstractDAO<T, ID> implements CoreDAO<T, ID>{

    protected final File file;
    protected final UniversityWrapper universityWrapper;

    public JaxbAbstractDAO(String filePath) {
        this.file = new File(filePath);
        this.universityWrapper = loadFromFile();
        // On child classes, handle their specific wrappers within the if, like so:
        // if (this.universityWrapper.getStudents() == null) {
        //    this.universityWrapper.setStudents(new ArrayList<>());
        //}
    }


    protected UniversityWrapper loadFromFile() {
        UniversityWrapper ret = new UniversityWrapper();

        if (file.exists()) {
            try {
                JAXBContext context = JAXBContext.newInstance(UniversityWrapper.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ret = (UniversityWrapper) unmarshaller.unmarshal(file);
            } catch (JAXBException e) {
                // Handle error and logs properly here
            }
        }
        return ret;
    }

    protected void saveToFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(UniversityWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(universityWrapper, file);
        } catch (JAXBException exc) {
            // Handle proper exception logging here
        }
    }

}
