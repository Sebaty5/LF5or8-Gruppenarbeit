package Kaufvertrag.dataLayer.dataAccessObjects;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

//This is a factory
public class DataLayerManager {
    private static DataLayerManager instance;
    private String persistenceType;

    private DataLayerManager(){}

    public static DataLayerManager getInstance() {
        return instance;
    }

    public IDataLayer getDataLayer() {
        return null;
    }

    private String readPersistenceType() {
        return null;
    }
}