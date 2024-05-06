package Kaufvertrag.dataLayer.businessClasses;

import Kaufvertrag.businessInterfaces.IAdresse;

public class Adresse implements IAdresse {

    private String strasse;
    private String hausNr;
    private String plz;
    private String ort;


    @Override
    public String getStrasse() { return strasse; }
    @Override
    public void setStrasse(String n_strasse) { strasse = n_strasse; }

    @Override
    public String getHausNr() { return hausNr; }
    @Override
    public void setHausNr(String n_hausNr) { hausNr = n_hausNr; }

    @Override
    public String getPlz() { return plz; }
    @Override
    public void setPlz(String n_plz) { plz = n_plz; }

    @Override
    public String getOrt() { return ort; }
    @Override
    public void setOrt(String n_ort) { ort = n_ort; }

    public Adresse(String strasse, String hausNr, String plz, String ort)
    {
        this.strasse = strasse;
        this.hausNr = hausNr;
        this.plz = plz;
        this.ort = ort;
    }

    @Override
    public String toString()
    {
        //TODO needs second look
        return "Adresse{" +
                "strasse='" + strasse + '\'' +
                ", hausNr='" + hausNr + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                '}';
    }
}
