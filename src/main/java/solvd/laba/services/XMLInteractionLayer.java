package solvd.laba.services;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import solvd.laba.dao.XmlAbstractDAO;
import solvd.laba.xml.daos.*;
import solvd.laba.xml.utilities.XMLValidator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static solvd.laba.services.ServiceLayer.parseInput;

public class XMLInteractionLayer {

    private static String xmlPath = "src/main/resources/university.xml";
    private static String xsdPath = "src/main/resources/university.xsd";
    private static final String xmlUpdatedPath = "src/main/resources/university_update.xml";
    private Document document;
    private static ArrayList<Object> daoInstances;


    public XMLInteractionLayer(){
        loadDocument();
        daoInstances = new ArrayList<>();
        daoInstances.add(new XmlStudentDAO(document));
        daoInstances.add(new XmlProfessorDAO(document));
        daoInstances.add(new XmlOfficeDAO(document));
        daoInstances.add(new XmlDepartmentDAO(document));
        daoInstances.add(new XmlCareerDAO(document));
    }



    public static boolean validate(Scanner scan){

        String aux;
        System.out.println("Write the xml path (leave blank for default path: " + xmlPath);
        aux = scan.nextLine();
        if(!aux.isEmpty()){
            xmlPath = aux;
        }
        System.out.println("Write the xsd path (leave blank for default path: " + xsdPath);
        aux = scan.nextLine();
        if(!aux.isEmpty()){
            xsdPath = aux;
        }
        return XMLValidator.validate(xmlPath, xsdPath);
    }

    public void execute(Scanner scan){
        if(hasDocument()){
            xmlMainMenu(scan);
        }
        saveDocument();
        //Here, save the document.
    }

    private void loadDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.parse(new File(xmlPath));
            this.document.getDocumentElement().normalize();
            System.out.println("XML document loaded successfully.");
        }
        catch(IOException | ParserConfigurationException | SAXException exc){
            System.out.println("Loading of document failed. Aborting menu.");
        }
    }

    private void saveDocument() {
        try {

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlUpdatedPath));

            transformer.transform(source, result);

            System.out.println("Document saved successfully to " + xmlUpdatedPath);
        } catch (Exception e) {
            System.out.println("Error saving the document: " + e.getMessage());
        }
    }

    private void xmlMainMenu(Scanner scan){

        boolean processMenu = true;
        while (processMenu) {
            System.out.println("Select a DAO and method to execute (or '-1' to exit):");

            int index = 0;
            for (Object daoInstance : daoInstances) {
                System.out.println("DAO " + index + ": " + daoInstance.getClass().getSimpleName());
                index++;
            }

            System.out.print("Enter DAO number: ");
            try {
                int daoNum = scan.nextInt();

                if(daoNum==-1){
                    processMenu = false;
                    System.out.println("XML menu exited. Have a good day!");
                }
                else if (daoNum < -1 || daoNum >= daoInstances.size()) {
                    System.out.println("Invalid selection. Please try again.");
                }
                else{
                    processMenu = false;
                    if(daoInstances.get(daoNum) instanceof XmlAbstractDAO<?,?> dao){
                        this.xmlOperationMenu(scan, dao);
                    }
                    else{
                        System.out.println("Double-keyed DAO implementation TBI");
                    }
                }
            } catch (InputMismatchException exc) {
                System.out.println("Please enter an integer.");
            }
        }

    }


    @SuppressWarnings("unchecked")
    private <T, ID> void xmlOperationMenu(Scanner scan, XmlAbstractDAO<T,ID> daoInstance){
        boolean menuLoop = true;
        int option;
        while(menuLoop){
            System.out.println("Select an option:");
            System.out.println("1 - Add an entry");
            System.out.println("2 - Read an entry");
            System.out.println("3 - Modify an entry");
            System.out.println("4 - Delete an entry");
            System.out.println("5 - View all entries");
            System.out.println("-1 - Exit the program.");

            try{
                option = scan.nextInt();
                scan.nextLine();    // Flush the \n
                String aux;
                switch(option){
                    case 1:
                        daoInstance.insert((T) ServiceLayer.createObjectForDAO(scan, daoInstance));
                        break;
                    case 2:
                        System.out.println("Enter the key required to read: ");
                        aux = scan.nextLine();
                        System.out.println(daoInstance.read((ID)parseInput(aux,Integer.class)));
                        break;
                    case 3:
                        daoInstance.update((T) ServiceLayer.createObjectForDAO(scan, daoInstance));
                        break;
                    case 4:
                        System.out.println("Enter the key required to delete: ");
                        aux = scan.nextLine();
                        daoInstance.delete((ID)parseInput(aux,Integer.class));
                        break;
                    case 5:
                        System.out.println("The complete result set is:");
                        for (T value: daoInstance.findAll()) {
                            System.out.println(value);
                        }
                        break;
                    case -1:
                        menuLoop = false;
                        break;
                    default:
                        System.out.println("Incorrect option. Please select another one.");
                        break;
                }
            }
            catch(InputMismatchException exc){
                System.out.println("Error. Please input an integer.");
            }

        }
    }

    public boolean hasDocument(){
        return this.document!=null;
    }

}
