import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Adresse;
import Kaufvertrag.dataLayer.businessClasses.Vertragspartner;
import Kaufvertrag.dataLayer.businessClasses.Ware;
import Kaufvertrag.dataLayer.dataAccessObjects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleApplication implements IApplication
{
    private final Scanner sc = new Scanner(System.in);

    public ConsoleApplication()
    {

    }

    @Override
    public void startApplication() throws DaoException {
        System.out.println("The program successfully started.");
        DataLayerManager dataLayerManager = DataLayerManager.getInstance();
        choosePersistence(dataLayerManager);

        IDataLayer dataLayer = dataLayerManager.getDataLayer();

        while(true)
        {
            System.out.println("Which Data Access Object do you want to manipulate?\nValid inputs are \"ware\" or \"vertragspartner\".\nIf you want to quit the program, please input \"q\".");
            String inputDao = sc.nextLine().trim().toLowerCase();
            switch (inputDao)
            {
                case "ware" -> doWare(dataLayer.getDaoWare());
                case "vertragspartner" -> doVertragspartner(dataLayer.getDaoVertragspartner());
                case "q" ->
                {
                    return;
                }
            }
        }
    }

    private void doVertragspartner(IDao<IVertragspartner, String> dataAccessObject) throws DaoException {
        while (true) {
            System.out.println("What do you intend to do with your data access object?\nValid inputs are \"create\", \"read\", \"readall\", \"update\", \"delete\".\nIf you want to change the data access object, please input \"q\".");
            String inputMethod = sc.nextLine().trim().toLowerCase();
            switch (inputMethod) {
                case "create" -> createVertragsPartner(dataAccessObject);
                case "read" -> readVertragspartner(dataAccessObject);
                case "readall" -> readAllVertragspartner(dataAccessObject);
                case "update" -> updateVertragspartner(dataAccessObject);
                case "delete" -> deleteVertragspartner(dataAccessObject);
                case "q" -> {
                    return;
                }
            }
        }
    }

    private void doWare(IDao<IWare, Long> dataAccessObject) throws DaoException {
        while (true) {
            System.out.println("What do you intend to do with your data access object?\nValid inputs are \"create\", \"read\", \"readall\", \"update\", \"delete\".\nIf you want to change the data access object, please input \"q\".");
            String inputMethod = sc.nextLine().trim().toLowerCase();
            switch (inputMethod) {
                case "create" -> createWare(dataAccessObject);
                case "read" -> readWare(dataAccessObject);
                case "readall" -> readAllWare(dataAccessObject);
                case "update" -> updateWare(dataAccessObject);
                case "delete" -> deleteWare(dataAccessObject);
                case "q" -> {
                    return;
                }
            }
        }
    }

    private void choosePersistence(DataLayerManager dataLayerManager)
    {
        boolean choosingPersistence = true;
        while (choosingPersistence)
        {
            System.out.println("Which type of persistence do you want? Valid inputs are \"sqlite\" or \"xml\".");
            String type =  sc.nextLine().trim().toLowerCase();
            switch (type) {
                case "sqlite" -> {
                    dataLayerManager.setPersistenceType(PersistenceType.SQLITE);
                    choosingPersistence = false;
                }
                case "xml" -> {
                    dataLayerManager.setPersistenceType(PersistenceType.XML);
                    choosingPersistence = false;
                }
                default -> System.out.println("Your input was wrong. Please make sure you use the correct options.");
            }
        }
    }

    private void createVertragsPartner(IDao<IVertragspartner, String> dataAccessObject)
    {
        boolean creating = true;
        String ausweisNr = "";
        String nachname = "";
        String vorname = "";
        String strasse = "";
        String hausNr = "";
        String plz = "";
        String ort = "";
        while (creating)
        {
            System.out.println("Please input the attribute that you want to enter a value in.\nValid inputs are \"AusweisNr\", \"Vorname\", \"Nachame\", \"Strasse\", \"HausNr\", \"PLZ\", \"Ort\".");
            System.out.println("If you are finished and want to create the object, please input \"create\".");
            System.out.println("If you want to cancel creating, please input \"c\".");
            String attributeInput = sc.nextLine().trim().toLowerCase();
            switch (attributeInput) {
                case "ausweisnr" -> ausweisNr = inputValue();
                case "vorname" -> vorname = inputValue();
                case "nachname" -> nachname = inputValue();
                case "strasse" -> strasse = inputValue();
                case "hausnr" -> hausNr = inputValue();
                case "plz" -> plz = inputValue();
                case "ort" -> ort = inputValue();
                case "create" -> {
                    createVertragspartnerObject(dataAccessObject, vorname, nachname, strasse, hausNr, plz, ort, ausweisNr);
                    creating = false;
                }
                case "c" -> creating = false;
            }
        }
    }
    private static void createVertragspartnerObject(IDao<IVertragspartner, String> dataAccessObject, String vorname, String nachname, String strasse, String hausNr, String plz, String ort, String ausweisNr)
    {
        IVertragspartner vertragspartner = new Vertragspartner(vorname, nachname);
        vertragspartner.setAdresse(new Adresse(strasse, hausNr, plz, ort));
        vertragspartner.setAusweisNr(ausweisNr);
        dataAccessObject.create(vertragspartner);
        System.out.println("you successfully created the object:\n" + vertragspartner);
    }

    private void readVertragspartner(IDao<IVertragspartner, String> dataAccessObject) throws DaoException
    {
        System.out.println("Please enter the ID of the object you want to read.");
        String id = sc.nextLine().trim();
        System.out.println(dataAccessObject.read(id));
    }

    private static void readAllVertragspartner(IDao<IVertragspartner, String> dataAccessObject)
    {
        List<IVertragspartner> objectList = dataAccessObject.readAll();
        for (IVertragspartner object : objectList)
        {
            System.out.println(object);
        }
    }

    private void updateVertragspartner(IDao<IVertragspartner, String> dataAccessObject) throws DaoException
    {
        System.out.println("Please enter the ID of the object you want to update.");
        String id = sc.nextLine().trim();
        IVertragspartner objectToUpdate = dataAccessObject.read(id);
        boolean updating = true;
        String ausweisNr = objectToUpdate.getAusweisNr();
        String nachname = objectToUpdate.getNachname();
        String vorname = objectToUpdate.getVorname();
        String strasse = objectToUpdate.getAdresse().getStrasse();
        String hausNr = objectToUpdate.getAdresse().getHausNr();
        String plz = objectToUpdate.getAdresse().getPlz();
        String ort = objectToUpdate.getAdresse().getOrt();
        while (updating)
        {
            System.out.println("Please input the attribute whose value you want to update.\nValid inputs are \"AusweisNr\", \"Vorname\", \"Nachame\", \"Strasse\", \"HausNr\", \"PLZ\", \"Ort\".");
            System.out.println("If you are finished and want to update the object, please input \"update\".");
            System.out.println("If you want to cancel the update, please input \"c\".");
            String attributeInput = sc.nextLine().trim().toLowerCase();
            switch (attributeInput) {
                case "ausweisnr" -> ausweisNr = inputValue();
                case "vorname" -> vorname = inputValue();
                case "nachname" -> nachname = inputValue();
                case "strasse" -> strasse = inputValue();
                case "hausnr" -> hausNr = inputValue();
                case "plz" -> plz = inputValue();
                case "ort" -> ort = inputValue();
                case "update" -> {
                    updateVertragspartnerObject(dataAccessObject, vorname, nachname, strasse, hausNr, plz, ort, ausweisNr, id);
                    updating = false;
                }
                case "c" -> updating = false;
            }
        }
    }

    private static void updateVertragspartnerObject(IDao<IVertragspartner, String> dataAccessObject, String vorname, String nachname, String strasse, String hausNr, String plz, String ort, String ausweisNr, String id)
    {
        IVertragspartner vertragspartner = new Vertragspartner(vorname, nachname);
        vertragspartner.setAdresse(new Adresse(strasse, hausNr, plz, ort));
        vertragspartner.setAusweisNr(ausweisNr);
        vertragspartner.setId(id);
        dataAccessObject.update(vertragspartner);
        System.out.println("you succesfully updated the object:\n" + vertragspartner);
    }

    private void deleteVertragspartner(IDao<IVertragspartner, String> dataAccessObject)
    {
        System.out.println("Please enter the ID of the object you want to delete.");
        String id = sc.nextLine().trim();
        dataAccessObject.delete(id);
        System.out.println("object successfully deleted.");
    }

    private void createWare(IDao<IWare, Long> dataAccessObject)
    {
        boolean creating = true;
        String bezeichnung = "";
        String beschreibung = "";
        double preis = 0.0;
        List <String> besonderheiten = new ArrayList<>();
        List <String> maengel = new ArrayList<>();
        while (creating)
        {
            System.out.println("Please input the attribute that you want to enter a value in.\nValid inputs are \"Bezeichnung\", \"Beschreibung\", \"Preis\", \"Besonderheiten\", \"Maengel\".");
            System.out.println("If you are finished and want to create the object, please input \"create\".");
            System.out.println("If you want to cancel creating, please input \"c\".");
            String attributeInput = sc.nextLine().trim().toLowerCase();
            switch (attributeInput) {
                case "bezeichnung" -> bezeichnung = inputValue();
                case "beschreibung" -> beschreibung = inputValue();
                case "preis" -> preis = inputValueDouble();
                case "besonderheiten" -> besonderheiten = inputValueList();
                case "maengel" -> maengel = inputValueList();
                case "create" -> {
                    createWareObject(dataAccessObject, bezeichnung, beschreibung, preis, besonderheiten, maengel);
                    creating = false;
                }
                case "c" -> creating = false;
            }
        }
    }

    private static void createWareObject(IDao<IWare, Long> dataAccessObject, String bezeichnung, String beschreibung, double preis, List<String> besonderheiten, List<String> maengel)
    {
        IWare ware = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);
        dataAccessObject.create(ware);
        System.out.println("you succesfully created the object:\n" + ware);
    }

    private void readWare(IDao<IWare, Long> dataAccessObject) throws DaoException
    {
        System.out.println("Please enter the ID of the object you want to read.");
        Long id = Long.valueOf(sc.nextLine().trim());
        System.out.println(dataAccessObject.read(id));
    }

    private static void readAllWare(IDao<IWare, Long> dataAccessObject)
    {
        List<IWare> objectList = dataAccessObject.readAll();
        for (IWare object : objectList)
        {
            System.out.println(object);
        }
    }

    private void updateWare(IDao<IWare, Long> dataAccessObject) throws DaoException
    {
        System.out.println("Please enter the ID of the object you want to update.");
        String id = sc.nextLine().trim();
        IWare objectToUpdate = dataAccessObject.read(Long.valueOf(id));
        boolean updating = true;
        String bezeichnung = objectToUpdate.getBezeichnung();
        String beschreibung = objectToUpdate.getBeschreibung();
        double preis = objectToUpdate.getPreis();
        List <String> besonderheiten = objectToUpdate.getBesonderheiten();
        List <String> maengel = objectToUpdate.getMaengel();
        while (updating)
        {
            System.out.println("Please input the attribute whose value you want to update.\nValid inputs are \"Bezeichnung\", \"Beschreibung\", \"Preis\", \"Besonderheiten\", \"Maengel\".");
            System.out.println("If you are finished and want to create the object, please input \"update\".");
            System.out.println("If you want to cancel updating, please input \"c\".");
            String attributeInput = sc.nextLine().trim().toLowerCase();
            switch (attributeInput) {
                case "bezeichnung" -> bezeichnung = inputValue();
                case "beschreibung" -> beschreibung = inputValue();
                case "preis" -> preis = inputValueDouble();
                case "besonderheiten" -> besonderheiten = inputValueList();
                case "maengel" -> maengel = inputValueList();
                case "update" -> {
                    updateWareObject(dataAccessObject, bezeichnung, beschreibung, preis, besonderheiten, maengel, id);
                    updating = false;
                }
                case "c" -> updating = false;
            }
        }
    }

    private static void updateWareObject(IDao<IWare, Long> dataAccessObject, String bezeichnung, String beschreibung, double preis, List<String> besonderheiten, List<String> maengel, String id)
    {
        IWare ware = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);
        ware.setId(Long.parseLong(id));
        dataAccessObject.update(ware);
        System.out.println("you successfully updated the object:\n" + ware);
    }

    private void deleteWare(IDao<IWare, Long> dataAccessObject)
    {
        System.out.println("Please enter the ID of the object you want to delete.");
        Long id = Long.parseLong(sc.nextLine().trim());
        dataAccessObject.delete(id);
        System.out.println("object successfully deleted.");
    }

    String inputValue() {
        System.out.println("Please input a value.");
        String valueinput = sc.nextLine().trim();
        System.out.println(valueinput + " entered.");
        return valueinput;
    }
    double inputValueDouble() {
        System.out.println("Please input a value. Follow the format of \"X.YY\"");
        double valueinput = Double.parseDouble(sc.nextLine().trim());
        System.out.println(valueinput + " entered.");
        return valueinput;
    }

    List<String> inputValueList() {
        System.out.println("Please input a value. You can enter multiple ones by separating them with \";\"");
        String besonderheitenString = sc.nextLine().trim();
        List<String> inputvalue = Arrays.stream(besonderheitenString.split(";")).toList();
        System.out.println(besonderheitenString + " entered.");
        return inputvalue;
    }
}