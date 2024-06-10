package Kaufvertrag.dataLayer.dataAccessObjects;

import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.DataLayerSqlite;

public class DataLayerManager {
    private static DataLayerManager instance; // instance is a Singleton
    private String persistenceType;

    private DataLayerManager(){}

    public static DataLayerManager getInstance() {
        if(instance == null) {
            instance = new DataLayerManager();
        }
        return instance;
    }

    public IDataLayer getDataLayer() {
        return new DataLayerSqlite();
    }

    private String readPersistenceType() {
        return null;
    }
}