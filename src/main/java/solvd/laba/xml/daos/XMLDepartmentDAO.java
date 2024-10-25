package solvd.laba.xml.daos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import solvd.laba.tableclasses.Department;
import solvd.laba.xml.XMLAbstractDAO;
import solvd.laba.xml.XMLCoreDAO;

import java.util.List;

public class XMLDepartmentDAO extends XMLAbstractDAO<Department, Integer> {



    public XMLDepartmentDAO(Document doc){
        super(doc);
    }



    @Override
    public void insert(Department entity) {

    }

    @Override
    public Department read(Integer integer) {
        return null;
    }

    @Override
    public void update(Department entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Department mapRecord(Node node) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }

    @Override
    protected Element createElement(Department entity) {
        return null;
    }

    @Override
    protected int findIndex(Integer identifier) {
        return 0;
    }
}
