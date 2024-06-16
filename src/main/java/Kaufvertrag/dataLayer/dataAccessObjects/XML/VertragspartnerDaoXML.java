package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import Kaufvertrag.businessInterfaces.IAdresse;
import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Adresse;
import Kaufvertrag.dataLayer.businessClasses.Vertragspartner;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VertragspartnerDaoXML implements IDao<IVertragspartner, String>
{
    private static final String VERTRAGSPARTNER_XML = "src/main/java/Kaufvertrag/dataLayer/dataAccessObjects/XML/vertragspartnerTest.xml"; // Adjust the path as needed

    /*
    public static void main(String[] args)
    {
        Adresse testAdresse = new Adresse("testStraße", "123a", "2034a", "testOrt");
        Vertragspartner testVertragspartner = new Vertragspartner("Maxi", "Muster");
        testVertragspartner.setAdresse(testAdresse);
        testVertragspartner.setAusweisNr("12fefsq23");
        testVertragspartner.setId(String.valueOf(10L));

        VertragspartnerDaoXML vertragspartnerDaoXML = new VertragspartnerDaoXML();

        vertragspartnerDaoXML.create();
        vertragspartnerDaoXML.create(testVertragspartner);
        vertragspartnerDaoXML.update(testVertragspartner);
        vertragspartnerDaoXML.delete(String.valueOf(10));
        System.out.println(vertragspartnerDaoXML.readAll());
        System.out.println(vertragspartnerDaoXML.read(String.valueOf(10)));
    }
     */

    @Override
    public IVertragspartner create()
    {
        List<IVertragspartner> vertragspartnerList = readAll();

        Adresse testAdresse = new Adresse("testStraße", "123a", "2034a", "testOrt");
        Vertragspartner testVertragspartner = new Vertragspartner("Max", "Muster");
        testVertragspartner.setAdresse(testAdresse);
        testVertragspartner.setAusweisNr("12fefsq23");
        testVertragspartner.setId(String.valueOf(10));

        vertragspartnerList.add(testVertragspartner);
        writeIWareListToXml(vertragspartnerList);
        return testVertragspartner;
    }

    @Override
    public IVertragspartner create(IVertragspartner vertragspartnerToInsert)
    {
        List<IVertragspartner> vertragspartnerList = readAll();
        vertragspartnerList.add(vertragspartnerToInsert);
        writeIWareListToXml(vertragspartnerList);
        return vertragspartnerToInsert;
    }

    @Override
    public IVertragspartner read(String id)
    {
        List<IVertragspartner> vertragspartnerList = readAll();
        for(IVertragspartner vertragspartner : vertragspartnerList)
        {
            if(Objects.equals(vertragspartner.getId(), id))
            {
                return vertragspartner;
            }
        }
        System.out.println("Ware with the id " + id + " not found!");
        return null;
    }

    @Override
    public List<IVertragspartner> readAll()
    {
        Document content = ServiceXML.read(VERTRAGSPARTNER_XML);
        if (content == null)
        {
            System.out.println("File is empty!");
            return List.of();
        }

        List<IVertragspartner> vertragspartnerList = new ArrayList<>();
        NodeList nodeList = content.getElementsByTagName("w3s:vertragspartner");

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element vertragspartnerElement = (Element) node;
                IVertragspartner vertragspartner = domElementToVertragspartner(vertragspartnerElement);
                vertragspartnerList.add(vertragspartner);
            }
        }
        return vertragspartnerList;
    }

    @Override
    public void update(IVertragspartner objectToUpdate)
    {
        List<IVertragspartner> vertragspartnerList = readAll();
        if (vertragspartnerList.removeIf(vertragspartner -> Objects.equals(vertragspartner.getId(), objectToUpdate.getId())))
        {
            vertragspartnerList.add(objectToUpdate);
        }
        writeIWareListToXml(vertragspartnerList);
    }

    @Override
    public void delete(String id)
    {
        List<IVertragspartner> vertragspartnerList = readAll();
        vertragspartnerList.removeIf(vertragspartner -> Objects.equals(vertragspartner.getId(), id));
        writeIWareListToXml(vertragspartnerList);
    }

    private IVertragspartner domElementToVertragspartner(Element vertragspartner)
    {
        long id = Long.parseLong(Objects.requireNonNull(ServiceXML.getChildElementValue(vertragspartner, ServiceXML.getPrefix() + "id")));
        String ausweisNr = ServiceXML.getChildElementValue(vertragspartner, ServiceXML.getPrefix() + "ausweisNr");
        String vorname = ServiceXML.getChildElementValue(vertragspartner, ServiceXML.getPrefix() + "vorname");
        String nachname = ServiceXML.getChildElementValue(vertragspartner, ServiceXML.getPrefix() + "nachname");

        Element adresseElement = (Element) vertragspartner.getElementsByTagName(ServiceXML.getPrefix() + "adresse").item(0);

        String strasse = ServiceXML.getChildElementValue(adresseElement, ServiceXML.getPrefix() + "strasse");
        String hausNr = ServiceXML.getChildElementValue(adresseElement, ServiceXML.getPrefix() + "hausNr");
        String plz = ServiceXML.getChildElementValue(adresseElement, ServiceXML.getPrefix() + "plz");
        String ort = ServiceXML.getChildElementValue(adresseElement, ServiceXML.getPrefix() + "ort");

        IAdresse adresse = new Adresse(strasse, hausNr, plz, ort);

        IVertragspartner newVertragspartner = new Vertragspartner(vorname, nachname);
        newVertragspartner.setAusweisNr(ausweisNr);
        newVertragspartner.setAdresse(adresse);
        newVertragspartner.setId(String.valueOf(id));

        return newVertragspartner;
    }

    private void writeIWareListToXml(List<IVertragspartner> listToWrite)
    {
        Document doc = ServiceXML.setupAndReturnDBuilder().newDocument();
        Element rootElement = ServiceXML.createRootElement(doc);
        doc.appendChild(rootElement);
        for (IVertragspartner vertragspartner : listToWrite)
        {
            Element vertragspartnerElement = createElementFromVertragspartner(doc, vertragspartner);
            rootElement.appendChild(vertragspartnerElement);
        }
        ServiceXML.write(doc, VERTRAGSPARTNER_XML);
    }

    private static Element createElementFromVertragspartner(Document doc, IVertragspartner vertragspartner)
    {
        Element vertragspartnerElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "vertragspartner");

        Element idElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "id");
        idElement.appendChild(doc.createTextNode(String.valueOf(vertragspartner.getId())));
        vertragspartnerElement.appendChild(idElement);

        Element ausweisNrElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "ausweisNr");
        ausweisNrElement.appendChild(doc.createTextNode(String.valueOf(vertragspartner.getAusweisNr())));
        vertragspartnerElement.appendChild(ausweisNrElement);

        Element vornameElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "vormame");
        vornameElement.appendChild(doc.createTextNode(vertragspartner.getVorname()));
        vertragspartnerElement.appendChild(vornameElement);

        Element nachnameElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "nachname");
        nachnameElement.appendChild(doc.createTextNode(vertragspartner.getNachname()));
        vertragspartnerElement.appendChild(nachnameElement);

        Element adresseElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "adresse");

        Element strasseElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "strasse");
        strasseElement.appendChild(doc.createTextNode(vertragspartner.getAdresse().getStrasse()));
        adresseElement.appendChild(strasseElement);

        Element hausNrElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "hausNr");
        hausNrElement.appendChild(doc.createTextNode(vertragspartner.getAdresse().getHausNr()));
        adresseElement.appendChild(hausNrElement);

        Element plzElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "plz");
        plzElement.appendChild(doc.createTextNode(vertragspartner.getAdresse().getPlz()));
        adresseElement.appendChild(plzElement);

        Element ortElement = doc.createElementNS("https://www.w3schools.com", ServiceXML.getPrefix() + "ort");
        ortElement.appendChild(doc.createTextNode(vertragspartner.getAdresse().getOrt()));
        adresseElement.appendChild(ortElement);

        vertragspartnerElement.appendChild(adresseElement);

        return vertragspartnerElement;
    }

}
