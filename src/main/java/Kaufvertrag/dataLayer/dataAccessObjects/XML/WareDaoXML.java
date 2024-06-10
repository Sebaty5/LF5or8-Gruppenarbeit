package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Ware;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

public class WareDaoXML implements IDao<IWare, Long>
{
    private static final String WARE_XML = "LF5or8-Gruppenarbeit/src/main/java/Kaufvertrag/dataLayer/dataAccessObjects/XML/wareTest.xml"; // Adjust the path as needed


    public static void main(String[] args)
    {
        List<String> besonderheiten = List.of("Ist toll");
        List<String> maengel = List.of("ist aber auch kaputt");

        Ware test = new Ware("test", "test", 20, besonderheiten, maengel);
        test.setId(10);
    }

    @Override
    public IWare create()
    {
        // Hard coded for now. Inject variables with the content of the text boxes later.
        Ware testWare = new Ware("testWare", "Eine tolle testWare", 10, new ArrayList<>(), new ArrayList<>());
        ServiceXML.write(wareToDomElement(testWare), WARE_XML);
        return testWare;
    }

    @Override
    public IWare create(IWare objectToInsert)
    {
        ServiceXML.write(wareToDomElement(objectToInsert), WARE_XML);
        return null;
    }

    @Override
    public IWare read(Long id)
    {
        return null;
    }

    @Override
    public List<IWare> readAll()
    {
        return List.of();
    }

    @Override
    public void update(IWare objectToUpdate)
    {

    }

    @Override
    public void delete(Long id)
    {

    }

    private static Element wareToDomElement(IWare ware)
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            String prefix = "w3s:";
            Element wareElement = doc.createElementNS("https://www.w3schools.com", prefix + "ware");

            Element idElement = doc.createElementNS("https://www.w3schools.com", prefix + "id");
            idElement.appendChild(doc.createTextNode(String.valueOf(ware.getId())));
            wareElement.appendChild(idElement);

            Element bezeichnungElement = doc.createElementNS("https://www.w3schools.com", prefix + "bezeichnung");
            bezeichnungElement.appendChild(doc.createTextNode(ware.getBezeichnung()));
            wareElement.appendChild(bezeichnungElement);

            Element beschreibungElement = doc.createElementNS("https://www.w3schools.com", prefix + "beschreibung");
            beschreibungElement.appendChild(doc.createTextNode(ware.getBeschreibung()));
            wareElement.appendChild(beschreibungElement);

            Element preisElement = doc.createElementNS("https://www.w3schools.com", prefix + "preis");
            preisElement.appendChild(doc.createTextNode(String.valueOf(ware.getPreis())));
            wareElement.appendChild(preisElement);

            for (String besonderheit : ware.getBesonderheiten())
            {
                Element besonderheitElement = doc.createElementNS("https://www.w3schools.com", prefix + "besonderheit");
                besonderheitElement.appendChild(doc.createTextNode(besonderheit));
                wareElement.appendChild(besonderheitElement);
            }

            for (String mangel : ware.getMaengel())
            {
                Element mangelElement = doc.createElementNS("https://www.w3schools.com", prefix + "mangel");
                mangelElement.appendChild(doc.createTextNode(mangel));
                wareElement.appendChild(mangelElement);
            }

            return wareElement;
        }
        catch (ParserConfigurationException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
