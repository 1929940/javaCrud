package Main.Windows.dodajNarzedzia;

import DataModels.narzedziaData;
import Main.helpers;
import dbUtilities.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;



public class dodajNarzedziaController implements Initializable {

    @FXML
    private Button dodajBtn;

    @FXML
    private TextField NazwaTxt, KodTxt, CenaTxt;

    @FXML
    private DatePicker DateDtp;



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

    public void dodaj(ActionEvent event){

        String Nazwa = NazwaTxt.getText();
        String Kod = KodTxt.getText();
        String Cena = CenaTxt.getText();
        String Date = String.valueOf(DateDtp.getValue());

        double cenaTmp;

        if (Nazwa.isEmpty() ||
                Kod.isEmpty() ||
                Cena.isEmpty() ||
                DateDtp.getValue() == null
        ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Wymagane Pola nie zostaly wypelnione");
            alert.setHeaderText(null);
            alert.setContentText("Uzupelnij Wymagane Pola: Nazwa, Kod, Cena, Data Zakupu");

            alert.showAndWait();

            return;
        }
        if (helpers.isDouble(Cena)){
            cenaTmp = helpers.ConvertDouble(Cena);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Pole cena musi byc cyfra");
            alert.setHeaderText(null);
            alert.setContentText("Pole Cena musi byc cyfra");

            alert.showAndWait();

            return;
        }

        DBconnection dBconnection = new DBconnection();
        dBconnection.dodajNarzedzieDB(new narzedziaData(0, Kod, Nazwa, Date, null, cenaTmp));
        dBconnection.closeConnection();

        CloseWindow();
    }

    public void anuluj(ActionEvent event) {
    CloseWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateDtp.setValue(LocalDate.now());
    }
}
