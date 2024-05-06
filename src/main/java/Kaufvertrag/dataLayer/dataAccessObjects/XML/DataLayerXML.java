package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Vertragspartner;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;

public class DataLayerXML implements IDataLayer {
    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        return null;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        return null;
    }
}
