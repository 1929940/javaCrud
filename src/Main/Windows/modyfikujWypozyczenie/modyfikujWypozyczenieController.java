package Main.Windows.modyfikujWypozyczenie;

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

public class modyfikujWypozyczenieController implements Initializable {

    @FXML
    private ChoiceBox<dropBoxNarzedzia> choiceBox_Narzedzie;

    @FXML
    private ChoiceBox<dropBoxPracownik> choiceBox_Pracownik;

    @FXML
    private DatePicker dataWypDtp, dataZwrotDtp;

    @FXML
    private Button anulujBtn;

    public void modyfikuj(ActionEvent event){

        int id_pracownik = choiceBox_Pracownik.getValue().getId();
        int id_narzedzia = choiceBox_Narzedzie.getValue().getId_narzedzia();
        int id_wypozyczenia = wypozyczeniaData.container.getId_wypozyczenia();


        if (dataWypDtp.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Puste Pole Data Wypozyczenia");
            alert.setHeaderText(null);
            alert.setContentText("Upewnij się że pole Data Wypozyczenia jest uzupelnione");

            alert.showAndWait();

            return;
        }

        String dataWyp = dataWypDtp.getValue().toString();
        String dataZwrot = null;
        if (dataZwrotDtp.getValue() != null) {
            dataZwrot = dataZwrotDtp.getValue().toString();
        }



        DBconnection dBconnection = new DBconnection();

        wypozyczeniaData tmp = new wypozyczeniaData(id_wypozyczenia,id_pracownik, id_narzedzia, dataWyp, dataZwrot);

        dBconnection.modyfikujWypozyczenieDB(tmp);
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
        int index = 0;
        for (dropBoxNarzedzia dbn : dropBoxNarzedzia.containerList){
            if (dbn.getId_narzedzia() == wypozyczeniaData.container.getId_narzedzia()){
                index = dropBoxNarzedzia.containerList.indexOf(dbn);
            }
        }
        choiceBox_Narzedzie.setValue(dropBoxNarzedzia.containerList.get(index));



        choiceBox_Pracownik.setItems(dropBoxPracownik.containerList);
        index = 0;
        for (dropBoxPracownik dbp : dropBoxPracownik.containerList){
            if (dbp.getId() == wypozyczeniaData.container.getId_pracownik()){
                index = dropBoxPracownik.containerList.indexOf(dbp);
            }
        }
        choiceBox_Pracownik.setValue(dropBoxPracownik.containerList.get(index));

        try{
            dataWypDtp.setValue(LocalDate.parse(wypozyczeniaData.container.getDataWyp()));
        }
        catch (Exception e){

        }
        try{
            dataZwrotDtp.setValue(LocalDate.parse(wypozyczeniaData.container.getDataZwrotu()));
        }
        catch (Exception e){

        }

    }
}
