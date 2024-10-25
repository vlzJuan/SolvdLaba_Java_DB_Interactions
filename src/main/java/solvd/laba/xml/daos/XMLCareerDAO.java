package solvd.laba.xml.daos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import solvd.laba.tableclasses.Career;
import solvd.laba.xml.XMLAbstractDAO;

import java.util.ArrayList;
import java.util.List;

public class XMLCareerDAO extends XMLAbstractDAO<Career, Integer> {

    public XMLCareerDAO(Document doc){
        super(doc);
    }

    @Override
    public void insert(Career career) {
        int index = findIndex(career.careerId);
        if (index == NOT_FOUND) {
            Element newCareer = createElement(career);
            document.getElementsByTagName("careers").item(0).appendChild(newCareer);
        }
    }

    @Override
    public Career read(Integer careerId) {
        Career ret = null;
        int index = findIndex(careerId);
        if (index != NOT_FOUND) {
            Element careerElement = (Element) document.getElementsByTagName("career").item(index);
            ret = mapRecord(careerElement);
        }
        return ret;
    }

    @Override
    public void update(Career career) {
        int index = findIndex(career.careerId);
        if (index != NOT_FOUND) {
            Element careerElement = (Element) document.getElementsByTagName("career").item(index);
            careerElement.getElementsByTagName("name").item(0).setTextContent(career.name);
            careerElement.getElementsByTagName("level").item(0).setTextContent(career.level);
            careerElement.getElementsByTagName("departmentId").item(0).setTextContent(String.valueOf(career.departmentId));
        }
    }

    @Override
    public void delete(Integer careerId) {
        int index = findIndex(careerId);
        if (index != NOT_FOUND) {
            Element careerElement = (Element) document.getElementsByTagName("career").item(index);
            careerElement.getParentNode().removeChild(careerElement);
        }
    }

    @Override
    public Career mapRecord(Node node) {
        Career ret = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            int careerId = Integer.parseInt(element.getElementsByTagName("careerId").item(0).getTextContent());
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String level = element.getElementsByTagName("level").item(0).getTextContent();
            int departmentId = Integer.parseInt(element.getElementsByTagName("departmentId").item(0).getTextContent());

            ret = new Career(careerId, name, level, departmentId);
        }
        return ret;
    }

    @Override
    public List<Career> findAll() {
        List<Career> careers = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("career");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Career career = mapRecord(node);
            if (career != null) {
                careers.add(career);
            }
        }
        return careers;
    }

    @Override
    protected Element createElement(Career career) {
        Element ret = document.createElement("career");

        appendChildToElement("careerId", String.valueOf(career.careerId), ret);
        appendChildToElement("name", career.name, ret);
        appendChildToElement("level", career.level, ret);
        appendChildToElement("departmentId", String.valueOf(career.departmentId), ret);

        return ret;
    }

    @Override
    protected int findIndex(Integer careerId) {
        int ret = NOT_FOUND;
        NodeList careerNodes = document.getElementsByTagName("career");

        for (int i = 0; i < careerNodes.getLength(); i++) {
            Element careerElement = (Element) careerNodes.item(i);
            int id = Integer.parseInt(careerElement.getElementsByTagName("careerId").item(0).getTextContent());

            if (id == careerId) {
                ret = i;
                break;
            }
        }
        return ret;
    }
}
