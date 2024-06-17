package Kaufvertrag.dataLayer.dataAccessObjects;

import Kaufvertrag.dataLayer.dataAccessObjects.XML.DataLayerXML;
import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.DataLayerSqlite;

public class DataLayerManager {
    private static DataLayerManager instance; // instance is a Singleton
    private PersistenceType persistenceType = PersistenceType.SQLITE;

    private DataLayerManager(){}

    public static DataLayerManager getInstance() {
        if(instance == null) {
            instance = new DataLayerManager();
        }
        return instance;
    }

    public void setPersistenceType(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
    }

    public IDataLayer getDataLayer() {
        return switch (readPersistenceType()) {
            case SQLITE -> new DataLayerSqlite();
            case XML -> new DataLayerXML();
            case JSON -> new DataLayerSqlite();
        };
    }

    private PersistenceType readPersistenceType() {
        return persistenceType;
    }
}