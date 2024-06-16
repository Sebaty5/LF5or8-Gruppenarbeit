package test;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Adresse;
import Kaufvertrag.dataLayer.businessClasses.Vertragspartner;
import Kaufvertrag.dataLayer.businessClasses.Ware;
import Kaufvertrag.dataLayer.dataAccessObjects.*;

import java.util.Arrays;
import java.util.List;

public class TestMain {

    private static DataLayerManager dlm = DataLayerManager.getInstance();

    public static void main(String[] args) {
        //testXML();
        testSQLite();
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
        testCreateEmpty(dl);
        testCreate(dl);
        testReadAll(dl);
        testUpdate(dl);
        testDelete(dl);
    }

    private static void testCreateEmpty(IDataLayer dl) {
        IDao<IVertragspartner, String> dv = dl.getDaoVertragspartner();
        IDao<IWare, Long> dw = dl.getDaoWare();
        try {
            IVertragspartner v = dv.read(dv.create().getId());
            System.out.println(v.toString());
        }
        catch (DaoException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {
            IWare w = dw.read(dw.create().getId());
            System.out.println(w.toString());
        }
        catch (DaoException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void testCreate(IDataLayer dl) {
        String vorname = "Maxine";
        String nachname = "Musterfrau";
        String ausweisNr = "-2";
        String strasse = "Immer Überall Ballustrade";
        String hausNr = "-2";
        String plz = "54321";
        String ort = "Everywhere";

        IVertragspartner vertragspartner = new Vertragspartner(vorname, nachname);
        vertragspartner.setAdresse(new Adresse(strasse, hausNr, plz, ort));
        vertragspartner.setAusweisNr(ausweisNr);

        IDao<IVertragspartner, String> dv = dl.getDaoVertragspartner();

        try {
            IVertragspartner v = dv.read(dv.create(vertragspartner).getId());
            System.out.println(v.toString());
        }
        catch (DaoException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


        String bezeichnung = "Anderes Musterdings";
        String beschreibung = "Es ist nicht da (oder etwa doch?)";
        double preis = 20202;
        List<String> besonderheiten = Arrays.asList("Nope", "Keine");
        List<String> maengel= Arrays.asList("Ebenfalls", "Keine");

        IWare ware = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);

        IDao<IWare, Long> dw = dl.getDaoWare();

        try {
            IWare w = dw.read(dw.create(ware).getId());
            System.out.println(w.toString());
        }
        catch (DaoException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void testReadAll(IDataLayer dl) {
        IDao<IVertragspartner, String> dv = dl.getDaoVertragspartner();
        List<IVertragspartner> listPartner = dv.readAll();
        System.out.println("Alle existierenden Vertragspartner-IDs:");
        for (IVertragspartner v : listPartner) {
            System.out.println(v.getId());
        }

        IDao<IWare, Long> dw = dl.getDaoWare();
        List<IWare> listWare = dw.readAll();
        System.out.println("Alle existierenden Waren-IDs:");
        for (IWare w : listWare) {
            System.out.println(w.getId());
        }
    }

    private static void testUpdate(IDataLayer dl) {
        String idPartner = "1";
        String vorname = "Der Neue";
        String nachname = "Typ";
        String ausweisNr = "1";
        String strasse = "Frisch Errichtet Straße";
        String hausNr = "1";
        String plz = "1";
        String ort = "Aus dem Nichts erschienen";

        IVertragspartner vertragspartner = new Vertragspartner(vorname, nachname);
        vertragspartner.setAdresse(new Adresse(strasse, hausNr, plz, ort));
        vertragspartner.setAusweisNr(ausweisNr);
        vertragspartner.setId(idPartner);

        IDao<IVertragspartner, String> dv = dl.getDaoVertragspartner();

        try {
            dv.update(vertragspartner);
            IVertragspartner v = dv.read(idPartner);
            System.out.println(v.toString());
        }
        catch (DaoException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        long idWare = 1;
        String bezeichnung = "Das Superneue Dings";
        String beschreibung = "Es ist da (seit grade eben!)";
        double preis = 99999;
        List<String> besonderheiten = Arrays.asList("Super", "Neu");
        List<String> maengel= Arrays.asList("Keine", "(bis jetzt)");

        IWare ware = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);
        ware.setId(idWare);

        IDao<IWare, Long> dw = dl.getDaoWare();

        try {
            dw.update(ware);
            IWare w = dw.read(idWare);
            System.out.println(w.toString());
        }
        catch (DaoException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void testDelete(IDataLayer dl) {
        IDao<IVertragspartner, String> dv = dl.getDaoVertragspartner();
        IDao<IWare, Long> dw = dl.getDaoWare();

        try {
            IVertragspartner v = dv.read(dv.create().getId());
            String idPartner = v.getId();
            System.out.println("Vertragspartner mit ID " + idPartner + " erstellt.");
            System.out.println("Lösche Eintrag mit ID " + idPartner + "...");
            dv.delete(idPartner);
            System.out.println("Versuche, Eintrag mit ID " + idPartner + " zu lesen...");
            dv.read(idPartner);
        }
        catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        try {
            IWare w = dw.read(dw.create().getId());
            long idWare = w.getId();
            System.out.println("Ware mit ID " + idWare + " erstellt.");
            System.out.println("Lösche Eintrag mit ID " + idWare + "...");
            dw.delete(idWare);
            System.out.println("Versuche, Eintrag mit ID " + idWare + " zu lesen...");
            dw.read(idWare);
        }
        catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }
}
