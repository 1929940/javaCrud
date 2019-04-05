package Main.Windows.dodajWypozyczenie;

import DataModels.dropBoxNarzedzia;
import DataModels.dropBoxPracownik;
import DataModels.wypozyczeniaData;
import dbUtilities.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class dodajWypozyczenieController implements Initializable {

    @FXML
    private ChoiceBox<dropBoxNarzedzia> choiceBox_Narzedzie;

    @FXML
    private ChoiceBox<dropBoxPracownik> choiceBox_Pracownik;

    @FXML
    private DatePicker dataWypDtp;

    @FXML
    private Button anulujBtn;

    public void dodaj(ActionEvent event){

        int id_pracownik = choiceBox_Pracownik.getValue().getId();
        int id_narzedzia = choiceBox_Narzedzie.getValue().getId_narzedzia();


        if ((dataWypDtp.getValue() == null)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Pola Daty");
            alert.setHeaderText(null);
            alert.setContentText("Upewnij się że pola dat zostaly wybrane");

            alert.showAndWait();

            return;
        }

        String dataWyp = dataWypDtp.getValue().toString();

        DBconnection dBconnection = new DBconnection();

        wypozyczeniaData tmp = new wypozyczeniaData(id_pracownik, id_narzedzia, dataWyp);

        dBconnection.dodajWypozyczenieDB(tmp);
        dBconnection.closeConnection();

        CloseWindow();
    }

    private void CloseWindow(){
        try {
            Stage stage = (Stage)this.anulujBtn.getScene().getWindow();

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

        dataWypDtp.setValue(LocalDate.now());

        choiceBox_Narzedzie.setItems(dropBoxNarzedzia.containerList);
        choiceBox_Narzedzie.setValue(dropBoxNarzedzia.containerList.get(0));

        choiceBox_Pracownik.setItems(dropBoxPracownik.containerList);
        choiceBox_Pracownik.setValue(dropBoxPracownik.containerList.get(0));
        }

}
