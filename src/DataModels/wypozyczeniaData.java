package DataModels;

import javafx.beans.property.*;


public class wypozyczeniaData {

    public static wypozyczeniaData container;

    private final IntegerProperty Id_wypozyczenia;
    private final IntegerProperty Id_narzedzia;
    private final IntegerProperty Id_pracownik;

    private IntegerProperty Lp;
    private final StringProperty Nazwisko;
    private final StringProperty Imie;
    private final StringProperty Stanowisko;
    private final StringProperty Kod;
    private final StringProperty Nazwa;
    private final StringProperty DataWyp;
    private final StringProperty DataZwrotu;
    private final DoubleProperty Cena;

    // Used to add
    public wypozyczeniaData(int id_pracownik, int id_narzedzia, String dataWyp){

        Lp = new SimpleIntegerProperty(0);

        Id_wypozyczenia = new SimpleIntegerProperty(777); // irrelevant DB will set it
        Id_narzedzia = new SimpleIntegerProperty(id_narzedzia);
        Id_pracownik = new SimpleIntegerProperty(id_pracownik);
        Nazwisko = new SimpleStringProperty(null);
        Imie = new SimpleStringProperty(null);
        Stanowisko = new SimpleStringProperty(null);
        Kod = new SimpleStringProperty(null);
        Nazwa = new SimpleStringProperty(null);
        DataWyp = new SimpleStringProperty(dataWyp);
        DataZwrotu = new SimpleStringProperty(null);
        Cena = new SimpleDoubleProperty(0);
    }

    public wypozyczeniaData(int id_wypozyczenia,int id_pracownik, int id_narzedzia, String dataWyp, String dataZwrot){

        Lp = new SimpleIntegerProperty(0);

        Id_wypozyczenia = new SimpleIntegerProperty(id_wypozyczenia); // irrelevant DB will set it
        Id_narzedzia = new SimpleIntegerProperty(id_narzedzia);
        Id_pracownik = new SimpleIntegerProperty(id_pracownik);
        Nazwisko = new SimpleStringProperty(null);
        Imie = new SimpleStringProperty(null);
        Stanowisko = new SimpleStringProperty(null);
        Kod = new SimpleStringProperty(null);
        Nazwa = new SimpleStringProperty(null);
        DataWyp = new SimpleStringProperty(dataWyp);
        DataZwrotu = new SimpleStringProperty(dataZwrot);
        Cena = new SimpleDoubleProperty(0);
    }

    // Used to display data in the table
    public wypozyczeniaData(int id_wypozyczenia, int id_narzedzia, int id_pracownik,
                            String nazwisko, String imie, String stanowisko,
                            String kod, String nazwa,
                            String dataWyp, String dataZwrotu, double cena) {

        Lp = new SimpleIntegerProperty(0);

        Id_wypozyczenia = new SimpleIntegerProperty(id_wypozyczenia);
        Id_narzedzia = new SimpleIntegerProperty(id_narzedzia);
        Id_pracownik = new SimpleIntegerProperty(id_pracownik);
        Nazwisko = new SimpleStringProperty(nazwisko);
        Imie = new SimpleStringProperty(imie);
        Stanowisko = new SimpleStringProperty(stanowisko);
        Kod = new SimpleStringProperty(kod);
        Nazwa = new SimpleStringProperty(nazwa);
        DataWyp = new SimpleStringProperty(dataWyp);
        DataZwrotu = new SimpleStringProperty(dataZwrotu);
        Cena = new SimpleDoubleProperty(cena);
    }

    @Override
    public String toString() {
        return "wypozyczeniaData{" +
                "Nazwisko=" + Nazwisko +
                ", Imie=" + Imie +
                ", Stanowisko=" + Stanowisko +
                ", Kod=" + Kod +
                ", Nazwa=" + Nazwa +
                '}';
    }

    public int getId_wypozyczenia() {
        return Id_wypozyczenia.get();
    }

    public IntegerProperty id_wypozyczeniaProperty() {
        return Id_wypozyczenia;
    }

    public void setId_wypozyczenia(int id_wypozyczenia) {
        this.Id_wypozyczenia.set(id_wypozyczenia);
    }

    public int getId_narzedzia() {
        return Id_narzedzia.get();
    }

    public IntegerProperty id_narzedziaProperty() {
        return Id_narzedzia;
    }

    public void setId_narzedzia(int id_narzedzia) {
        this.Id_narzedzia.set(id_narzedzia);
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

    public String getDataWyp() {
        return DataWyp.get();
    }

    public StringProperty dataWypProperty() {
        return DataWyp;
    }

    public void setDataWyp(String dataWyp) {
        this.DataWyp.set(dataWyp);
    }

    public String getDataZwrotu() {
        return DataZwrotu.get();
    }

    public StringProperty dataZwrotuProperty() {
        return DataZwrotu;
    }

    public void setDataZwrotu(String dataZwrotu) {
        this.DataZwrotu.set(dataZwrotu);
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
