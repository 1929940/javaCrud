package DataModels;

import javafx.beans.property.*;

import java.math.BigDecimal;

public class narzedziaData {

    public static narzedziaData containerNarzedzia;

    private final IntegerProperty Id_Narzedzie;

    private IntegerProperty Lp;
    private final StringProperty Kod;
    private final StringProperty Nazwa;
    private final StringProperty DataZak;
    private final StringProperty DataUtyl;
    private final DoubleProperty Cena;

    public narzedziaData(int id, String kod, String nazwa, String dataZak, double cena) {

        Lp = new SimpleIntegerProperty(0);
        Id_Narzedzie = new SimpleIntegerProperty(id);
        Kod = new SimpleStringProperty(kod);
        Nazwa = new SimpleStringProperty(nazwa);
        DataZak = new SimpleStringProperty(dataZak);
        DataUtyl = new SimpleStringProperty(null); // can this accept null?
        Cena = new SimpleDoubleProperty(cena);

    }

    public narzedziaData(int id, String kod, String nazwa, String dataZak, String dataUtyl, double cena) {

        Lp = new SimpleIntegerProperty(0);
        Id_Narzedzie = new SimpleIntegerProperty(id);
        Kod = new SimpleStringProperty(kod);
        Nazwa = new SimpleStringProperty(nazwa);
        DataZak = new SimpleStringProperty(dataZak);
        DataUtyl = new SimpleStringProperty(dataUtyl);
        Cena = new SimpleDoubleProperty(cena);
    }

    @Override
    public String toString(){
        String output = "narzedziaData: \n" +
                "kod: " + getKod() +
                "\nNazwa: " + getNazwa() +
                "\nData Zakupu: " + getDataZak() +
                "\nData Utylizacji: " +getDataUtyl() +
                "\nCena: " + getCena();

        return output;
    }

    public int getId_Narzedzie() {
        return Id_Narzedzie.get();
    }

    public IntegerProperty id_NarzedzieProperty() {
        return Id_Narzedzie;
    }

    public void setId_Narzedzie(int id_Narzedzie) {
        this.Id_Narzedzie.set(id_Narzedzie);
    }

    public int getLp() {
        return Lp.get();
    }

    public IntegerProperty lpProperty() {
        return Lp;
    }

    public void setLp(int lp) {
        this.Lp.set(lp);
    }

    public String getKod() {
        return Kod.get();
    }

    public StringProperty kodProperty() {
        return Kod;
    }

    public void setKod(String kod) {
        this.Kod.set(kod);
    }

    public String getNazwa() {
        return Nazwa.get();
    }

    public StringProperty nazwaProperty() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        this.Nazwa.set(nazwa);
    }

    public String getDataZak() {
        return DataZak.get();
    }

    public StringProperty dataZakProperty() {
        return DataZak;
    }

    public void setDataZak(String dataZak) {
        this.DataZak.set(dataZak);
    }

    public String getDataUtyl() {
        return DataUtyl.get();
    }

    public StringProperty dataUtylProperty() {
        return DataUtyl;
    }

    public void setDataUtyl(String dataUtyl) {
        this.DataUtyl.set(dataUtyl);
    }

    public double getCena() {
        return Cena.get();
    }

    public DoubleProperty cenaProperty() {
        return Cena;
    }

    public void setCena(double cena) {
        this.Cena.set(cena);
    }
}
