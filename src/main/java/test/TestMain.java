package test;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.dataAccessObjects.*;

public class TestMain {

    private static DataLayerManager dlm = DataLayerManager.getInstance();

    public static void main(String[] args) {
        testSQLite();
        testXML();
    }

    private static void testSQLite() {
        dlm.setPersistenceType(PersistenceType.SQLITE);
        test();
    }

    private static void testXML() {
        dlm.setPersistenceType(PersistenceType.XML);
        test();
    }

    private static void test() {
        IDataLayer dl = dlm.getDataLayer();
        IDao<IVertragspartner, String> dv = dl.getDaoVertragspartner();
        IDao<IWare, Long> dw = dl.getDaoWare();
        try {
            IVertragspartner v = dv.read(Integer.toString(dv.create().getID()));
            System.out.println(v.toString());
        }
        catch (DaoException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
