package Main.Windows.dodajPracownika;

import DataModels.pracownikData;
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

public class dodajPracownikaController implements Initializable {

    @FXML
    private Button dodajPracownika_Anuluj;

    @FXML
    private TextField dodajPrac_Nazwisko, dodajPrac_Imie, dodajPrac_Narodowosc, dodajPrac_Stanowisko;

    @FXML
    private DatePicker dodajPrac_DataZatr;



    public void dodaj(ActionEvent event){

        String nazw = dodajPrac_Nazwisko.getText();
        String imie = dodajPrac_Imie.getText();
        String narod = dodajPrac_Narodowosc.getText();
        String stan = dodajPrac_Stanowisko.getText();
        String date = String.valueOf(dodajPrac_DataZatr.getValue());

        if (nazw.isEmpty() ||
                imie.isEmpty() ||
                narod.isEmpty() ||
                stan.isEmpty() ||
                dodajPrac_DataZatr.getValue() == null
        ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Nie wszystkie pola wypelnione");
            alert.setHeaderText(null);
            alert.setContentText("Wypełnij wszystkie pola");

            alert.showAndWait();

            return;
        }


        DBconnection dBconnection = new DBconnection();
        dBconnection.dodajPracownikaDB(new pracownikData(nazw,imie,narod,stan,date, null));
        dBconnection.closeConnection();

        anuluj(event);
    }

    public void anuluj(ActionEvent event){
        try {
            Stage stage = (Stage)this.dodajPracownika_Anuluj.getScene().getWindow();

            stage.fireEvent(
                    new WindowEvent(
                            stage,
                            WindowEvent.WINDOW_CLOSE_REQUEST
                    )
            );

        }
        catch (Exception locaException) {}
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dodajPrac_DataZatr.setValue(LocalDate.now());
    }


}
