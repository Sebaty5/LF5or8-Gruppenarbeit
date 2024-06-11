package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class ServiceXML
{
    public static Document read(String path)
    {
        Document doc = null;
        try
        {
            File xmlFile = new File(path);
            if (!xmlFile.exists())
            {
                System.out.println("The file " + path + " does not exist.");
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

    public static void write(Element domElementToWrite, String path)
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            File xmlFile = new File(path);

            if (!xmlFile.exists())
            {
                boolean isFileCreated = xmlFile.createNewFile();
                if (!isFileCreated)
                {
                    throw new IOException("Failed to create new file: " + path);
                }
            }

            doc.appendChild(domElementToWrite);

            // Write the content into an XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));

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

    // For testing purposes only
    private static void printDocument(Document doc) throws TransformerException
    {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult console = new StreamResult(System.out);
        transformer.transform(source, console);
    }

}
