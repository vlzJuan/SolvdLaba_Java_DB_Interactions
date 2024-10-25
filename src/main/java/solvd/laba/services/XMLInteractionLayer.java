package solvd.laba.services;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import solvd.laba.xml.XMLValidator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class XMLInteractionLayer {

    private static String xmlPath = "src/main/resources/university.xml";
    private static String xsdPath = "src/main/resources/university.xsd";
    private static Document document;

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

    public static void execute(Scanner scan){
        if(loadDocument()){
            xmlMainMenu(scan);
        }

    }

    private static boolean loadDocument() {
        boolean ret = false;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(xmlPath));
            document.getDocumentElement().normalize();
            System.out.println("XML document loaded successfully.");
            ret = true;
        }
        catch(IOException | ParserConfigurationException | SAXException exc){
            System.out.println("Loading of document failed. Aborting menu.");
        }
        return ret;
    }


    private static void xmlMainMenu(Scanner scan){

        boolean menuLoop = true;
        int option;
        while(menuLoop){
            System.out.println("Select an option:");
            System.out.println("1 - Add an entry");
            System.out.println("2 - Modify an entry");
            System.out.println("3 - Remove an entry");
            System.out.println("-1 - Exit the program.");

            try{
                option = scan.nextInt();
                scan.nextLine();    // Flush the \n

                switch(option){
                    case 1:
                        System.out.println("Add entry menu, TBI");
                        break;
                    case 2:
                        System.out.println("Modify entry menu, TBI");
                        break;
                    case 3:
                        System.out.println("Remove entry menu, TBI");
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



}
