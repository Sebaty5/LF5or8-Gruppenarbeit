package Kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Ware;
import Kaufvertrag.dataLayer.dataAccessObjects.DaoException;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.database.FieldType;
import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.database.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class WareDaoSqlite implements IDao<IWare, Long> {
    private final String tableName = "ware";

    public WareDaoSqlite() {
        Table.Builder tableBuilder = new Table.Builder(tableName);
        tableBuilder.addField("ID", FieldType.STRING, true, true);
        tableBuilder.addField("Bezeichnung", FieldType.STRING, false, true);
        tableBuilder.addField("Beschreibung", FieldType.STRING, false, true);
        tableBuilder.addField("Preis", FieldType.STRING, false, true);
        tableBuilder.addField("Besonderheiten", FieldType.STRING, false, true);
        tableBuilder.addField("Maengel", FieldType.STRING, false, true);
        Table wareTable = tableBuilder.build();
        ConnectionManager.INSTANCE.createNewTable(wareTable);
    }

    @Override
    public IWare create() {
        String bezeichnung = "Musterdings";
        String beschreibung = "Es ist da (oder auch nicht)";
        double preis = 50505;
        List<String> besonderheiten = Arrays.asList("Ganz", "Besonders", "Viele");
        List<String> maengel= Arrays.asList("Absolut", "Keine", "Maengel");

        IWare ware = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);
        String getIDSql = "SELECT ID FROM " + tableName + " ORDER BY ID DESC LIMIT 1;";
        long id = parseLong(ConnectionManager.INSTANCE.executeQuerySQL(getIDSql, new String[]{}).get(0).get("ID")) + 1;
        ware.setId(id);

        String sqlString = "REPLACE INTO " + tableName + " (ID, Bezeichnung, Beschreibung, Preis, Besonderheiten, Maengel) VALUES(?,?,?,?,?,?)";
        ConnectionManager.INSTANCE.executeSQL(sqlString, new String[]{Long.toString(id), bezeichnung, beschreibung, Double.toString(preis), listToString(besonderheiten), listToString(maengel)});
        return ware;
    }

    @Override
    public IWare create(IWare objectToInsert) {
        String bezeichnung = objectToInsert.getBezeichnung();
        String beschreibung = objectToInsert.getBeschreibung();
        double preis = objectToInsert.getPreis();
        List<String> besonderheiten = objectToInsert.getBesonderheiten();
        List<String> maengel = objectToInsert.getMaengel();

        String getIDSql = "SELECT ID FROM " + tableName + " ORDER BY ID DESC LIMIT 1;";
        int id = parseInt(ConnectionManager.INSTANCE.executeQuerySQL(getIDSql, new String[]{}).get(0).get("ID")) + 1;
        objectToInsert.setId(id);
        String sqlString = "REPLACE INTO " + tableName + " (ID, Bezeichnung, Beschreibung, Preis, Besonderheiten, Maengel) VALUES(?,?,?,?,?,?)";
        ConnectionManager.INSTANCE.executeSQL(sqlString, new String[]{Long.toString(id), bezeichnung, beschreibung, Double.toString(preis), listToString(besonderheiten), listToString(maengel)});

        return objectToInsert;
    }

    @Override
    public IWare read(Long id) throws DaoException {
        String sql = "SELECT * FROM " + tableName + " WHERE ID = ?";
        List<Map<String, String>> resultList = ConnectionManager.INSTANCE.executeQuerySQL(sql, new String[]{String.valueOf(id)});
        if (resultList.size() <= 0){
            throw new DaoException("Tried to read Ware with unknown ID.");
        }
        IWare ware = new Ware(resultList.get(0).get("Bezeichnung"), resultList.get(0).get("Beschreibung"), parseDouble(resultList.get(0).get("Preis")), stringToList(resultList.get(0).get("Besonderheiten")), stringToList(resultList.get(0).get("Maengel")));
        ware.setId(parseInt(resultList.get(0).get("ID")));
        return ware;
    }

    @Override
    public List<IWare> readAll() {
        String sql = "SELECT * FROM " + tableName;
        List<Map<String, String>> resultList = ConnectionManager.INSTANCE.executeQuerySQL(sql, new String[]{});
        ArrayList<IWare> returnList = new ArrayList<>();
        for (Map<String, String> m:resultList) {
            IWare ware = new Ware(m.get("Bezeichnung"), m.get("Beschreibung"), parseDouble(m.get("Preis")), stringToList(m.get("Besonderheiten")), stringToList(m.get("Maengel")));
            ware.setId(parseInt(m.get("ID")));
            returnList.add(ware);
        }
        return returnList;
    }

    @Override
    public void update(IWare objectToUpdate) {
        String bezeichnung = objectToUpdate.getBezeichnung();
        String beschreibung = objectToUpdate.getBeschreibung();
        double preis = objectToUpdate.getPreis();
        List<String> besonderheiten = objectToUpdate.getBesonderheiten();
        List<String> maengel = objectToUpdate.getMaengel();
        long id = objectToUpdate.getId();
        String sqlString = "REPLACE INTO " + tableName + " (ID, Bezeichnung, Beschreibung, Preis, Besonderheiten, Maengel) VALUES(?,?,?,?,?,?)";
        ConnectionManager.INSTANCE.executeSQL(sqlString, new String[]{Long.toString(id), bezeichnung, beschreibung, Double.toString(preis), listToString(besonderheiten), listToString(maengel)});
 }
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM " + tableName + " WHERE ID = ?";
        ConnectionManager.INSTANCE.executeSQL(sql, new String[]{String.valueOf(id)});
    }

    private String listToString (List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s:list) {
            sb.append(s).append(";");
        }
    return sb.toString();
    }

    private List<String> stringToList (String s) {
        return Arrays.stream(s.split(";")).toList();
    }
}
