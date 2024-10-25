package solvd.laba.services;

import solvd.laba.xml.XMLValidator;

import java.util.Scanner;

public class XMLInteractionLayer {

    private static String xmlPath = "src/main/resources/university.xml";
    private static String xsdPath = "src/main/resources/university.xsd";

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


    public static void execute(){
        System.out.println("TBI");
    }







}
