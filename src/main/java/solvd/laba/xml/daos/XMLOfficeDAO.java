package solvd.laba.xml.daos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import solvd.laba.tableclasses.Office;
import solvd.laba.dao.XmlAbstractDAO;

import java.util.ArrayList;
import java.util.List;

public class XmlOfficeDAO extends XmlAbstractDAO<Office, Integer> {

    public XmlOfficeDAO(Document doc) {
        super(doc);
    }

    @Override
    public void insert(Office office) {
        int index = findIndex(office.officeId);
        if (index == NOT_FOUND) {
            Element newOffice = createElement(office);
            document.getElementsByTagName("offices").item(0).appendChild(newOffice);
        }
    }

    @Override
    public Office read(Integer officeId) {
        Office ret = null;
        int index = findIndex(officeId);
        if (index != NOT_FOUND) {
            Element officeElement = (Element) document.getElementsByTagName("office").item(index);
            ret = mapRecord(officeElement);
        }
        return ret;
    }

    @Override
    public void update(Office office) {
        int index = findIndex(office.officeId);
        if (index != NOT_FOUND) {
            Element officeElement = (Element) document.getElementsByTagName("office").item(index);
            officeElement.getElementsByTagName("building").item(0).setTextContent(office.building);
            officeElement.getElementsByTagName("room").item(0).setTextContent(office.room);
        }
    }

    @Override
    public void delete(Integer officeId) {
        int index = findIndex(officeId);
        if (index != NOT_FOUND) {
            Element officeElement = (Element) document.getElementsByTagName("office").item(index);
            officeElement.getParentNode().removeChild(officeElement);
        }
    }

    @Override
    public Office mapRecord(Node node) {
        Office ret = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            int officeId = Integer.parseInt(element.getElementsByTagName("officeId").item(0).getTextContent());
            String building = element.getElementsByTagName("building").item(0).getTextContent();
            String room = element.getElementsByTagName("room").item(0).getTextContent();

            ret = new Office(officeId, building, room);
        }
        return ret;
    }

    @Override
    public List<Office> findAll() {
        List<Office> offices = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("office");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Office office = mapRecord(node);
            if (office != null) {
                offices.add(office);
            }
        }
        return offices;
    }

    @Override
    protected Element createElement(Office office) {
        Element ret = document.createElement("office");

        appendChildToElement("officeId", String.valueOf(office.officeId), ret);
        appendChildToElement("building", office.building, ret);
        appendChildToElement("room", office.room, ret);

        return ret;
    }

    @Override
    protected int findIndex(Integer officeId) {
        int ret = NOT_FOUND;
        NodeList officeNodes = document.getElementsByTagName("office");

        for (int i = 0; i < officeNodes.getLength(); i++) {
            Element officeElement = (Element) officeNodes.item(i);
            int id = Integer.parseInt(officeElement.getElementsByTagName("officeId").item(0).getTextContent());

            if (id == officeId) {
                ret = i;
                break;
            }
        }
        return ret;
    }
}