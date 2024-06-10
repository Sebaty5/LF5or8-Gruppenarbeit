package Kaufvertrag.dataLayer.dataAccessObjects.XML;

import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Ware;
import Kaufvertrag.dataLayer.dataAccessObjects.IDao;

import java.util.ArrayList;
import java.util.List;

public class WareDaoXML implements IDao<IWare, Long> {
    @Override
    public IWare create() {
        // Hard coded for now. Inject variables with the content of the text boxes later.
        Ware testWare = new Ware("testWare", "Eine tolle testWare", 10, new ArrayList<>(), new ArrayList<>());
        ServiceXML.write(testWare);
        return testWare;
    }

    @Override
    public IWare create(IWare objectToInsert) {
        ServiceXML.write(objectToInsert);
        return null; //TODO add return value
    }

    @Override
    public IWare read(Long id) {
        return ServiceXML.read(id);
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
