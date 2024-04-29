package Kaufvertrag.businessInterfaces;

public interface IAdresse {
    String getStrasse();
    void setStrasse(String n_strasse);
    String getHausNr();
    void setHausNr(String n_hausNr);
    String getPlz();
    void setPlz(String n_plz);
    String getOrt();
    void setOrt(String n_ort);
    String toString();
}
