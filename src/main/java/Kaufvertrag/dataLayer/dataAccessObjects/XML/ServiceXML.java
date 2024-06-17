package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

    public static Element createRootElement(Document doc)
    {
        Element rootElement = doc.createElement("rootList");
        rootElement.setAttribute("xmlns:w3s", "https://www.w3schools.com");
        return rootElement;
    }

    public static String getChildElementValue(Element parent, String tagName)
    {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    // For testing purposes only
    public static void printDocument(Document doc) throws TransformerException
    {
        Transformer transformer = createTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult console = new StreamResult(System.out);
        transformer.transform(source, console);
    }

    public static String getPrefix() { return prefix; }
}
