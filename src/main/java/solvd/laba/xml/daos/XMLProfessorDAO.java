package solvd.laba.xml.daos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import solvd.laba.tableclasses.Professor;
import solvd.laba.xml.XMLAbstractDAO;

import java.util.List;

public class XMLProfessorDAO extends XMLAbstractDAO<Professor, Integer> {

    public XMLProfessorDAO(Document doc){
        super(doc);
    }


    @Override
    public void insert(Professor entity) {

    }

    @Override
    public Professor read(Integer integer) {
        return null;
    }

    @Override
    public void update(Professor entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Professor mapRecord(Node node) {
        return null;
    }

    @Override
    public List<Professor> findAll() {
        return List.of();
    }

    @Override
    protected Element createElement(Professor entity) {
        return null;
    }

    @Override
    protected int findIndex(Integer identifier) {
        return 0;
    }
}
