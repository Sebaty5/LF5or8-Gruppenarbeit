package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Ware;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WareDaoXML implements IDao<IWare, Long>
{
    private static final String WARE_XML = "LF5or8-Gruppenarbeit/src/main/java/Kaufvertrag/dataLayer/dataAccessObjects/XML/wareTest.xml"; // Adjust the path as needed

    public static void main(String[] args)
    {
        List<String> besonderheiten = List.of("Ist toll");
        List<String> maengel = List.of("ist aber auch kaputt");

        Ware test = new Ware("test", "test", 20, besonderheiten, maengel);
        test.setId(10);
        WareDaoXML wareDaoXML = new WareDaoXML();
        wareDaoXML.create(test);
    }

    @Override
    public IWare create()
    {
        List<IWare> wareList = readAll();
        // Hard coded default object
        IWare testWare = new Ware("testWare", "Eine tolle testWare", 10, new ArrayList<>(), new ArrayList<>());
        wareList.add(testWare);
        writeIWareListToXml(wareList);
        return testWare;
    }

    @Override
    public IWare create(IWare objectToInsert)
    {
        List<IWare> wareList = readAll();
        wareList.add(objectToInsert);
        writeIWareListToXml(wareList);
        return objectToInsert;
    }

    @Override
    public IWare read(Long id)
    {
        List<IWare> wareList = readAll();

        for(IWare ware : wareList)
        {
           if(ware.getId() == id)
           {
               return ware;
           }
        }
        System.out.println("Ware with the id " + id + " not found!");
        return null;
    }

    @Override
    public List<IWare> readAll()
    {
        Document content = ServiceXML.read(WARE_XML);

        if (content == null)
        {
            return List.of(); // Return an empty list if content is null
        }

        List<IWare> wareList = new ArrayList<>();
        NodeList nodeList = content.getElementsByTagName("ware");

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element wareElement = (Element) node;
                IWare ware = domElementToWare(wareElement);
                wareList.add(ware);
            }
        }

        return wareList;
    }

    @Override
    public void update(IWare objectToUpdate)
    {
        List<IWare> wareList = readAll();
        wareList.remove((int)objectToUpdate.getId());
        wareList.add(objectToUpdate);
        writeIWareListToXml(wareList);
    }

    @Override
    public void delete(Long id)
    {
        List<IWare> wareList = readAll();
        wareList.removeIf(ware -> ware.getId() == id);
        writeIWareListToXml(wareList);
    }

    private Element wareToDomElement(IWare ware)
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

    private IWare domElementToWare(Element ware)
    {
        long id = Long.parseLong(Objects.requireNonNull(getChildElementValue(ware, "id")));
        String bezeichnung = getChildElementValue(ware, "bezeichnung");
        String beschreibung = getChildElementValue(ware, "beschreibung");
        double preis = Double.parseDouble(Objects.requireNonNull(getChildElementValue(ware, "preis")));

        List<String> besonderheiten = new ArrayList<>();
        NodeList besonderheitNodes = ware.getElementsByTagName("besonderheit");
        for (int i = 0; i < besonderheitNodes.getLength(); i++)
        {
            besonderheiten.add(besonderheitNodes.item(i).getTextContent());
        }

        List<String> maengel = new ArrayList<>();
        NodeList mangelNodes = ware.getElementsByTagName("mangel");
        for (int i = 0; i < mangelNodes.getLength(); i++)
        {
            maengel.add(mangelNodes.item(i).getTextContent());
        }

        Ware newWare = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);
        newWare.setId(id);

        return newWare;
    }

    private String getChildElementValue(Element parent, String tagName)
    {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    private Element createRootElement()
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("rootList");
            rootElement.setAttribute("xmlns:w3s", "https://www.w3schools.com");

            return rootElement;
        }
        catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void writeIWareListToXml(List<IWare> listToWrite)
    {
        Element rootElement = createRootElement();
        for(IWare ware : listToWrite)
        {
            rootElement.appendChild(wareToDomElement(ware));
        }
        ServiceXML.write(rootElement, WARE_XML);
    }
}
