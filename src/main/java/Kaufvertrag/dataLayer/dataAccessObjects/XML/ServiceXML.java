package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Ware;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ServiceXML
{
    private static final String WARE_XML = "LF5or8-Gruppenarbeit/src/main/java/Kaufvertrag/dataLayer/dataAccessObjects/XML/wareTest.xml"; // Adjust the path as needed

    public static void main(String[] args)
    {
        List<String> besonderheiten = List.of("Ist toll");
        List<String> maengel = List.of("ist aber auch kaputt");

        Ware test = new Ware("test", "test", 20, besonderheiten, maengel);
        test.setId(10);
        //write(test);
    }

    public static Document read()
    {
        Document doc = null;
        try
        {
            File xmlFile = new File(WARE_XML);
            if (!xmlFile.exists())
            {
                System.out.println("The file " + WARE_XML + " does not exist.");
                return null;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            //printDocument(doc);
        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            System.out.println(e.getMessage());
        }
        return doc;
    }

    private static void printDocument(Document doc) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult console = new StreamResult(System.out);
        transformer.transform(source, console);
    }

    public static void write(IWare ware)
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            File xmlFile = new File(WARE_XML);

            if (!xmlFile.exists())
            {
                boolean isFileCreated = xmlFile.createNewFile();
                if (!isFileCreated)
                {
                    throw new IOException("Failed to create new file: " + WARE_XML);
                }
            }

            // Create root element
            Element rootElement = doc.createElement("wareList");
            rootElement.setAttribute("xmlns:w3s", "https://www.w3schools.com");
            doc.appendChild(rootElement);

            // Convert Ware object to XML Element
            Element wareElement = createWareElement(doc, ware);
            rootElement.appendChild(wareElement);

            // Write the content into an XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(WARE_XML));

            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        }
        catch (ParserConfigurationException | TransformerException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static Element createWareElement(Document doc, IWare ware)
    {
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
}
