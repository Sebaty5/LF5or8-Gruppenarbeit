package Kaufvertrag.dataLayer.businessClasses;

import Kaufvertrag.businessInterfaces.IKaufvertrag;
import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;

public class Kaufvertrag implements IKaufvertrag {

    private IVertragspartner verkauefer;
    private IVertragspartner kauefer;
    private IWare ware;
    private String zahlungsmodalitaet;


    public Kaufvertrag(IVertragspartner kaeufer, IVertragspartner verkaeufer, IWare ware) {
        setKauefer(kaeufer);
        setVerkauefer(verkaeufer);
        setWare(ware);
    }

    @Override
    public IVertragspartner getVerkauefer() { return verkauefer; }
    @Override
    public void setVerkauefer(IVertragspartner n_verkauefer) { verkauefer = n_verkauefer; }

    @Override
    public IVertragspartner getKauefer() { return kauefer; }
    @Override
    public void setKauefer(IVertragspartner n_kauefer) { kauefer = n_kauefer; }

    @Override
    public IWare getWare() { return ware; }
    @Override
    public void setWare(IWare n_ware) { ware = n_ware; }

    @Override
    public String getZahlungsModalitaeten() { return zahlungsmodalitaet; }
    @Override
    public void setZahlungsmodalitaeten(String n_zahlungsmodalitaeten) { zahlungsmodalitaet = n_zahlungsmodalitaeten; }

    @Override
    public String toString() {
        return "Kaufvertrag:" +
                verkauefer +
                kauefer +
                ware +
                "Zahlungsmodalität: " + zahlungsmodalitaet;
    }
}
