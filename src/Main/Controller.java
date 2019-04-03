package Main;

import DataModels.pracownikData;
import Main.Windows.dodajPracownika.dodajPracownikaController;
import Main.Windows.modyfikujPracownika.modyfikujPracownikaController;
import dbUtilities.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static DataModels.dataLists.PracownicyDataList;

public class Controller implements Initializable {

    // TAB: Pracownicy


    //Table and Columns

    @FXML
    private TableView<pracownikData> Table_Pracownik;

    @FXML
    private TableColumn<pracownikData, Integer> tabPrac_lp;

    @FXML
    private TableColumn<pracownikData, String> tabPrac_nazwisko;

    @FXML
    private TableColumn<pracownikData, String> tabPrac_imie;

    @FXML
    private TableColumn<pracownikData, String> tabPrac_narodowosc;

    @FXML
    private TableColumn<pracownikData, String> tabPrac_stanowisko;

    @FXML
    private TableColumn<pracownikData, String> tabPrac_dataZatr;

    @FXML
    private TableColumn<pracownikData, String> tabPrac_dataWyp;

    public void pracownicy_dodaj_click(ActionEvent event){

        try{
            Stage popUp = new Stage();

            FXMLLoader loader = new FXMLLoader();
            Parent gui = loader.load(getClass().getResource("/Main/Windows/dodajPracownika/dodajPracownikaFXML.fxml"));


            // Dont think the following line is nessesary

            dodajPracownikaController dodajPracownika_ctrl = (dodajPracownikaController) loader.getController();
            //Parent gui = (Parent)loader.load(getClass().getResource("/Main/Windows/dodajPracownika/dodajPracownikaFXML.fxml"));

            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(false);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);

            popUp.show();

            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    pracownicy_loadData();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    public void pracownicy_loadData(){

        tabPrac_lp.setCellValueFactory(new PropertyValueFactory("Lp"));
        tabPrac_nazwisko.setCellValueFactory(new PropertyValueFactory("Nazwisko"));
        tabPrac_imie.setCellValueFactory(new PropertyValueFactory("Imie"));
        tabPrac_narodowosc.setCellValueFactory(new PropertyValueFactory("Narodowosc"));
        tabPrac_stanowisko.setCellValueFactory(new PropertyValueFactory("Stanowisko"));
        tabPrac_dataZatr.setCellValueFactory(new PropertyValueFactory("DataZatr"));
        tabPrac_dataWyp.setCellValueFactory(new PropertyValueFactory("DataWyp"));

        Table_Pracownik.setItems(new DBconnection().getPracownicyDB());
    }

    public void pracownicy_modyfikuj_click(ActionEvent event){

        try{
            ObservableList<pracownikData> selectedData;

            selectedData = Table_Pracownik.getSelectionModel().getSelectedItems();

            if (selectedData.get(0) == null){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Błąd: Nie wybrano wiersza");
                alert.setHeaderText(null);
                alert.setContentText("Bład: Wybierz wiersz, potem ponownie kliknij na przycisk modyfikuj");
                alert.showAndWait();


                return;
            }

            pracownikData.containerPracownik = selectedData.get(0);

            Stage popUp = new Stage();

            FXMLLoader loader = new FXMLLoader();
            Parent gui = loader.load(getClass().getResource("/Main/Windows/modyfikujPracownika/modyfikujPracownikaFXML.fxml"));


            // Dont think the following line is nessesary

            modyfikujPracownikaController modyfukujPracownika_ctrl = (modyfikujPracownikaController) loader.getController();
            //Parent gui = (Parent)loader.load(getClass().getResource("/Main/Windows/dodajPracownika/dodajPracownikaFXML.fxml"));

            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(false);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);





            popUp.show();



            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    pracownicy_loadData();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }



    }

    public void pracownicy_usun_click(){
        ObservableList<pracownikData> selectedData;

        selectedData = Table_Pracownik.getSelectionModel().getSelectedItems();

        if (selectedData.get(0) == null){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd: Nie wybrano wiersza");
            alert.setHeaderText(null);
            alert.setContentText("Bład: Wybierz wiersz, potem ponownie kliknij na przycisk usuń");
            alert.showAndWait();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy napewno chcesz usunąć ten wiersz?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
/*            PracownicyDataList.remove(selectedData.get(0));
            pracownicy_loadData();*/
            int tmp = new DBconnection().znajdzPracownika(selectedData.get(0));
            new DBconnection().usunPracownikaDB(tmp);

            pracownicy_loadData();
        }

        //Counter Issues [LP]
    }


    // TAB:

    // TAB:

    // TAB:


    // Other

    // Perhaps unnessesary
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Load and Seed PracownicyTab
        DBconnection dBconnection = new DBconnection();

        dBconnection.initilizeDB();

        if(dBconnection.IsSeedPracownik()){
            dBconnection.seedPracownik();
        }


        pracownicy_loadData();


    }
}
