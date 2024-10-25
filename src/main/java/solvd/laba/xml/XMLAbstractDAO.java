package solvd.laba.xml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class XMLAbstractDAO<T, ID> implements XMLCoreDAO<T, ID>{

    protected Document document;
    protected static int NOT_FOUND = -1;

    public XMLAbstractDAO(Document document){
        this.document = document;
    }

    protected abstract Element createElement(T entity);

    protected abstract int findIndex(ID identifier);

    protected void appendChildToElement(String tagName, String textContent, Element parent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

}
