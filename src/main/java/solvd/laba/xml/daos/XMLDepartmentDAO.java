package solvd.laba.xml.daos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import solvd.laba.tableclasses.Department;
import solvd.laba.xml.XMLAbstractDAO;
import solvd.laba.xml.XMLCoreDAO;

import java.util.ArrayList;
import java.util.List;

public class XMLDepartmentDAO extends XMLAbstractDAO<Department, Integer> {

    public XMLDepartmentDAO(Document doc) {
        super(doc);
    }

    @Override
    public void insert(Department department) {
        int index = findIndex(department.departmentId);
        if (index == NOT_FOUND) {
            Element newDepartment = createElement(department);
            document.getElementsByTagName("departments").item(0).appendChild(newDepartment);
        }
    }

    @Override
    public Department read(Integer departmentId) {
        Department ret = null;
        int index = findIndex(departmentId);
        if (index != NOT_FOUND) {
            Element departmentElement = (Element) document.getElementsByTagName("department").item(index);
            ret = mapRecord(departmentElement);
        }
        return ret;
    }

    @Override
    public void update(Department department) {
        int index = findIndex(department.departmentId);
        if (index != NOT_FOUND) {
            Element departmentElement = (Element) document.getElementsByTagName("department").item(index);
            departmentElement.getElementsByTagName("name").item(0).setTextContent(department.name);
            departmentElement.getElementsByTagName("officeId").item(0).setTextContent(String.valueOf(department.officeId));
        }
    }

    @Override
    public void delete(Integer departmentId) {
        int index = findIndex(departmentId);
        if (index != NOT_FOUND) {
            Element departmentElement = (Element) document.getElementsByTagName("department").item(index);
            departmentElement.getParentNode().removeChild(departmentElement);
        }
    }

    @Override
    public Department mapRecord(Node node) {
        Department ret = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            int departmentId = Integer.parseInt(element.getElementsByTagName("departmentId").item(0).getTextContent());
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            int officeId = Integer.parseInt(element.getElementsByTagName("officeId").item(0).getTextContent());

            ret = new Department(departmentId, name, officeId);
        }
        return ret;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("department");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Department department = mapRecord(node);
            if (department != null) {
                departments.add(department);
            }
        }
        return departments;
    }

    @Override
    protected Element createElement(Department department) {
        Element ret = document.createElement("department");

        appendChildToElement("departmentId", String.valueOf(department.departmentId), ret);
        appendChildToElement("name", department.name, ret);
        appendChildToElement("officeId", String.valueOf(department.officeId), ret);

        return ret;
    }

    @Override
    protected int findIndex(Integer departmentId) {
        int ret = NOT_FOUND;
        NodeList departmentNodes = document.getElementsByTagName("department");

        for (int i = 0; i < departmentNodes.getLength(); i++) {
            Element departmentElement = (Element) departmentNodes.item(i);
            int id = Integer.parseInt(departmentElement.getElementsByTagName("departmentId").item(0).getTextContent());

            if (id == departmentId) {
                ret = i;
                break;
            }
        }
        return ret;
    }
}
