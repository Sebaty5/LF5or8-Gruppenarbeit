package Kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;

import javax.lang.model.type.NullType;

//This is a factory for IDao's
public class DataLayerSqlite implements IDataLayer {
    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        return null;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        return null;
    }
}
