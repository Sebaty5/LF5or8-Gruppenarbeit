package Kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.database.FieldType;
import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.database.Table;

import java.util.List;

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
        Table vertragsparterTable = tableBuilder.build();
        ConnectionManager.INSTANCE.createNewTable(vertragsparterTable);
    }

    @Override
    public IVertragspartner create() {

        return null; //TODO add return value
    }

    @Override
    public IVertragspartner create(IVertragspartner objectToInsert) {

        return null; //TODO add return value
    }

    @Override
    public IVertragspartner read(String id) {
        return null;
    }

    @Override
    public List<IVertragspartner> readAll() {
        return List.of();
    }

    @Override
    public void update(IVertragspartner objectToUpdate) {

    }

    @Override
    public void delete(String id) {

    }
}
