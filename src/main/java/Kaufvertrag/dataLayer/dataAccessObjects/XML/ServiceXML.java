package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import Kaufvertrag.businessInterfaces.IWare;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import javax.xml.validation.Schema;

public class ServiceXML {

    private static final String XSD_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    public static IWare read(Long id) {

        return null;
    }

    public static void write(IWare ware) {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            Document doc = dbFactory.newDocumentBuilder().parse(new InputSource("ware.xsd"));

            // Create and append root element
            Element rootElement = doc.createElement("ware");
            doc.appendChild(rootElement);

            // Create and append ComplexType to root element
            Element complexType = doc.createElementNS(XSD_SCHEMA, "xs:complexType");
            rootElement.appendChild(complexType);

            // Create and append rootSequence, which contains the variables of ware, to complexType
            Element rootSequence = doc.createElementNS(XSD_SCHEMA, "xs:sequence");
            complexType.appendChild(rootSequence);

            // Create and append elements: id, bezeichnung, beschreibung, preis to rootSequence
            Element idElement = doc.createElement("id");
            // set namespace attributes (attributes in the element header)
            idElement.setAttributeNS(null, "name", "id");
            idElement.setAttributeNS(null, "type", "long");
            // set attribute
            idElement.setTextContent(String.valueOf(ware.getId()));
            rootSequence.appendChild(idElement);

            Element bezeichnungElement = doc.createElement("bezeichnung");
            bezeichnungElement.setAttributeNS(null, "name", "bezeichnung");
            bezeichnungElement.setAttributeNS(null, "type", "string");
            bezeichnungElement.setTextContent(ware.getBezeichnung());
            rootSequence.appendChild(bezeichnungElement);

            Element beschreibungElement = doc.createElement("beschreibung");
            beschreibungElement.setAttributeNS(null, "name", "beschreibung");
            beschreibungElement.setAttributeNS(null, "type", "string");
            beschreibungElement.setTextContent(ware.getBeschreibung());
            rootSequence.appendChild(beschreibungElement);

            Element preisElement = doc.createElement("preis");
            preisElement.setAttributeNS(null, "name", "preis");
            preisElement.setAttributeNS(null, "type", "double");
            preisElement.setTextContent(String.valueOf(ware.getPreis()));
            rootSequence.appendChild(preisElement);

            // Create and append besonderheiten and maengel as sequences
            Element subSequence = doc.createElementNS(XSD_SCHEMA, "xs:sequence");
            complexType.appendChild(rootSequence);

            subSequence = doc.createElementNS(XSD_SCHEMA, "xs:sequence");
            complexType.appendChild(rootSequence);

            // Create and append all entries of besonderheiten and maengel
            for (String besonderheit : ware.getBesonderheiten())
            {
                Element besonderheitToAdd = doc.createElement("besonderheit");
                besonderheitToAdd.setTextContent(besonderheit);
                subSequence.appendChild(besonderheitToAdd);
            }

            for (String mangel : ware.getMaengel())
            {
                Element besonderheitToAdd = doc.createElement("besonderheit");
                besonderheitToAdd.setTextContent(mangel);
                subSequence.appendChild(besonderheitToAdd);
            }

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL schemaURL = new URL("ware.xml");
            Schema wareSchema = sf.newSchema(schemaURL);
            Validator wareValidator = wareSchema.newValidator();
            wareValidator.validate(new DOMSource(doc));



        }
        catch (ParserConfigurationException | IOException | SAXException e)
        {
            throw new RuntimeException(e);
        }
    }
}