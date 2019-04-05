package Main.Windows.dodajWynagrodzenie;

import DataModels.dropBoxPracownik;
import DataModels.narzedziaData;
import DataModels.wynagrodzenieData;
import Main.helpers;
import dbUtilities.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class dodajWynagrodzenieController implements Initializable {

    @FXML
    private Button dodajBtn;

    @FXML
    private ChoiceBox<dropBoxPracownik> choiceBox;

    @FXML
    private TextField StawkaTxt, GodzinyTxt, PrzedmiotTXT;

    @FXML
    private DatePicker StartDtp, KoniecDtp;

    public void dodaj(ActionEvent event){

        int id = choiceBox.getValue().getId();

        String StawkaStr = StawkaTxt.getText();
        String GodzStr = GodzinyTxt.getText();
        String Przedmiot = PrzedmiotTXT.getText();
        String DateStart = String.valueOf(StartDtp.getValue());
        String DateKoniec = String.valueOf(KoniecDtp.getValue());

        double Godz;
        double Stawka;

        if (StawkaStr.isEmpty() ||
                Przedmiot.isEmpty() ||
                GodzStr.isEmpty()
        ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Nie wszystkie pola wypelnione");
            alert.setHeaderText(null);
            alert.setContentText("Wypełnij wszystkie pola");

            alert.showAndWait();

            return;
        }
        if (helpers.isDouble(GodzStr) && helpers.isDouble(StawkaStr)){
            Godz = helpers.ConvertDouble(GodzStr);
            Stawka = helpers.ConvertDouble(StawkaStr);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Pola liczbowe");
            alert.setHeaderText(null);
            alert.setContentText("Upewnij się że w polach Stawka i Godziny znajdują sie cyfry");

            alert.showAndWait();

            return;
        }

        if ((StartDtp.getValue() == null) || (KoniecDtp.getValue() == null)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Pola Daty");
            alert.setHeaderText(null);
            alert.setContentText("Upewnij się że pola dat zostaly wybrane");

            alert.showAndWait();

            return;
        }
        DBconnection dBconnection = new DBconnection();

        // Generuje numer umowy na podstawie daty podpisania umowy

        String Year = DateStart.substring(0,4);
        String Month = DateStart.substring(5,7);

        String umowa = dBconnection.generujNrUmowy(Year,Month);

        wynagrodzenieData tmp = new wynagrodzenieData(id, umowa, DateStart, DateKoniec, Stawka, Godz, Przedmiot );

        dBconnection.dodajWynagrodzenieDB(tmp);
        dBconnection.closeConnection();

        CloseWindow();
    }

    private void CloseWindow(){
        try {
            Stage stage = (Stage)this.dodajBtn.getScene().getWindow();

            stage.fireEvent(
                    new WindowEvent(
                            stage,
                            WindowEvent.WINDOW_CLOSE_REQUEST
                    )
            );

        }
        catch (Exception locaException) {}
    }

    public void anuluj(ActionEvent event) {
        CloseWindow();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        StartDtp.setValue(LocalDate.now());

        choiceBox.setItems(dropBoxPracownik.containerList);
        choiceBox.setValue(dropBoxPracownik.containerList.get(0));



    }
}
