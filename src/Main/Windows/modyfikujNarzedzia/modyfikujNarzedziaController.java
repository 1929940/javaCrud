package Main.Windows.modyfikujNarzedzia;

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

public class modyfikujNarzedziaController implements Initializable {

    @FXML
    private Button dodajBtn;

    @FXML
    private TextField NazwaTxt, KodTxt, CenaTxt;

    @FXML
    private DatePicker DateDtp, UtylDtp;

    public void modyfikuj(ActionEvent event){

        String Nazwa = NazwaTxt.getText();
        String Kod = KodTxt.getText();
        String Cena = CenaTxt.getText();
        String Date = String.valueOf(DateDtp.getValue());

        int id = narzedziaData.container.getId_Narzedzie();

        double cenaTmp;

        if (Nazwa.isEmpty() ||
                Kod.isEmpty() ||
                Cena.isEmpty()
        ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Nie wszystkie pola wypelnione");
            alert.setHeaderText(null);
            alert.setContentText("Wypełnij wszystkie pola");

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
        narzedziaData tmp;

        if (UtylDtp.getValue() == null){
            tmp = new narzedziaData(id, Kod, Nazwa, Date, null, cenaTmp);
        }
        else{
            String Utyl = UtylDtp.getValue().toString();
            tmp = new narzedziaData(id, Kod, Nazwa, Date, Utyl, cenaTmp);
        }

        DBconnection dBconnection = new DBconnection();
        dBconnection.modyfikujNarzedzieDB(tmp);
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

        NazwaTxt.setText(narzedziaData.container.getNazwa());
        KodTxt.setText(narzedziaData.container.getKod());

        CenaTxt.setText(Double.toString(narzedziaData.container.getCena()));

        // Dont know how to test for null, nor if its parsable

        try{
            DateDtp.setValue(LocalDate.parse(narzedziaData.container.getDataZak()));
        }
        catch (Exception e){

        }
        try{
            UtylDtp.setValue(LocalDate.parse(narzedziaData.container.getDataUtyl()));
        }
        catch (Exception e){

        }
    }
}

