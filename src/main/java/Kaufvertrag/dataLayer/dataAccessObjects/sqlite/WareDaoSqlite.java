package Kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;

import java.util.List;

public class WareDaoSqlite implements IDao<IWare, Long> {


    @Override
    public IWare create() {
        return null;
    }

    @Override
    public void create(IWare objectToInsert) {

    }

    @Override
    public IWare read(Long id) {
        return null;
    }

    @Override
    public List<IWare> read() {
        return List.of();
    }

    @Override
    public void update(IWare objectToUpdate) {

    }

    @Override
    public void delete(Long id) {

    }
}
