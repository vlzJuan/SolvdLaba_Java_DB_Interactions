package solvd.laba.xml.utilities;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator {


    public static boolean validate(String xmlPath, String xsdPath) {
        boolean ret = false;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new File(xmlPath));

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdPath));

            // Create a Validator object from the xsd schema
            Validator validator = schema.newValidator();

            // Validate the Document using DOMSource
            validator.validate(new DOMSource(document));

            ret = true;
        }
        catch(IOException exc) {
            System.out.println("Error trying to open the xml or xsd files");
        }
        catch (SAXException | ParserConfigurationException exc) {
            System.out.println("XML validation error: " + exc.getMessage());
        }
        return ret;
    }


}
