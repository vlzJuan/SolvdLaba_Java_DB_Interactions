package solvd.laba.xml.daos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import solvd.laba.tableclasses.Professor;
import solvd.laba.xml.XMLAbstractDAO;

import java.util.ArrayList;
import java.util.List;

public class XMLProfessorDAO extends XMLAbstractDAO<Professor, Integer> {

    public XMLProfessorDAO(Document doc) {
        super(doc);
    }

    @Override
    public void insert(Professor professor) {
        int index = findIndex(professor.professorId);
        if (index == NOT_FOUND) {
            Element newProfessor = createElement(professor);
            document.getElementsByTagName("professors").item(0).appendChild(newProfessor);
        }
    }

    @Override
    public Professor read(Integer professorId) {
        Professor ret = null;

        int index = findIndex(professorId);
        if (index != NOT_FOUND) {
            Element professorElement = (Element) document.getElementsByTagName("professor").item(index);
            String name = professorElement.getElementsByTagName("name").item(0).getTextContent();
            String surname = professorElement.getElementsByTagName("surname").item(0).getTextContent();
            String email = professorElement.getElementsByTagName("email").item(0).getTextContent();
            int departmentId = Integer.parseInt(professorElement.getElementsByTagName("departmentId").item(0).getTextContent());

            ret = new Professor(professorId, name, surname, email, departmentId);
        }
        return ret;
    }

    @Override
    public void update(Professor professor) {
        int index = findIndex(professor.professorId);
        if (index != NOT_FOUND) {
            Element professorElement = (Element) document.getElementsByTagName("professor").item(index);
            // Update the values of the existing professor element, except the ID.
            professorElement.getElementsByTagName("name").item(0).setTextContent(professor.name);
            professorElement.getElementsByTagName("surname").item(0).setTextContent(professor.surname);
            professorElement.getElementsByTagName("email").item(0).setTextContent(professor.email);
            professorElement.getElementsByTagName("departmentId").item(0).setTextContent(String.valueOf(professor.departmentId));
        }
    }

    @Override
    public void delete(Integer professorId) {
        int index = findIndex(professorId);
        if (index != NOT_FOUND) {
            Element professorElement = (Element) document.getElementsByTagName("professor").item(index);
            professorElement.getParentNode().removeChild(professorElement);
        }
    }

    @Override
    public Professor mapRecord(Node node) {
        Professor ret = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            int professorId = Integer.parseInt(element.getElementsByTagName("professorId").item(0).getTextContent());
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String surname = element.getElementsByTagName("surname").item(0).getTextContent();
            String email = element.getElementsByTagName("email").item(0).getTextContent();
            int departmentId = Integer.parseInt(element.getElementsByTagName("departmentId").item(0).getTextContent());

            ret = new Professor(professorId, name, surname, email, departmentId);
        }
        return ret;
    }

    @Override
    public List<Professor> findAll() {
        List<Professor> professors = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("professor");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Professor aux = mapRecord(node);
            if (aux != null) {
                professors.add(aux);
            }
        }
        return professors;
    }

    @Override
    protected Element createElement(Professor professor) {
        Element ret = document.createElement("professor");

        appendChildToElement("professorId", String.valueOf(professor.professorId), ret);
        appendChildToElement("name", professor.name, ret);
        appendChildToElement("surname", professor.surname, ret);
        appendChildToElement("email", professor.email, ret);
        appendChildToElement("departmentId", String.valueOf(professor.departmentId), ret);
        return ret;
    }

    @Override
    protected int findIndex(Integer professorId) {
        int ret = NOT_FOUND;
        NodeList professorNodes = document.getElementsByTagName("professor");
        for (int i = 0; i < professorNodes.getLength(); i++) {
            Element professorElement = (Element) professorNodes.item(i);
            int id = Integer.parseInt(professorElement.getElementsByTagName("professorId").item(0).getTextContent());

            if (id == professorId) {
                ret = i;
                break; // Exit the loop once the professor is found
            }
        }
        return ret;
    }
}
