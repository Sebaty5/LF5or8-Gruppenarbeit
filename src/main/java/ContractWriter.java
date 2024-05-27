
import Kaufvertrag.businessInterfaces.IWare;
import Kaufvertrag.dataLayer.businessClasses.Adresse;
import Kaufvertrag.dataLayer.businessClasses.Kaufvertrag;
import Kaufvertrag.dataLayer.businessClasses.Vertragspartner;
import Kaufvertrag.dataLayer.businessClasses.Ware;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractWriter {
    static String csvFile = "src/main/resources/Kaufvertrag.csv";
    static String txtFile = "src/main/resources/Kaufvertrag.txt";

    /**
     * creates a writer object to write the Kaufvertrag.txt file
     */
    public static void main(String[] args) {

        try {
            PrintWriter writer = new PrintWriter(txtFile);

            // Schreibe die Daten des Kaufvertrags in die Datei
            writer.println("Kaufvertrag");
            writer.println("Datum: " + LocalDate.now());

            writer.println(createContract());

            writer.println("Preis: [Preis]");
            // Weitere Daten des Kaufvertrags können hier ergänzt werden

            // Schließe den PrintWriter, um die Datei zu schreiben
            writer.close();

            System.out.println("Kaufvertrag wurde erfolgreich in die Datei Kaufvertrag.txt geschrieben.");
        } catch (FileNotFoundException e) {
            System.out.println("Datei konnte nicht gefunden oder erstellt werden.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static Kaufvertrag createContract() throws IOException {
        List<Vertragspartner> parties = readFromCsv(csvFile);
        return new Kaufvertrag(parties.get(0), parties.get(1), new Ware("test", "test", 20, null, null));
    }

    private static List<Vertragspartner> readFromCsv(String filePath) throws IOException {
        List<Vertragspartner> parties = new ArrayList<>();
        List<Ware> products = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");
            String street = parts[2];
            String houseNr = parts[3];
            String postalCode = parts[4];
            String location = parts[5];
            Adresse address = new Adresse(street, houseNr, postalCode, location);
            if (parts[1].contains(",")) {
                String[] name = parts[1].split(",");
                Vertragspartner party = new Vertragspartner(name[1], name[0]);
                party.setAdresse(address);
                parties.add(party);
            } else {
                String name = parts[1];
                Ware ware = new Ware("test", "test", 20, null, null);
                products.add(ware);
            }
        }
        return parties;
    }

}
