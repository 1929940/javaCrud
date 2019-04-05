package Main.Windows.modyfikujWynagrodzenie;

import DataModels.dropBoxPracownik;
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

public class modyfikujWynagrodzenieController implements Initializable {

    @FXML
    private Button modifyBtn;

    @FXML
    private ChoiceBox<dropBoxPracownik> choiceBox;

    @FXML
    private TextField wyplataTxt, stawkaTxt, godzinyTxt, umowaTxt ,przedmiotTxt;

    @FXML
    private DatePicker startDtp, koniecDtp;



    public void modifikuj (ActionEvent event){


        String Wyplata = wyplataTxt.getText();
        String Godziny = godzinyTxt.getText();
        String Stawka = stawkaTxt.getText();
        String Umowa = umowaTxt.getText();
        String Przedmiot = przedmiotTxt.getText();

        double WyplataD = 0;
        double GodzinyD = 0;
        double StawkaD = 0;

        int id_umowa = wynagrodzenieData.container.getId_umowa();
        int id_prac = choiceBox.getValue().getId();

        if ((Wyplata.trim().isEmpty()) ||
                (Godziny.trim().isEmpty()) ||
                (Stawka.trim().isEmpty()) ||
                (Umowa.trim().isEmpty()) ||
                (Przedmiot.trim().isEmpty()) ||
                (startDtp.getValue() == null) ||
                (koniecDtp.getValue() == null))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Nie wszystkie pola sa wypelnione");
            alert.setHeaderText(null);
            alert.setContentText("Upewnij się że wszystkie pola sa wypełnione");

            alert.showAndWait();

            return;
        }

        if (helpers.isDouble(Wyplata) && helpers.isDouble(Stawka) && helpers.isDouble(Godziny)){
            GodzinyD = helpers.ConvertDouble(Godziny);
            StawkaD = helpers.ConvertDouble(Stawka);
            WyplataD = helpers.ConvertDouble(Wyplata);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Pola liczbowe");
            alert.setHeaderText(null);
            alert.setContentText("Upewnij się że w polach Stawka i Godziny znajdują sie cyfry");

            alert.showAndWait();

            return;
        }

        String DateStart = startDtp.getValue().toString();
        String DateKoniec = koniecDtp.getValue().toString();

        //(int id_pracownik, int id_umowa, String umowa, String dataStart, String dataKoniec,
        // double stawka, double liczbaGodzin, double wyplata, String przedmiot)

        wynagrodzenieData tmp = new wynagrodzenieData(id_prac,id_umowa, Umowa, DateStart, DateKoniec, StawkaD, GodzinyD, WyplataD, Przedmiot);

        DBconnection dBconnection = new DBconnection();
        dBconnection.modyfikujWynagrodzenieDB(tmp);
        dBconnection.closeConnection();

        CloseWindow();

    }

    private void CloseWindow(){
        try {
            Stage stage = (Stage)this.modifyBtn.getScene().getWindow();

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

        choiceBox.setItems(dropBoxPracownik.containerList);

        int index = 0;
        for (dropBoxPracownik dbp : dropBoxPracownik.containerList){
            if (dbp.getId() == wynagrodzenieData.container.getId_pracownik()){
                index = dropBoxPracownik.containerList.indexOf(dbp);
            }
        }
        choiceBox.setValue(dropBoxPracownik.containerList.get(index));

        wyplataTxt.setText(String.valueOf(wynagrodzenieData.container.getWyplata()));
        godzinyTxt.setText(String.valueOf(wynagrodzenieData.container.getLiczbaGodzin()));
        stawkaTxt.setText(String.valueOf(wynagrodzenieData.container.getStawka()));
        umowaTxt.setText(String.valueOf(wynagrodzenieData.container.getUmowa()));
        przedmiotTxt.setText(String.valueOf(wynagrodzenieData.container.getPrzedmiot()));

        try{
            startDtp.setValue(LocalDate.parse(wynagrodzenieData.container.getDataStart()));
        }
        catch (Exception e){

        }
        try{
            koniecDtp.setValue(LocalDate.parse(wynagrodzenieData.container.getDataKoniec()));
        }
        catch (Exception e){

        }
    }
}
