package DataModels;

import javafx.beans.property.*;

public class narzedziaData {

    public static narzedziaData containerNarzedzia;

    private IntegerProperty Lp;
    private final IntegerProperty Id;
    private final StringProperty Nazwa;
    private final StringProperty DataZak;
    private final StringProperty DataUtyl;
    private final DoubleProperty Cena;

    public narzedziaData(int id, String nazwa, String dataZak, double cena) {
        Id = new SimpleIntegerProperty(id);
        Nazwa = new SimpleStringProperty(nazwa);
        DataZak = new SimpleStringProperty(dataZak);
        DataUtyl = new SimpleStringProperty("");
        Cena = new SimpleDoubleProperty(cena);

    }

    public narzedziaData(int id, String nazwa, String dataZak, String dataUtyl, double cena) {
        Id = new SimpleIntegerProperty(id);
        Nazwa = new SimpleStringProperty(nazwa);
        DataZak = new SimpleStringProperty(dataZak);
        DataUtyl = new SimpleStringProperty(dataUtyl);
        Cena = new SimpleDoubleProperty(cena);
    }

    @Override
    public String toString(){
        String output = "narzedziaData: \n" +
                "id: " + getId() +
                "\nNazwa: " + getNazwa() +
                "\nData Zakupu: " + getDataZak() +
                "\nData Utylizacji: " +getDataUtyl() +
                "\nCena: " + getCena();

        return output;
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

    public int getId() {
        return Id.get();
    }

    public IntegerProperty idProperty() {
        return Id;
    }

    public void setId(int id) {
        this.Id.set(id);
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
