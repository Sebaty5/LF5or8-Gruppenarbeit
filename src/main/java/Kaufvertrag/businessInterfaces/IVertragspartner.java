package Kaufvertrag.businessInterfaces;

public interface IVertragspartner {
    String getId();
    void setId(String n_id);
    String getAusweisNr();
    void setAusweisNr(String n_ausweisNr);
    String getVorname();
    void setVorname(String n_vorname);
    String getNachname();
    void setNachname(String n_nachname);
    IAdresse getAdresse();
    void setAdresse(IAdresse n_adresse);
}
