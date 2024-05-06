package Kaufvertrag.businessInterfaces;

public interface IKaufvertrag {
    IVertragspartner getVerkauefer();
    void setVerkauefer(IVertragspartner n_verkauefer);
    IVertragspartner getKauefer();
    void setKauefer(IVertragspartner n_kauefer);
    IWare getWare();
    void setWare(IWare n_ware);
    String getZahlungsModalitaeten();
    void setZahlungsmodalitaeten(String n_zahlungsmodalitaeten);
}
