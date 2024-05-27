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
import java.io.File;
import java.util.List;

public class ServiceXML {
    private static final String WARE_XML = "wareList.xml"; // Adjust the path as needed

    public static IWare read(Long id) {
        // Implementation of read method
        return null;
    }

    public static void main(String[] args) {
        List<String> besonderheiten = List.of("Ist toll");
        List<String> maengel = List.of("ist aber auch kaputt");

        Ware test = new Ware("test", "test", 20, besonderheiten, maengel);
        Ware test2 = new Ware("test2", "test2", 20, besonderheiten, maengel);
        test.setId(10);
        test2.setId(11);
        write(test);
        write(test2);
    }

    private static void printDocument(Document doc) {
        // Get the root element of the document
        Element rootElement = doc.getDocumentElement();
        // Print the root element
        System.out.println(rootElement.getNodeName());
        // Print child elements recursively
        printElements(rootElement.getChildNodes(), 1);
    }

    // Recursive method to print child elements
    private static void printElements(NodeList nodeList, int depth) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                // Print element name with indentation based on depth
                System.out.print(getIndentation(depth) + element.getNodeName());
                // Print element attributes, if any
                NamedNodeMap attributes = element.getAttributes();
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attribute = attributes.item(j);
                    System.out.print(" " + attribute.getNodeName() + "=" + attribute.getNodeValue());
                }
                System.out.println();
                // Recursively print child elements
                printElements(element.getChildNodes(), depth + 1);
            }
        }
    }

    // Utility method to generate indentation based on depth
    private static String getIndentation(int depth) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indentation.append("  "); // Two spaces for each depth level
        }
        return indentation.toString();
    }

    public static void write(IWare ware) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Create root element
            Element rootElement = doc.createElementNS("https://www.w3schools.com", "wareList");
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

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static Element createWareElement(Document doc, IWare ware) {
        Element wareElement = doc.createElementNS("https://www.w3schools.com", "ware");

        // Id element
        Element idElement = doc.createElementNS("https://www.w3schools.com", "id");
        idElement.appendChild(doc.createTextNode(String.valueOf(ware.getId())));
        wareElement.appendChild(idElement);

        // Bezeichnung element
        Element bezeichnungElement = doc.createElementNS("https://www.w3schools.com", "bezeichnung");
        bezeichnungElement.appendChild(doc.createTextNode(ware.getBezeichnung()));
        wareElement.appendChild(bezeichnungElement);

        // Beschreibung element
        Element beschreibungElement = doc.createElementNS("https://www.w3schools.com", "beschreibung");
        beschreibungElement.appendChild(doc.createTextNode(ware.getBeschreibung()));
        wareElement.appendChild(beschreibungElement);

        // Preis element
        Element preisElement = doc.createElementNS("https://www.w3schools.com", "preis");
        preisElement.appendChild(doc.createTextNode(String.valueOf(ware.getPreis())));
        wareElement.appendChild(preisElement);

        // Besonderheiten elements
        for (String besonderheit : ware.getBesonderheiten()) {
            Element besonderheitElement = doc.createElementNS("https://www.w3schools.com", "besonderheit");
            besonderheitElement.appendChild(doc.createTextNode(besonderheit));
            wareElement.appendChild(besonderheitElement);
        }

        // Mangel elements
        for (String mangel : ware.getMaengel()) {
            Element mangelElement = doc.createElementNS("https://www.w3schools.com", "mangel");
            mangelElement.appendChild(doc.createTextNode(mangel));
            wareElement.appendChild(mangelElement);
        }

        return wareElement;
    }
}
