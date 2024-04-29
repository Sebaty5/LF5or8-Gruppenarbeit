package Kaufvertrag.dataLayer.businessClasses;

import Kaufvertrag.businessInterfaces.IWare;

import java.util.List;

public class Ware implements IWare {
    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getBezeichnung() {
        return "";
    }

    @Override
    public void setBezeichnung(String n_bezeichnung) {

    }

    @Override
    public String getBeschreibung() {
        return "";
    }

    @Override
    public void setBeschreibung(String n_beschreibung) {

    }

    @Override
    public double getPreis() {
        return 0;
    }

    @Override
    public void setPreis(double n_preis) {

    }

    @Override
    public List<String> getBesonderheiten() {
        return List.of();
    }

    @Override
    public List<String> getMaengel() {
        return List.of();
    }
}
