package Kaufvertrag.dataLayer.businessClasses;

import Kaufvertrag.businessInterfaces.IWare;

import java.util.List;

public class Ware implements IWare {

    private long id;
    private String bezeichnung;
    private String beschreibung;
    private double preis;
    private final List<String> besonderheiten;
    private final List<String> maengel;

    public Ware(String n_bezeichnung, String n_beschreibung, double n_preis, List<String> n_besonderheiten, List<String> n_maengel)
    {
        bezeichnung = n_bezeichnung;
        beschreibung = n_beschreibung;
        preis = n_preis;
        besonderheiten = n_besonderheiten;
        maengel = n_maengel;
    }

    @Override
    public long getId() { return id; }
    public void setId(long id) { this.id = id; } // Brauchen wir den?

    @Override
    public String getBezeichnung() { return bezeichnung; }
    @Override
    public void setBezeichnung(String n_bezeichnung) { bezeichnung = n_bezeichnung; }

    @Override
    public String getBeschreibung() { return beschreibung; }
    @Override
    public void setBeschreibung(String n_beschreibung) { beschreibung = n_beschreibung; }

    @Override
    public double getPreis() { return preis; }
    @Override
    public void setPreis(double n_preis) { preis = n_preis; }

    @Override
    public List<String> getBesonderheiten() { return besonderheiten; }

    @Override
    public List<String> getMaengel() { return maengel; }

    @Override
    public String toString() {
        return "\nWare" +
                "\nId: " + id +
                "\nBezeichnung: " + bezeichnung +
                "\nBeschreibung: " + beschreibung +
                "\nPreis: " + preis +
                "\nBesonderheiten: " + besonderheiten +
                "\nMängel: " + maengel;
    }
}


