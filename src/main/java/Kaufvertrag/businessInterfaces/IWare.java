package Kaufvertrag.businessInterfaces;

import java.util.List;

public interface IWare {
    long getId();
    void setId(long id);
    String getBezeichnung();
    void setBezeichnung(String n_bezeichnung);
    String getBeschreibung();
    void setBeschreibung(String n_beschreibung);
    double getPreis();
    void setPreis(double n_preis);
    List<String> getBesonderheiten();
    List<String> getMaengel();
}
