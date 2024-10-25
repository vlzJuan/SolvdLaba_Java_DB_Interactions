package solvd.laba.xml.daos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import solvd.laba.tableclasses.Office;
import solvd.laba.xml.XMLAbstractDAO;
import solvd.laba.xml.XMLCoreDAO;

import java.util.List;

public class XMLOfficeDAO extends XMLAbstractDAO<Office, Integer> {


    public XMLOfficeDAO(Document doc){
        super(doc);
    }


    @Override
    public void insert(Office entity) {

    }

    @Override
    public Office read(Integer integer) {
        return null;
    }

    @Override
    public void update(Office entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Office mapRecord(Node node) {
        return null;
    }

    @Override
    public List<Office> findAll() {
        return List.of();
    }

    @Override
    protected Element createElement(Office entity) {
        return null;
    }

    @Override
    protected int findIndex(Integer identifier) {
        return 0;
    }
}
