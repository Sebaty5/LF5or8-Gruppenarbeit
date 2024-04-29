package Kaufvertrag.dataLayer.businessClasses;

import Kaufvertrag.businessInterfaces.IKaufvertrag;
import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.businessInterfaces.IWare;

public class Kaufvertrag implements IKaufvertrag {
    @Override
    public IVertragspartner getVerkauefer() {
        return null;
    }

    @Override
    public void setVerkauefer(IVertragspartner n_verkauefer) {

    }

    @Override
    public IVertragspartner getKauefer() {
        return null;
    }

    @Override
    public void setKauefer(IVertragspartner n_kauefer) {

    }

    @Override
    public IWare getWare() {
        return null;
    }

    @Override
    public void setWare(IWare n_ware) {

    }

    @Override
    public String getZahlungsModalitaeten() {
        return "";
    }

    @Override
    public void setZahlungsmodalitaeten(String n_zahlungsmodalitaeten) {

    }
}
