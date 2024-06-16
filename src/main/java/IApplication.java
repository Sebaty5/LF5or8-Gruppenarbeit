import Kaufvertrag.dataLayer.dataAccessObjects.DaoException;

public interface IApplication
{
    void startApplication() throws DaoException;

    Long getID();

    String getString(String whatToGetTheStringFor, Class<?> classForTheString);
}