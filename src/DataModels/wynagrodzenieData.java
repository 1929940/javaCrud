package DataModels;

import javafx.beans.property.*;

import java.math.BigDecimal;

public class wynagrodzenieData {

    public static wynagrodzenieData containerWynagrodzenie;

    private final IntegerProperty Id_umowa;
    private final IntegerProperty Id_pracownik;

    private IntegerProperty Lp;
    private final StringProperty Nazwisko;
    private final StringProperty Imie;
    private final StringProperty Stanowisko;
    private final StringProperty Umowa;
    private final StringProperty DataStart;
    private final StringProperty DataKoniec;
    private final DoubleProperty Stawka;
    private final DoubleProperty LiczbaGodzin;
    private final DoubleProperty Wyplata;
    private final StringProperty Przedmiot;

    // Use for creating new objects
    public wynagrodzenieData(int id_umowa, int id_pracownik, String nazwisko, String imie, String stanowisko, String umowa, String dataStart, double stawka, double liczbaGodzin, String przedmiot) {

        Lp = new SimpleIntegerProperty(0);

        Id_umowa = new SimpleIntegerProperty(id_umowa);
        Id_pracownik = new SimpleIntegerProperty(id_pracownik);

        Nazwisko = new SimpleStringProperty(nazwisko);
        Imie = new SimpleStringProperty(imie);
        Stanowisko = new SimpleStringProperty(stanowisko);
        Umowa = new SimpleStringProperty(umowa);
        DataStart = new SimpleStringProperty(dataStart);
        DataKoniec = new SimpleStringProperty("");
        Stawka = new SimpleDoubleProperty(stawka);
        LiczbaGodzin = new SimpleDoubleProperty(liczbaGodzin);
        Wyplata = new SimpleDoubleProperty(stawka * liczbaGodzin);
        Przedmiot = new SimpleStringProperty(przedmiot);
    }

    //Use for displaying or modifying
    public wynagrodzenieData(int id_umowa, int id_pracownik, String nazwisko, String imie, String stanowisko, String umowa, String dataStart, String dataKoniec, double stawka, double liczbaGodzin, double wyplata, String przedmiot) {

        Lp = new SimpleIntegerProperty(0);

        Id_umowa = new SimpleIntegerProperty(id_umowa);
        Id_pracownik = new SimpleIntegerProperty(id_pracownik);

        Nazwisko = new SimpleStringProperty(nazwisko);
        Imie = new SimpleStringProperty(imie);
        Stanowisko = new SimpleStringProperty(stanowisko);
        Umowa = new SimpleStringProperty(umowa);
        DataStart = new SimpleStringProperty(dataStart);
        DataKoniec = new SimpleStringProperty(dataKoniec);
        Stawka = new SimpleDoubleProperty(stawka);
        LiczbaGodzin = new SimpleDoubleProperty(liczbaGodzin);
        Wyplata = new SimpleDoubleProperty(wyplata);
        Przedmiot = new SimpleStringProperty(przedmiot);
    }

    @Override
    public String toString(){
        String output = "wynagrodzenie: " +
                "\nNazwisko: " + getNazwisko() +
                "\nImie: " + getImie() +
                "\nStanowisko: " + getStanowisko() +
                "\nUmowa: " + getUmowa() +
                "\nData Start: " + getDataStart() +
                "\nData Koniec: " + getDataKoniec() +
                "\nStawka: " + getStawka() +
                "\nLiczba Godzin: " + getLiczbaGodzin() +
                "\nWyplata: " + getLiczbaGodzin() +
                "\nPrzedmiot: " + getPrzedmiot();

        return output;
    }

    public int getId_umowa() {
        return Id_umowa.get();
    }

    public IntegerProperty id_umowaProperty() {
        return Id_umowa;
    }

    public void setId_umowa(int id_umowa) {
        this.Id_umowa.set(id_umowa);
    }

    public int getId_pracownik() {
        return Id_pracownik.get();
    }

    public IntegerProperty id_pracownikProperty() {
        return Id_pracownik;
    }

    public void setId_pracownik(int id_pracownik) {
        this.Id_pracownik.set(id_pracownik);
    }

    public String getUmowa() {
        return Umowa.get();
    }

    public StringProperty umowaProperty() {
        return Umowa;
    }

    public void setUmowa(String umowa) {
        this.Umowa.set(umowa);
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

    public String getStanowisko() {
        return Stanowisko.get();
    }

    public StringProperty stanowiskoProperty() {
        return Stanowisko;
    }

    public void setStanowisko(String stanowisko) {
        this.Stanowisko.set(stanowisko);
    }

    public String getDataStart() {
        return DataStart.get();
    }

    public StringProperty dataStartProperty() {
        return DataStart;
    }

    public void setDataStart(String dataStart) {
        this.DataStart.set(dataStart);
    }

    public String getDataKoniec() {
        return DataKoniec.get();
    }

    public StringProperty dataKoniecProperty() {
        return DataKoniec;
    }

    public void setDataKoniec(String dataKoniec) {
        this.DataKoniec.set(dataKoniec);
    }

    public double getStawka() {
        return Stawka.get();
    }

    public DoubleProperty stawkaProperty() {
        return Stawka;
    }

    public void setStawka(double stawka) {
        this.Stawka.set(stawka);
    }

    public double getLiczbaGodzin() {
        return LiczbaGodzin.get();
    }

    public DoubleProperty liczbaGodzinProperty() {
        return LiczbaGodzin;
    }

    public void setLiczbaGodzin(double liczbaGodzin) {
        this.LiczbaGodzin.set(liczbaGodzin);
    }

    public double getWyplata() {
        return Wyplata.get();
    }

    public DoubleProperty wyplataProperty() {
        return Wyplata;
    }

    public void setWyplata(double wyplata) {
        this.Wyplata.set(wyplata);
    }

    public String getPrzedmiot() {
        return Przedmiot.get();
    }

    public StringProperty przedmiotProperty() {
        return Przedmiot;
    }

    public void setPrzedmiot(String przedmiot) {
        this.Przedmiot.set(przedmiot);
    }
}
