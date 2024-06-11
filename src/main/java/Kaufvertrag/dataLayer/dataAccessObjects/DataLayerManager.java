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
        switch (readPersistenceType()) {
            case SQLITE: return new DataLayerSqlite();
            case XML: return new DataLayerXML();
            case JSON: return new DataLayerSqlite();
        }
        return null;
    }

    private PersistenceType readPersistenceType() {
        return persistenceType;
    }
}