package Main.Windows.modyfikujPracownika;

import DataModels.pracownikData;
import dbUtilities.DBconnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class modyfikujPracownikaController implements Initializable {

    //Lookup any different tab, this is a propotype, that I did not have the time to clean up.

    @FXML
    private Button anulujBtn;

    @FXML
    private TextField Nazwisko, Imie, Narodowosc, Stanowisko;

    @FXML
    private DatePicker DataZatr, DataWyp;

    pracownikData container;

    public void modyfikuj(){

        String nazwisko = Nazwisko.getText();
        String imie = Imie.getText();
        String narodowosc = Narodowosc.getText();
        String stanowisko = Stanowisko.getText();

        if (nazwisko.isEmpty() || imie.isEmpty() ||
        narodowosc.isEmpty() || stanowisko.isEmpty()
        || DataZatr.getValue() == null){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Nie wszystkie pola wypelniono");
            alert.setHeaderText(null);
            alert.setContentText("Bład: Upewnij się że wymienione pola są wypełnione: \n" +
                    "Nazwisko, Imie, Narodowosc, Stanowisko, Data Zatrudnienia");
            alert.showAndWait();

            return;
        }

        String dataZatr = DataZatr.getValue().toString();

        pracownikData tmp;

        if (DataWyp.getValue() == null){
            tmp = new pracownikData(nazwisko,imie,narodowosc,stanowisko,dataZatr);
        }
        else{
            String dataWyp = DataWyp.getValue().toString();
            tmp = new pracownikData(nazwisko,imie,narodowosc,stanowisko,dataZatr,dataWyp);
        }

        DBconnection dBconnection = new DBconnection();
        dBconnection.modyfikujPracownikaDB(tmp, container);
        dBconnection.closeConnection();

        anuluj();
    }

    public void anuluj(){
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


    private void initilizeForm(){
        // Fill the form with data

        container = pracownikData.containerPracownik;

        // null is acceptable

        Nazwisko.setText(container.getNazwisko());
        Imie.setText(container.getImie());
        Narodowosc.setText(container.getNarodowosc());
        Stanowisko.setText(container.getStanowisko());

        // Dont know how to test for null, nor if its parsable

        try{
            DataZatr.setValue(LocalDate.parse(container.getDataZatr()));
        }
        catch (Exception e){

        }
        try{
            DataWyp.setValue(LocalDate.parse(container.getDataWyp()));
        }
        catch (Exception e){

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initilizeForm();

        pracownikData.containerPracownik = null;
    }
}
