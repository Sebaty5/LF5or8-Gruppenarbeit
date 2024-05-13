package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.dataLayer.businessClasses.Vertragspartner;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;

import java.util.List;

public class VertragspartnerDaoXML implements IDao<IVertragspartner, String> {

    @Override
    public IVertragspartner create() {
        return null;
    }

    @Override
    public void create(IVertragspartner objectToInsert) {

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
