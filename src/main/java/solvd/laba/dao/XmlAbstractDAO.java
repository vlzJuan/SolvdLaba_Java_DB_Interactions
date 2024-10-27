package solvd.laba.dao;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class XmlAbstractDAO<T, ID> implements CoreDAO<T, ID> {

    protected Document document;
    protected static int NOT_FOUND = -1;

    public XmlAbstractDAO(Document document){
        this.document = document;
    }

    protected abstract Element createElement(T entity);

    protected abstract int findIndex(ID identifier);

    protected abstract T mapRecord(Node node);

    protected void appendChildToElement(String tagName, String textContent, Element parent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    public boolean hasDocument(){
        return this.document!=null;
    }

}
