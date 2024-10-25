package solvd.laba.xml.daos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import solvd.laba.tableclasses.Career;
import solvd.laba.xml.XMLAbstractDAO;

import java.util.List;

public class XMLCareerDAO extends XMLAbstractDAO<Career, Integer> {

    public XMLCareerDAO(Document doc){
        super(doc);
    }



    @Override
    public void insert(Career entity) {

    }

    @Override
    public Career read(Integer integer) {
        return null;
    }

    @Override
    public void update(Career entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Career mapRecord(Node node) {
        return null;
    }

    @Override
    public List<Career> findAll() {
        return List.of();
    }


    @Override
    protected Element createElement(Career entity) {
        return null;
    }

    @Override
    protected int findIndex(Integer identifier) {
        return 0;
    }


}
