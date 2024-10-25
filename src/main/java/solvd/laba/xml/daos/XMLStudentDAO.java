package solvd.laba.xml.daos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import solvd.laba.tableclasses.Student;
import solvd.laba.xml.XMLAbstractDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XMLStudentDAO extends XMLAbstractDAO<Student, Integer> {


    public XMLStudentDAO(Document doc){
        super(doc);
    }

    @Override
    public void insert(Student student) {

        int index = findIndex(student.studentId);
        if(index==NOT_FOUND) {
            Element newStudent = createElement(student);
            document.getElementsByTagName("students").item(0).appendChild(newStudent);
        }
    }

    @Override
    public Student read(Integer studentId) {

        Student ret = null;

        int index = findIndex(studentId);
        if(index!=NOT_FOUND){
            Element studentElement = (Element) document.getElementsByTagName("student").item(index);
            String name = studentElement.getElementsByTagName("name").item(0).getTextContent();
            String surname = studentElement.getElementsByTagName("surname").item(0).getTextContent();
            Date dateOfBirth = Date.valueOf(studentElement.getElementsByTagName("dateOfBirth").item(0).getTextContent());
            String phoneNumber = studentElement.getElementsByTagName("phoneNumber").item(0).getTextContent();
            String email = studentElement.getElementsByTagName("email").item(0).getTextContent();
            int careerId = Integer.parseInt(studentElement.getElementsByTagName("careerId").item(0).getTextContent());

            ret = new Student(studentId, name, surname, dateOfBirth, phoneNumber, email, careerId);
        }
        return ret;
    }


    @Override
    public void update(Student student) {

        int index = findIndex(student.studentId);
        if(index!=NOT_FOUND) {
            Element studentElement = (Element) document.getElementsByTagName("student").item(index);
            // Update the values of the existing student element, except the ID.
            studentElement.getElementsByTagName("name").item(0).setTextContent(student.name);
            studentElement.getElementsByTagName("surname").item(0).setTextContent(student.surname);
            studentElement.getElementsByTagName("dateOfBirth").item(0).setTextContent(student.dateOfBirth.toString());
            studentElement.getElementsByTagName("phoneNumber").item(0).setTextContent(student.phoneNumber);
            studentElement.getElementsByTagName("email").item(0).setTextContent(student.email);
            studentElement.getElementsByTagName("careerId").item(0).setTextContent(String.valueOf(student.careerId));
        }
    }

    @Override
    public void delete(Integer studentId) {
        int index = findIndex(studentId);
        if(index!=NOT_FOUND){
            Element studentElement = (Element) document.getElementsByTagName("student").item(index);
            studentElement.getParentNode().removeChild(studentElement);
        }
    }


    @Override
    public Student mapRecord(Node node){
        Student ret = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            int studentId = Integer.parseInt(element.getElementsByTagName("studentId").item(0).getTextContent());
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String surname = element.getElementsByTagName("surname").item(0).getTextContent();
            String dateOfBirthString = element.getElementsByTagName("dateOfBirth").item(0).getTextContent();
            Date dateOfBirth = Date.valueOf(LocalDate.parse(dateOfBirthString, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            String phoneNumber = element.getElementsByTagName("phoneNumber").item(0).getTextContent();
            String email = element.getElementsByTagName("email").item(0).getTextContent();
            int careerId = Integer.parseInt(element.getElementsByTagName("careerId").item(0).getTextContent());

            ret = new Student(studentId, name, surname, dateOfBirth, phoneNumber, email, careerId);
        }

        return ret;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("student");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Student aux = mapRecord(node);
            if(aux!=null) {
                students.add(mapRecord(node));
            }
        }
        return students;
    }

    @Override
    protected int findIndex(Integer studentId){
        int ret = NOT_FOUND;
        NodeList studentNodes = document.getElementsByTagName("student");
        for (int i = 0; i < studentNodes.getLength(); i++) {    // Did not allow foreach
            Element studentElement = (Element) studentNodes.item(i);
            int id = Integer.parseInt(studentElement.getElementsByTagName("studentId").item(0).getTextContent());

            if (id == studentId) {  // If found, remote the element from the document.
                ret = i;
                break; // Exit the loop once the student is found
            }
        }
        return ret;
    }


    @Override
    public Element createElement(Student student){
        Element ret;
        ret = document.createElement("student");

        appendChildToElement("studentId", String.valueOf(student.studentId), ret);
        appendChildToElement("name", student.name, ret);
        appendChildToElement("surname", student.surname, ret);
        appendChildToElement("dateOfBirth", student.dateOfBirth.toString(), ret);
        appendChildToElement("phoneNumber", student.phoneNumber, ret);
        appendChildToElement("email", student.email, ret);
        appendChildToElement("careerId", String.valueOf(student.careerId), ret);
        return ret;
    }


}
