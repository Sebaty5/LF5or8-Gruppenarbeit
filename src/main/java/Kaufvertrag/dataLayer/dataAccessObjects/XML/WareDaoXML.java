package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Ware;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WareDaoXML implements IDao<IWare, Long>
{
    private static final String WARE_XML = "./XML/wareTest.xml"; // Adjust the path as needed

    @Override
    public IWare create()
    {
        List<IWare> wareList = readAll();
        IWare testWare = new Ware("testWare", "Eine tolle testWare", 10, new ArrayList<>(), new ArrayList<>());
        testWare.setId(getValidId(wareList));
        wareList.add(testWare);
        writeIWareListToXml(wareList);
        return testWare;
    }

    @Override
    public IWare create(IWare objectToInsert)
    {
        List<IWare> wareList = readAll();
        objectToInsert.setId(getValidId(wareList));
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
            System.out.println("File is empty!");
            return List.of();
        }

        List<IWare> wareList = new ArrayList<>();
        NodeList nodeList = content.getElementsByTagName("w3s:ware");

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
        wareList.removeIf(ware -> ware.getId() == objectToUpdate.getId());
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

    private IWare domElementToWare(Element ware)
    {
        long id = Long.parseLong(Objects.requireNonNull(ServiceXML.getChildElementValue(ware, ServiceXML.getPrefix() + "id")));
        String bezeichnung = ServiceXML.getChildElementValue(ware, ServiceXML.getPrefix() + "bezeichnung");
        String beschreibung = ServiceXML.getChildElementValue(ware, ServiceXML.getPrefix() + "beschreibung");
        double preis = Double.parseDouble(Objects.requireNonNull(ServiceXML.getChildElementValue(ware, ServiceXML.getPrefix() +"preis")));

        List<String> besonderheiten = new ArrayList<>();
        NodeList besonderheitNodes = ware.getElementsByTagName(ServiceXML.getPrefix() + "besonderheit");
        for (int i = 0; i < besonderheitNodes.getLength(); i++)
        {
            besonderheiten.add(besonderheitNodes.item(i).getTextContent());
        }

        List<String> maengel = new ArrayList<>();
        NodeList mangelNodes = ware.getElementsByTagName(ServiceXML.getPrefix() + "mangel");
        for (int i = 0; i < mangelNodes.getLength(); i++)
        {
            maengel.add(mangelNodes.item(i).getTextContent());
        }

        Ware newWare = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);
        newWare.setId(id);

        return newWare;
    }

    private void writeIWareListToXml(List<IWare> listToWrite)
    {
        Document doc = ServiceXML.setupAndReturnDBuilder().newDocument();
        Element rootElement = ServiceXML.createRootElement(doc);
        doc.appendChild(rootElement);
        for (IWare ware : listToWrite)
        {
            Element wareElement = createElementFromWare(doc, ware);
            rootElement.appendChild(wareElement);
        }
        ServiceXML.write(doc, WARE_XML);
    }

    private static Element createElementFromWare(Document doc, IWare ware)
    {
        Element wareElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "ware");

        Element idElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "id");
        idElement.appendChild(doc.createTextNode(String.valueOf(ware.getId())));
        wareElement.appendChild(idElement);

        Element bezeichnungElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "bezeichnung");
        bezeichnungElement.appendChild(doc.createTextNode(ware.getBezeichnung()));
        wareElement.appendChild(bezeichnungElement);

        Element beschreibungElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "beschreibung");
        beschreibungElement.appendChild(doc.createTextNode(ware.getBeschreibung()));
        wareElement.appendChild(beschreibungElement);

        Element preisElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "preis");
        preisElement.appendChild(doc.createTextNode(String.valueOf(ware.getPreis())));
        wareElement.appendChild(preisElement);

        for (String besonderheit : ware.getBesonderheiten()) {
            Element besonderheitElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "besonderheit");
            besonderheitElement.appendChild(doc.createTextNode(besonderheit));
            wareElement.appendChild(besonderheitElement);
        }

        for (String mangel : ware.getMaengel()) {
            Element mangelElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "mangel");
            mangelElement.appendChild(doc.createTextNode(mangel));
            wareElement.appendChild(mangelElement);
        }

        return wareElement;
    }

    private long getValidId(List<IWare> vertragspartnerList)
    {
        long highestId = 0;
        for(IWare ware : vertragspartnerList)
        {
            long currentId = ware.getId();
            if (currentId > highestId)
            {
                highestId = currentId;
            }
        }
        return highestId + 1;
    }
}
