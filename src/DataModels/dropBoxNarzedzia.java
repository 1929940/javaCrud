package DataModels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class dropBoxNarzedzia {

    public static ObservableList<dropBoxNarzedzia> containerList = FXCollections.observableArrayList();

    private final int id_narzedzia;
    private final String nazwa;
    private final String kod;

    public dropBoxNarzedzia(int id_narzedzia, String nazwa, String kod) {
        this.id_narzedzia = id_narzedzia;
        this.nazwa = nazwa;
        this.kod = kod;
    }

    @Override
    public String toString() {
        return kod + " " + nazwa;
    }

    public int getId_narzedzia() {
        return id_narzedzia;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getKod() {
        return kod;
    }

    //stores: id_narzedzia, nazwa, kod
    //displays: nazwa, kod
    //returns: id_narzedzia
}
