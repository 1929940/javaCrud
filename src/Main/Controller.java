package Main;

import DataModels.narzedziaData;
import DataModels.pracownikData;
import DataModels.wynagrodzenieData;
import DataModels.wypozyczeniaData;
import dbUtilities.DBconnection;
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

            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(true);
            popUp.maxHeightProperty().setValue(350);
            popUp.maxWidthProperty().setValue(200);
            popUp.minWidthProperty().setValue(150);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);

            popUp.show();

            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    pracownicy_zaladuj();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void pracownicy_zaladuj(){

        tabPrac_lp.setCellValueFactory(new PropertyValueFactory("Lp"));
        tabPrac_nazwisko.setCellValueFactory(new PropertyValueFactory("Nazwisko"));
        tabPrac_imie.setCellValueFactory(new PropertyValueFactory("Imie"));
        tabPrac_narodowosc.setCellValueFactory(new PropertyValueFactory("Narodowosc"));
        tabPrac_stanowisko.setCellValueFactory(new PropertyValueFactory("Stanowisko"));
        tabPrac_dataZatr.setCellValueFactory(new PropertyValueFactory("DataZatr"));
        tabPrac_dataWyp.setCellValueFactory(new PropertyValueFactory("DataWyp"));

        DBconnection dBconnection = new DBconnection();
        Table_Pracownik.setItems(dBconnection.getPracownicyDB());
        dBconnection.closeConnection();
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

            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(true);
            popUp.maxHeightProperty().setValue(350);
            popUp.maxWidthProperty().setValue(200);
            popUp.minWidthProperty().setValue(150);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);

            popUp.show();

            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    pracownicy_zaladuj();
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

            DBconnection dBconnection = new DBconnection();
            int tmp = dBconnection.znajdzPracownikaDB(selectedData.get(0));
            dBconnection.usunPracownikaDB(tmp);
            dBconnection.closeConnection();

            pracownicy_zaladuj();
            wynagrodzenie_zaladuj();
            wypozyczenia_zaladuj();
        }
    }


    // TAB: Wynagrodzenie

    @FXML
    private TableView<wynagrodzenieData> Table_Wynagrodzenie;

    @FXML
    private TableColumn<wynagrodzenieData, Integer> tabWynag_LP;

    @FXML
    private TableColumn<wynagrodzenieData, String> tabWynag_Nazw;

    @FXML
    private TableColumn<wynagrodzenieData, String> tabWynag_Imie;

    @FXML
    private TableColumn<wynagrodzenieData, String> tabWynag_Stanowisko;

    @FXML
    private TableColumn<wynagrodzenieData, String> tabWynag_Umowa;

    @FXML
    private TableColumn<wynagrodzenieData, String> tabWynag_DataStart;

    @FXML
    private TableColumn<wynagrodzenieData, String> tabWynag_DataKoniec;

    @FXML
    private TableColumn<wynagrodzenieData, Double> tabWynag_Stawka;

    @FXML
    private TableColumn<wynagrodzenieData, Double> tabWynag_Godz;

    @FXML
    private TableColumn<wynagrodzenieData, Double> tabWynag_Wyplata;

    @FXML
    private TableColumn<wynagrodzenieData, String> tabWynag_Przedmiot;

    public void wynagrodzenie_zaladuj(){

        tabWynag_LP.setCellValueFactory(new PropertyValueFactory("lp"));
        tabWynag_Nazw.setCellValueFactory(new PropertyValueFactory("Nazwisko"));
        tabWynag_Imie.setCellValueFactory(new PropertyValueFactory("Imie"));
        tabWynag_Stanowisko.setCellValueFactory(new PropertyValueFactory("Stanowisko"));
        tabWynag_Umowa.setCellValueFactory(new PropertyValueFactory("Umowa"));
        tabWynag_DataStart.setCellValueFactory(new PropertyValueFactory("DataStart"));
        tabWynag_DataKoniec.setCellValueFactory(new PropertyValueFactory("DataKoniec"));
        tabWynag_Stawka.setCellValueFactory(new PropertyValueFactory("Stawka"));
        tabWynag_Godz.setCellValueFactory(new PropertyValueFactory("LiczbaGodzin"));
        tabWynag_Wyplata.setCellValueFactory(new PropertyValueFactory("Wyplata"));
        tabWynag_Przedmiot.setCellValueFactory(new PropertyValueFactory("Przedmiot"));

        DBconnection dBconnection = new DBconnection();

        Table_Wynagrodzenie.setItems(dBconnection.getWynagrodzeniaDB());

        dBconnection.closeConnection();
    }

    public void wynagrodzenie_usun_click(){ // Untested
        ObservableList<wynagrodzenieData> selectedData;

        selectedData = Table_Wynagrodzenie.getSelectionModel().getSelectedItems();

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

            DBconnection dBconnection = new DBconnection();
            dBconnection.usunWynagrodzenieDB(selectedData.get(0).getId_umowa());
            dBconnection.closeConnection();

            wynagrodzenie_zaladuj();
        }

    }

    public void wynagrodzenie_dodaj_click(ActionEvent event){

        try{
            Stage popUp = new Stage();

            FXMLLoader loader = new FXMLLoader();
            Parent gui = loader.load(getClass().getResource("/Main/Windows/dodajWynagrodzenie/dodajWynagrodzenieFXML.fxml"));

            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(true);
            popUp.maxHeightProperty().setValue(550);
            popUp.maxWidthProperty().setValue(200);
            popUp.minWidthProperty().setValue(150);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);

            popUp.show();

            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    wynagrodzenie_zaladuj();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void wynagrodzenie_modyfikuj_click(ActionEvent event){

        try{
            ObservableList<wynagrodzenieData> selectedData;

            selectedData = Table_Wynagrodzenie.getSelectionModel().getSelectedItems();

            if (selectedData.get(0) == null){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Błąd: Nie wybrano wiersza");
                alert.setHeaderText(null);
                alert.setContentText("Bład: Wybierz wiersz, potem ponownie kliknij na przycisk modyfikuj");
                alert.showAndWait();

                return;
            }
            
            wynagrodzenieData.container = selectedData.get(0);

            Stage popUp = new Stage();

            FXMLLoader loader = new FXMLLoader();
            Parent gui = loader.load(getClass().getResource("/Main/Windows/modyfikujWynagrodzenie/modyfikujWynagrodzenieFXML.fxml"));

            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(true);
            popUp.maxHeightProperty().setValue(550);
            popUp.maxWidthProperty().setValue(200);
            popUp.minWidthProperty().setValue(150);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);

            popUp.show();

            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    wynagrodzenie_zaladuj();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // TAB: Spis Narzedzi

    @FXML
    private TableView<narzedziaData> Table_Narzedzia;

    @FXML
    private TableColumn<narzedziaData, Integer> tabNarz_LP;

    @FXML
    private TableColumn<narzedziaData, String> tabNarz_Kod;

    @FXML
    private TableColumn<narzedziaData, String> tabNarz_Nazwa;

    @FXML
    private TableColumn<narzedziaData, String> tabNarz_DataZak;

    @FXML
    private TableColumn<narzedziaData, String> tabNarz_DataUtyl;

    @FXML
    private TableColumn<narzedziaData, Double> tabNarz_Cena;

    public void narzedzia_zaladuj(){


        tabNarz_LP.setCellValueFactory(new PropertyValueFactory("lp"));
        tabNarz_Kod.setCellValueFactory(new PropertyValueFactory("Kod"));
        tabNarz_Nazwa.setCellValueFactory(new PropertyValueFactory("Nazwa"));
        tabNarz_DataZak.setCellValueFactory(new PropertyValueFactory("DataZak"));
        tabNarz_DataUtyl.setCellValueFactory(new PropertyValueFactory("DataUtyl"));
        tabNarz_Cena.setCellValueFactory(new PropertyValueFactory("Cena"));

        DBconnection dBconnection = new DBconnection();

        Table_Narzedzia.setItems(dBconnection.getNarzedziaDB());

        dBconnection.closeConnection();
    }

    public void narzedzia_usun_click(){ // Untested
        ObservableList<narzedziaData> selectedData;

        selectedData = Table_Narzedzia.getSelectionModel().getSelectedItems();

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

            DBconnection dBconnection = new DBconnection();
            dBconnection.usunNarzedzieDB(selectedData.get(0).getId_Narzedzie());
            dBconnection.closeConnection();

            narzedzia_zaladuj();
            wypozyczenia_zaladuj();
        }
    }

    public void narzedzia_dodaj_click(ActionEvent event){

        try{
            Stage popUp = new Stage();

            FXMLLoader loader = new FXMLLoader();
            Parent gui = loader.load(getClass().getResource("/Main/Windows/dodajNarzedzia/dodajNarzedziaFXML.fxml"));

            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(true);
            popUp.maxHeightProperty().setValue(350);
            popUp.maxWidthProperty().setValue(200);
            popUp.minWidthProperty().setValue(150);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);

            popUp.show();

            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    narzedzia_zaladuj();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    public void narzedzia_modyfikuj_click(ActionEvent event){

        try{
            ObservableList<narzedziaData> selectedData;

            selectedData = Table_Narzedzia.getSelectionModel().getSelectedItems();

            if (selectedData.get(0) == null){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Błąd: Nie wybrano wiersza");
                alert.setHeaderText(null);
                alert.setContentText("Bład: Wybierz wiersz, potem ponownie kliknij na przycisk modyfikuj");
                alert.showAndWait();

                return;
            }

            narzedziaData.container = selectedData.get(0);

            Stage popUp = new Stage();

            FXMLLoader loader = new FXMLLoader();
            Parent gui = loader.load(getClass().getResource("/Main/Windows/modyfikujNarzedzia/modyfikujNardzedziaFXML.fxml"));
            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(true);
            popUp.maxHeightProperty().setValue(350);
            popUp.maxWidthProperty().setValue(200);
            popUp.minWidthProperty().setValue(150);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);

            popUp.show();

            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    narzedzia_zaladuj();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }



    }

    // TAB: Wypozyczenia

    @FXML
    private TableView<wypozyczeniaData> Table_Wypozyczenia;

    @FXML
    private TableColumn<wypozyczeniaData, Integer> tabWyp_Lp;

    @FXML
    private TableColumn<wypozyczeniaData, String> tabWyp_Nazwisko;

    @FXML
    private TableColumn<wypozyczeniaData, String> tabWyp_Imie;

    @FXML
    private TableColumn<wypozyczeniaData, String> tabWyp_Stanowisko;

    @FXML
    private TableColumn<wypozyczeniaData, String> tabWyp_Kod;

    @FXML
    private TableColumn<wypozyczeniaData, String> tabWyp_Nazwa;

    @FXML
    private TableColumn<wypozyczeniaData, String> tabWyp_DataWyp;

    @FXML
    private TableColumn<wypozyczeniaData, String> tabWyp_DataZwrotu;

    @FXML
    private TableColumn<wypozyczeniaData, Double> tabWyp_Wartosc;


    public void wypozyczenia_zaladuj(){

        tabWyp_Lp.setCellValueFactory(new PropertyValueFactory("lp"));
        tabWyp_Nazwisko.setCellValueFactory(new PropertyValueFactory("Nazwisko"));
        tabWyp_Imie.setCellValueFactory(new PropertyValueFactory("Imie"));
        tabWyp_Stanowisko.setCellValueFactory(new PropertyValueFactory("Stanowisko"));
        tabWyp_Kod.setCellValueFactory(new PropertyValueFactory("Kod"));
        tabWyp_Nazwa.setCellValueFactory(new PropertyValueFactory("Nazwa"));
        tabWyp_DataWyp.setCellValueFactory(new PropertyValueFactory("DataWyp"));
        tabWyp_DataZwrotu.setCellValueFactory(new PropertyValueFactory("DataZwrotu"));
        tabWyp_Wartosc.setCellValueFactory(new PropertyValueFactory("Cena"));

        DBconnection dBconnection = new DBconnection();

        Table_Wypozyczenia.setItems(dBconnection.getWypozyczeniaDB());

        dBconnection.closeConnection();
    }

    public void wypozyczenia_usun_click(){ // Untested
        ObservableList<wypozyczeniaData> selectedData;

        selectedData = Table_Wypozyczenia.getSelectionModel().getSelectedItems();

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

            DBconnection dBconnection = new DBconnection();
            dBconnection.usunWypozyczenieDB(selectedData.get(0).getId_wypozyczenia());
            dBconnection.closeConnection();

            wypozyczenia_zaladuj();
        }
    }

    public void wypozyczenia_dodaj_click(ActionEvent event){

        try{
            Stage popUp = new Stage();

            FXMLLoader loader = new FXMLLoader();
            Parent gui = loader.load(getClass().getResource("/Main/Windows/dodajWypozyczenie/dodajWypozyczenieFXML.fxml"));

            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(true);
            popUp.maxHeightProperty().setValue(350);
            popUp.maxWidthProperty().setValue(200);
            popUp.minWidthProperty().setValue(150);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);

            popUp.show();

            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    wypozyczenia_zaladuj();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void wypozyczenia_modyfikuj_click(ActionEvent event){

        try{
            ObservableList<wypozyczeniaData> selectedData;

            selectedData = Table_Wypozyczenia.getSelectionModel().getSelectedItems();

            if (selectedData.get(0) == null){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Błąd: Nie wybrano wiersza");
                alert.setHeaderText(null);
                alert.setContentText("Bład: Wybierz wiersz, potem ponownie kliknij na przycisk modyfikuj");
                alert.showAndWait();

                return;
            }

            wypozyczeniaData.container = selectedData.get(0);

            Stage popUp = new Stage();

            FXMLLoader loader = new FXMLLoader();
            Parent gui = loader.load(getClass().getResource("/Main/Windows/modyfikujWypozyczenie/modyfikujWypozyczenieFXML.fxml"));

            Scene scene = new Scene(gui);

            popUp.setScene(scene);
            popUp.setResizable(true);
            popUp.maxHeightProperty().setValue(350);
            popUp.maxWidthProperty().setValue(200);
            popUp.minWidthProperty().setValue(150);
            popUp.initStyle(StageStyle.UTILITY);
            popUp.initModality(Modality.APPLICATION_MODAL);

            popUp.show();

            popUp.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent paramT){
                    wypozyczenia_zaladuj();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }



    }

    // Other

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Load and Seed PracownicyTab
        DBconnection dBconnection = new DBconnection();

        dBconnection.initilizeDB();

        dBconnection.getWypozyczeniaDB();


        if(dBconnection.IsSeedPracownik()){
            dBconnection.seedPracownik();
        }
        if(dBconnection.IsSeedNarzedzia()){
            dBconnection.seedNarzedzia();
        }
        if(dBconnection.IsSeedWynagrodzenia()){
            dBconnection.SeedWynagrodzenia();
        }
        if(dBconnection.IsSeedWypozyczenia()){
            dBconnection.SeedWypozyczenia();
        }

        dBconnection.closeConnection();

        pracownicy_zaladuj();
        wynagrodzenie_zaladuj();
        narzedzia_zaladuj();
        wypozyczenia_zaladuj();
    }
}
