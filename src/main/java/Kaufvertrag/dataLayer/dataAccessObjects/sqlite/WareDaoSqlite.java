package Kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;

import java.util.List;

public class WareDaoSqlite implements IDao<IWare, Long> {
    private final String tableName = "vertragspartner";


    @Override
    public IWare create() {
        return null;
    }

    @Override
    public IWare create(IWare objectToInsert) {
        return null; //TODO add return value
    }

    @Override
    public IWare read(Long id) {
        return null;
    }

    @Override
    public List<IWare> readAll() {
        return List.of();
    }

    @Override
    public void update(IWare objectToUpdate) {

    }

    @Override
    public void delete(Long id) {

    }
}
