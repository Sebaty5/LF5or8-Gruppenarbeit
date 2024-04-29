package Kaufvertrag.dataLayer.dataAccessObjects;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;

public interface IDataLayer {
    IDao<IVertragspartner, String> getDaoVertragspartner();
    IDao<IWare, Long> getDaoWare();
}
