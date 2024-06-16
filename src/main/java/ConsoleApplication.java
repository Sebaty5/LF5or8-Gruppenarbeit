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
                default -> {
                    System.out.println("Your input was wrong. Please make sure you use the correct options.");
                }
            }
        }

        dataLayerManager.setPersistenceType(PersistenceType.XML);
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
        System.out.println("What do you intend to do with your data access object?\nValid inputs are \"create\", \"read\", \"readall\", \"update\", \"delete\".\nIf you want to quit the program please input \"q\".");
        String inputMethod = sc.nextLine().trim().toLowerCase();
        switch (inputMethod)
        {
            case "create" -> {
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
                        case "ausweisnr" -> {
                            System.out.println("Please input a value.");
                            ausweisNr = sc.nextLine().trim();
                            System.out.println(ausweisNr + " entered.");
                        }
                        case "vorname" -> {
                            System.out.println("Please input a value.");
                            vorname = sc.nextLine().trim();
                            System.out.println(vorname + " entered.");
                        }
                        case "nachname" -> {
                            System.out.println("Please input a value.");
                            nachname = sc.nextLine().trim();
                            System.out.println(nachname + " entered.");
                        }
                        case "strasse" -> {
                            System.out.println("Please input a value.");
                            strasse = sc.nextLine().trim();
                            System.out.println(strasse + " entered.");
                        }
                        case "hausnr" -> {
                            System.out.println("Please input a value.");
                            hausNr = sc.nextLine().trim();
                            System.out.println(hausNr + " entered.");
                        }
                        case "plz" -> {
                            System.out.println("Please input a value.");
                            plz = sc.nextLine().trim();
                            System.out.println(plz + " entered.");
                        }
                        case "ort" -> {
                            System.out.println("Please input a value.");
                            ort = sc.nextLine().trim();
                            System.out.println(ort + " entered.");
                        }
                        case "create" -> {
                            IVertragspartner vertragspartner = new Vertragspartner(vorname, nachname);
                            vertragspartner.setAdresse(new Adresse(strasse, hausNr, plz, ort));
                            vertragspartner.setAusweisNr(ausweisNr);

                            dataAccessObject.create(vertragspartner);

                            System.out.println("you succesfully created the object:\n" + vertragspartner);

                            creating = false;
                        }
                        case "c" -> {
                            creating = false;
                        }
                    }
                }

            }
            case "read" -> {
                System.out.println(dataAccessObject.read(getString("Verkaufspartner AusweisNr", getClass())));
            }
            case "readall" -> {
                List<IVertragspartner> objectList = dataAccessObject.readAll();
                for (IVertragspartner object : objectList)
                {
                    System.out.println(object);
                }
            }
            case "update" -> {
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
                        case "ausweisnr" -> {
                            System.out.println("Please input a value.");
                            ausweisNr = sc.nextLine().trim();
                            System.out.println(ausweisNr + " entered.");
                        }
                        case "vorname" -> {
                            System.out.println("Please input a value.");
                            vorname = sc.nextLine().trim();
                            System.out.println(vorname + " entered.");
                        }
                        case "nachname" -> {
                            System.out.println("Please input a value.");
                            nachname = sc.nextLine().trim();
                            System.out.println(nachname + " entered.");
                        }
                        case "strasse" -> {
                            System.out.println("Please input a value.");
                            strasse = sc.nextLine().trim();
                            System.out.println(strasse + " entered.");
                        }
                        case "hausnr" -> {
                            System.out.println("Please input a value.");
                            hausNr = sc.nextLine().trim();
                            System.out.println(hausNr + " entered.");
                        }
                        case "plz" -> {
                            System.out.println("Please input a value.");
                            plz = sc.nextLine().trim();
                            System.out.println(plz + " entered.");
                        }
                        case "ort" -> {
                            System.out.println("Please input a value.");
                            ort = sc.nextLine().trim();
                            System.out.println(ort + " entered.");
                        }
                        case "update" -> {
                            System.out.println(id);
                            IVertragspartner vertragspartner = new Vertragspartner(vorname, nachname);
                            vertragspartner.setAdresse(new Adresse(strasse, hausNr, plz, ort));
                            vertragspartner.setAusweisNr(ausweisNr);
                            vertragspartner.setId(id);

                            dataAccessObject.update(vertragspartner);

                            System.out.println("you succesfully updated the object:\n" + vertragspartner);

                            updating = false;
                        }
                        case "c" -> {
                            updating = false;
                        }
                    }
                }
            }
            case "delete" -> {
                dataAccessObject.delete(getString("Verkaufspartner AusweisNr", getClass()));
            }
            case "q" ->
            {
                return;
            }
        }
    }

    private void doWare(IDao<IWare, Long> dataAccessObject) throws DaoException {
        System.out.println("What do you intend to do with your data access object?\nValid inputs are \"create\", \"read\", \"readall\", \"update\", \"delete\".\nIf you want to quit the program please input \"q\".");
        String inputMethod = sc.nextLine().trim().toLowerCase();
        switch (inputMethod)
        {
            case "create" -> {
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
                        case "bezeichnung" -> {
                            System.out.println("Please input a value.");
                            bezeichnung = sc.nextLine().trim();
                            System.out.println(beschreibung + " entered.");
                        }
                        case "beschreibung" -> {
                            System.out.println("Please input a value.");
                            beschreibung = sc.nextLine().trim();
                            System.out.println(beschreibung + " entered.");
                        }
                        case "preis" -> {
                            System.out.println("Please input a value. Follow the format of \"X.YY\"");
                            preis = Double.parseDouble(sc.nextLine().trim());
                            System.out.println(preis + " entered.");
                        }
                        case "besonderheiten" -> {
                            System.out.println("Please input a value. You can enter multiple ones by separating them with \";\"");
                            String besonderheitenString = sc.nextLine().trim();
                            besonderheiten = Arrays.stream(besonderheitenString.split(";")).toList();
                            System.out.println(besonderheitenString + " entered.");
                        }
                        case "maengel" -> {
                            System.out.println("Please input a value. You can enter multiple ones by separating them with \";\"");
                            String maengelString = sc.nextLine().trim();
                            maengel = Arrays.stream(maengelString.split(";")).toList();
                            System.out.println(maengelString + " entered.");
                        }
                        case "create" -> {
                            IWare ware = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);

                            dataAccessObject.create(ware);

                            System.out.println("you succesfully created the object:\n" + ware);

                            creating = false;
                        }
                        case "c" -> {
                            creating = false;
                        }
                    }
                }
            }
            case "read" -> {
                System.out.println(dataAccessObject.read(getID()));
            }
            case "readall" -> {
                List<IWare> objectList = dataAccessObject.readAll();
                for (IWare object : objectList)
                {
                    System.out.println(object);
                }
            }
            case "update" -> {
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
                        case "bezeichnung" -> {
                            System.out.println("Please input a value.");
                            bezeichnung = sc.nextLine().trim();
                            System.out.println(beschreibung + " entered.");
                        }
                        case "beschreibung" -> {
                            System.out.println("Please input a value.");
                            beschreibung = sc.nextLine().trim();
                            System.out.println(beschreibung + " entered.");
                        }
                        case "preis" -> {
                            System.out.println("Please input a value. Follow the format of \"X.YY\"");
                            preis = Double.parseDouble(sc.nextLine().trim());
                            System.out.println(preis + " entered.");
                        }
                        case "besonderheiten" -> {
                            System.out.println("Please input a value. You can enter multiple ones by separating them with \";\"");
                            String besonderheitenString = sc.nextLine().trim();
                            besonderheiten = Arrays.stream(besonderheitenString.split(";")).toList();
                            System.out.println(besonderheitenString + " entered.");
                        }
                        case "maengel" -> {
                            System.out.println("Please input a value. You can enter multiple ones by separating them with \";\"");
                            String maengelString = sc.nextLine().trim();
                            maengel = Arrays.stream(maengelString.split(";")).toList();
                            System.out.println(maengelString + " entered.");
                        }
                        case "update" -> {
                            IWare ware = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);
                            ware.setId(Long.parseLong(id));

                            dataAccessObject.update(ware);

                            System.out.println("you succesfully updated the object:\n" + ware);

                            updating = false;
                        }
                        case "c" -> {
                            updating = false;
                        }
                    }
                }
            }
            case "delete" -> {
                dataAccessObject.delete(getID());
            }
            case "q" ->
            {
                return;
            }
        }
    }

    @Override
    public Long getID()
    {
        System.out.println("Please input the ID of the object.");
        while (true)
        {
            String idString = sc.nextLine().trim();
            try
            {
                //This currently does not check the id for validity (if id exists in context)
                return Long.parseLong(idString);
            }
            catch (NumberFormatException ex)
            {
                System.out.println("Your input was wrong. Please make sure you input only numbers here.");
            }
        }
    }

    @Override
    public String getString(String whatToGetTheStringFor, Class<?> classForTheString)
    {
        String string = "";
        System.out.println("Please input your String for the " + whatToGetTheStringFor + " for the class " + classForTheString.getName() + ".");
        string = sc.nextLine();
        return string;
    }
}