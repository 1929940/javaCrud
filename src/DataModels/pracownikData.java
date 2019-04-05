package DataModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class pracownikData {

    public static pracownikData containerPracownik;

    private IntegerProperty Lp;
    private final StringProperty Nazwisko;
    private final StringProperty Imie;
    private final StringProperty Narodowosc;
    private final StringProperty Stanowisko;
    private final StringProperty DataZatr;
    private final StringProperty DataWyp;

    public pracownikData(String nazwisko, String imie, String narodowosc, String stanowisko, String dataZatr){

        Lp = new SimpleIntegerProperty(0);
        Nazwisko = new SimpleStringProperty(nazwisko);
        Imie = new SimpleStringProperty(imie);
        Narodowosc = new SimpleStringProperty(narodowosc);
        Stanowisko = new SimpleStringProperty(stanowisko);
        DataZatr = new SimpleStringProperty(dataZatr);
        DataWyp = new SimpleStringProperty(null);
    }
    public pracownikData(String nazwisko, String imie, String narodowosc, String stanowisko, String dataZatr, String dataWyp){

        Lp = new SimpleIntegerProperty((0));
        Nazwisko = new SimpleStringProperty(nazwisko);
        Imie = new SimpleStringProperty(imie);
        Narodowosc = new SimpleStringProperty(narodowosc);
        Stanowisko = new SimpleStringProperty(stanowisko);
        DataZatr = new SimpleStringProperty(dataZatr);
        DataWyp = new SimpleStringProperty(dataWyp);
    }

    @Override
    public String toString(){
        String output = "PracownikData: \n" +
                "Nazwisko: " + getNazwisko() +
                "\nImie: " + getImie() +
                "\nNarodowosc: " +getNarodowosc() +
                "\nStanowisko: " +getStanowisko() +
                "\nData Zatr: " +getDataZatr() +
                "\nData Wyp: " +getDataWyp() + "\n";

        return output;
    }

    public Integer getLp() {
        return Lp.get();
    }

    public IntegerProperty lpProperty() {
        return Lp;
    }

    public void setLp(Integer lp) {
        this.Lp.set(lp);
    }

    public String getNazwisko() {
        return Nazwisko.get();
    }

    public StringProperty nazwiskoProperty() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.Nazwisko.set(nazwisko);
    }

    public String getImie() {
        return Imie.get();
    }

    public StringProperty imieProperty() {
        return Imie;
    }

    public void setImie(String imie) {
        this.Imie.set(imie);
    }

    public String getNarodowosc() {
        return Narodowosc.get();
    }

    public StringProperty narodowoscProperty() {
        return Narodowosc;
    }

    public void setNarodowosc(String narodowosc) {
        this.Narodowosc.set(narodowosc);
    }

    public String getStanowisko() {
        return Stanowisko.get();
    }

    public StringProperty stanowiskoProperty() {
        return Stanowisko;
    }

    public void setStanowisko(String stanowisko) {
        this.Stanowisko.set(stanowisko);
    }

    public String getDataZatr() {
        return DataZatr.get();
    }

    public StringProperty dataZatrProperty() {
        return DataZatr;
    }

    public void setDataZatr(String dataZatr) {
        this.DataZatr.set(dataZatr);
    }

    public String getDataWyp() {
        return DataWyp.get();
    }

    public StringProperty dataWypProperty() {
        return DataWyp;
    }

    public void setDataWyp(String dataWyp) {
        this.DataWyp.set(dataWyp);
    }
}
