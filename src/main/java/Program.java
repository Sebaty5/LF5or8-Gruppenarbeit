import Kaufvertrag.dataLayer.dataAccessObjects.DaoException;

public class Program
    {
        private static IApplication inputMethod;

        public static void main(String[] args) throws DaoException {
            inputMethod = inputMethod == null ? new ConsoleApplication() : inputMethod;

            inputMethod.startApplication();
        }
    }

