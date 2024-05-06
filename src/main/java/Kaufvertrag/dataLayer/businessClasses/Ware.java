package Kaufvertrag.dataLayer.businessClasses;

import Kaufvertrag.businessInterfaces.IWare;

import java.util.List;

public class Ware implements IWare {

    private long id;
    private String bezeichnung;
    private String beschreibung;
    private double preis;
    private List<String> besonderheiten;
    private List<String> maengel;


    @Override
    public long getId() { return id; }

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

    public Ware(String bezeichnung, double preis)
    {
        setBezeichnung(bezeichnung);
        setPreis(preis);
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Ware{" +
                "id=" + id +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", preis=" + preis +
                ", besonderheiten=" + besonderheiten +
                ", maengel=" + maengel +
                '}';
    }
}


