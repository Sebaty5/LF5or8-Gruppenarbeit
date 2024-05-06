package Kaufvertrag.dataLayer.businessClasses;

import Kaufvertrag.businessInterfaces.IAdresse;
import Kaufvertrag.businessInterfaces.IVertragspartner;

public class Vertragspartner implements IVertragspartner {
    @Override
    public String getAusweisNr() {
        return "";
    }

    @Override
    public void setAusweisNr(String n_ausweisNr) {

    }

    @Override
    public String getVorname() {
        return "";
    }

    @Override
    public void setVorname(String n_vorname) {

    }

    @Override
    public String getNachname() {
        return "";
    }

    @Override
    public void setNachname(String n_nachname) {

    }

    @Override
    public IAdresse getAdresse() {
        return null;
    }

    @Override
    public void setAdresse(IAdresse n_adresse) {

    }
}
