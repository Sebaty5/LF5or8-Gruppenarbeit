package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;

//This is a factory for IDao's
public class DataLayerXML implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        return new VertragspartnerDaoXML();
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        return new WareDaoXML();
    }
}