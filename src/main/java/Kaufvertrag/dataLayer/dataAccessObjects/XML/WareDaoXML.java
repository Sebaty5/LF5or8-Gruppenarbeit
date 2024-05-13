package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Ware;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class WareDaoXML implements IDao<IWare, Long> {
    @Override
    public IWare create() {
        // Hard coded for now. Inject variables with the content of the text boxes later.
        Ware testWare = new Ware("testWare", "Eine tolle testWare", 10, new ArrayList<>(), new ArrayList<>());
        ServiceXML.write(testWare);
        return testWare;
    }

    @Override
    public void create(IWare objectToInsert) {
        ServiceXML.write(objectToInsert);
    }

    @Override
    public IWare read(Long id) {
        return ServiceXML.read(id);
    }

    @Override
    public List<IWare> readAll() {
        return List.of();
    }

    @Override
    public void update(IWare objectToUpdate) {

    }

    @Override
    public void delete(Long id) {

    }
}
