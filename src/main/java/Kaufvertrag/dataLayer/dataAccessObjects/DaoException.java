package Kaufvertrag.dataLayer.dataAccessObjects;

public class DaoException extends Exception
{
    private final String message;

    public DaoException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }
}