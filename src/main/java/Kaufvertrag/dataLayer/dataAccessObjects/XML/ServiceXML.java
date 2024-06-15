package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Kaufvertrag.businessInterfaces.IWare;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class ServiceXML
{
    private static final String prefix = "w3s:";

    public static Document read(String path)
    {
        File xmlFile = new File(path);
        try
        {
            Document doc;
            if (!xmlFile.exists())
            {
                doc = setupAndReturnDBuilder().newDocument();
            }
            else
            {
                doc = setupAndReturnDBuilder().parse(xmlFile);
                doc.getDocumentElement().normalize();
            }
            //printDocument(doc);
            return doc;
        }
        catch (SAXException | IOException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void write(Document doc, String path)
    {
        Transformer transformer = createTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        try
        {
            transformer.transform(source, result);
        }
        catch (TransformerException e)
        {
            throw new RuntimeException(e);
        }
    }

    // For testing purposes only
    public static void printDocument(Document doc) throws TransformerException
    {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult console = new StreamResult(System.out);
        transformer.transform(source, console);
    }

    public static DocumentBuilder setupAndReturnDBuilder()
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            return dbFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static Transformer createTransformer()
    {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try
        {
            transformer = transformerFactory.newTransformer();
        }
        catch (TransformerConfigurationException e)
        {
            throw new RuntimeException(e);
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        return transformer;
    }

    public static Element createElementFromWare(Document doc, IWare ware) {
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

        for (String besonderheit : ware.getBesonderheiten()) {
            Element besonderheitElement = doc.createElementNS("https://www.w3schools.com", prefix + "besonderheit");
            besonderheitElement.appendChild(doc.createTextNode(besonderheit));
            wareElement.appendChild(besonderheitElement);
        }

        for (String mangel : ware.getMaengel()) {
            Element mangelElement = doc.createElementNS("https://www.w3schools.com", prefix + "mangel");
            mangelElement.appendChild(doc.createTextNode(mangel));
            wareElement.appendChild(mangelElement);
        }

        return wareElement;
    }

    public static Element createRootElement(Document doc)
    {
        Element rootElement = doc.createElement("rootList");
        rootElement.setAttribute("xmlns:w3s", "https://www.w3schools.com");
        return rootElement;
    }

    public static String getPrefix() { return prefix; }
}
