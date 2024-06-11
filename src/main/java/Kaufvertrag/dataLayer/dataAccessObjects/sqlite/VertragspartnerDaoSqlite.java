package Kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import Kaufvertrag.businessInterfaces.IAdresse;
import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.dataLayer.businessClasses.Adresse;
import Kaufvertrag.dataLayer.businessClasses.Vertragspartner;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.database.FieldType;
import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.database.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class VertragspartnerDaoSqlite implements IDao<IVertragspartner, String> {
    private final String tableName = "vertragspartner";

    public VertragspartnerDaoSqlite() {
        Table.Builder tableBuilder = new Table.Builder(tableName);
        tableBuilder.addField("ID", FieldType.STRING, true, true);
        tableBuilder.addField("AusweisNr", FieldType.STRING, false, true);
        tableBuilder.addField("Vorname", FieldType.STRING, false, true);
        tableBuilder.addField("Nachname", FieldType.STRING, false, true);
        tableBuilder.addField("Strasse", FieldType.STRING, false, true);
        tableBuilder.addField("HausNr", FieldType.STRING, false, true);
        tableBuilder.addField("Plz", FieldType.STRING, false, true);
        tableBuilder.addField("Ort", FieldType.STRING, false, true);
        Table vertragspartnerTable = tableBuilder.build();
        ConnectionManager.INSTANCE.createNewTable(vertragspartnerTable);
    }

    @Override
    public IVertragspartner create() {
        String vorname = "Max";
        String nachname = "Mustermann";
        String ausweisNr = "-1";
        String strasse = "Nicht Vorhanden Allee";
        String hausNr = "-1";
        String plz = "12345";
        String ort = "Nowhere";

        IVertragspartner vertragspartner = new Vertragspartner(vorname, nachname);
        vertragspartner.setAdresse(new Adresse(strasse, hausNr, plz, ort));
        vertragspartner.setAusweisNr(ausweisNr);
        String getIDSql = "SELECT ID FROM " + tableName + " ORDER BY ID DESC LIMIT 1;";
        int id = parseInt(ConnectionManager.INSTANCE.executeQuerySQL(getIDSql, new String[]{}).get(0).get("ID")) + 1;
        vertragspartner.setID(id);
        String sqlString = "REPLACE INTO " + tableName + " (ID, AusweisNr, Vorname, Nachname, Strasse, HausNr, Plz, Ort) VALUES(?,?,?,?,?,?,?,?)";
        ConnectionManager.INSTANCE.executeSQL(sqlString, new String[]{Integer.toString(id), ausweisNr, vorname, nachname, strasse, hausNr, plz, ort});

        return vertragspartner;
    }

    @Override
    public IVertragspartner create(IVertragspartner objectToInsert) {
        String vorname = objectToInsert.getVorname();
        String nachname = objectToInsert.getNachname();
        String ausweisNr = objectToInsert.getAusweisNr();
        String strasse = objectToInsert.getAdresse().getStrasse();
        String hausNr = objectToInsert.getAdresse().getHausNr();
        String plz = objectToInsert.getAdresse().getPlz();
        String ort = objectToInsert.getAdresse().getOrt();

        String getIDSql = "SELECT ID FROM " + tableName + " ORDER BY ID DESC LIMIT 1;";
        int id = parseInt(ConnectionManager.INSTANCE.executeQuerySQL(getIDSql, new String[]{}).get(0).get("ID")) + 1;
        objectToInsert.setID(id);
        String sqlString = "REPLACE INTO " + tableName + " (ID, AusweisNr, Vorname, Nachname, Strasse, HausNr, Plz, Ort) VALUES(?,?,?,?,?,?,?,?)";
        ConnectionManager.INSTANCE.executeSQL(sqlString, new String[]{Integer.toString(id), ausweisNr, vorname, nachname, strasse, hausNr, plz, ort});
        
        return objectToInsert;
    }

    @Override
    public IVertragspartner read(String id) {
        String sql = "SELECT * FROM " + tableName + " WHERE ID = ?";
        List<Map<String, String>> resultList = ConnectionManager.INSTANCE.executeQuerySQL(sql, new String[]{id});
        IVertragspartner vertragspartner = new Vertragspartner(resultList.get(0).get("Vorname"), resultList.get(0).get("Nachname"));
        vertragspartner.setAusweisNr(resultList.get(0).get("AusweisNr"));
        IAdresse adresse = new Adresse(resultList.get(0).get("Strasse"), resultList.get(0).get("HausNr"), resultList.get(0).get("Plz"), resultList.get(0).get("Ort"));
        vertragspartner.setAdresse(adresse);
        vertragspartner.setID(parseInt(resultList.get(0).get("ID")));
        return vertragspartner;
    }

    @Override
    public List<IVertragspartner> readAll() {
        String sql = "SELECT * FROM " + tableName;
        List<Map<String, String>> resultList = ConnectionManager.INSTANCE.executeQuerySQL(sql, new String[]{});
        ArrayList<IVertragspartner> returnList = new ArrayList<>(); 
        for (Map<String, String> m:resultList) {
            IVertragspartner vertragspartner = new Vertragspartner(m.get("Vorname"), m.get("Nachname"));
            vertragspartner.setAusweisNr(m.get("AusweisNr"));
            IAdresse adresse = new Adresse(m.get("Strasse"), m.get("HausNr"), m.get("Plz"), m.get("Ort"));
            vertragspartner.setAdresse(adresse);
            vertragspartner.setID(parseInt(m.get("ID")));
            returnList.add(vertragspartner);
        }
        return returnList;
    }

    @Override
    public void update(IVertragspartner objectToUpdate) {
        String vorname = objectToUpdate.getVorname();
        String nachname = objectToUpdate.getNachname();
        String ausweisNr = objectToUpdate.getAusweisNr();
        String strasse = objectToUpdate.getAdresse().getStrasse();
        String hausNr = objectToUpdate.getAdresse().getHausNr();
        String plz = objectToUpdate.getAdresse().getPlz();
        String ort = objectToUpdate.getAdresse().getOrt();
        int id = objectToUpdate.getID();
        String sqlString = "REPLACE INTO " + tableName + " (ID, AusweisNr, Vorname, Nachname, Strasse, HausNr, Plz, Ort) VALUES(?,?,?,?,?,?,?,?)";
        ConnectionManager.INSTANCE.executeSQL(sqlString, new String[]{Integer.toString(id), ausweisNr, vorname, nachname, strasse, hausNr, plz, ort});
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM " + tableName + " WHERE ID = ?";
        ConnectionManager.INSTANCE.executeSQL(sql, new String[]{id});
    }
}
