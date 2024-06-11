package Kaufvertrag.dataLayer.businessClasses;

import Kaufvertrag.businessInterfaces.IAdresse;
import Kaufvertrag.businessInterfaces.IVertragspartner;

public class Vertragspartner implements IVertragspartner {

    private int ID;
    private String ausweisNr;
    private String vorname;
    private String nachname;
    private IAdresse adresse;


    public Vertragspartner(String vorname, String nachname) {
        setVorname(vorname);
        setNachname(nachname);
    }

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public void setID(int ID)
    {
        this.ID = ID;
    }

    @Override
    public String getAusweisNr() {
        return ausweisNr;
    }

    @Override
    public void setAusweisNr(String n_ausweisNr) {
        ausweisNr = n_ausweisNr;
    }

    @Override
    public String getVorname() {
        return vorname;
    }

    @Override
    public void setVorname(String n_vorname) {
        vorname = n_vorname;
    }

    @Override
    public String getNachname() {
        return nachname;
    }

    @Override
    public void setNachname(String n_nachname) {
        nachname = n_nachname;
    }

    @Override
    public IAdresse getAdresse() {
        return adresse;
    }

    @Override
    public void setAdresse(IAdresse n_adresse) {
        adresse = n_adresse;
    }

    @Override
    public String toString() {
        return "\nVertragspartner" +
                "\nAusweisNr: " + ausweisNr +
                "\nVorname: " + vorname +
                "\nNachname: " + nachname +
                "\n" + adresse;
    }
}
