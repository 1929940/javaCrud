package dbUtilities;

import DataModels.*;
import Main.helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBconnection {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:MK_database.db";

    private static Connection conn;
    private static Statement stmt;

    public DBconnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem połączenia");
            e.printStackTrace();
        }
    }

    public void initilizeDB(){

        // Pracownik

            //Create Statement
        String createPracownik="CREATE TABLE IF NOT EXISTS Pracownik(" +
                "id_pracownik INTEGER      PRIMARY KEY AUTOINCREMENT," +
                "nazwisko     VARCHAR (20) NOT NULL," +
                "imie         VARCHAR (20) NOT NULL," +
                "narodowosc   VARCHAR (20) NOT NULL," +
                "stanowisko   VARCHAR (20) NOT NULL," +
                "data_zatr    DATE         NOT NULL," +
                "data_wyp     DATE)";

            //Execute
        try {
            stmt.execute(createPracownik);

        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli: Pracownik");
            e.printStackTrace();
        }



        // Wynagrodzenie

            // Create Statement
        String createWynagrodzenie="CREATE TABLE IF NOT EXISTS Wynagrodzenia ("+
                "id_umowa         INTEGER        PRIMARY KEY AUTOINCREMENT,"+
                "id_pracownik     INTEGER        NOT NULL REFERENCES Pracownik (id_pracownik) ON DELETE CASCADE ON UPDATE CASCADE,"+
                "umowa            VARCHAR (11)   UNIQUE NOT NULL,"+
                "data_start       DATE           NOT NULL,"+
                "data_koniec      DATE           NOT NULL,"+
                "stawka_godzinowa DOUBLE (3, 2)  NOT NULL CHECK (stawka_godzinowa > 0),"+
                "godziny          DOUBLE (3, 2)  NOT NULL CHECK (godziny > 0),"+
                "wyplata          DECIMAL (5, 2) NOT NULL,"+
                "przedmiot_umowy  VARCHAR (100)  NOT NULL DEFAULT ('Ma pracowac'));";

            // Execute
        try {
            stmt.execute(createWynagrodzenie);

        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli: Wynagrodzenie");
            e.printStackTrace();
        }



        // Narzedzia

            // Create Statement
        String createNarzedzia="CREATE TABLE IF NOT EXISTS Narzedzia ("+
                "id_narzedzia    INTEGER        PRIMARY KEY AUTOINCREMENT,"+
                "nazwa           VARCHAR (25)   NOT NULL,"+
                "kod             VARCHAR (10)   NOT NULL,"+
                "data_zakupu     DATE           NOT NULL,"+
                "data_utylizacji DATE,"+
                "cena            DECIMAL (5, 2) NOT NULL CHECK (cena > 0));";

            // Execute
        try {
            stmt.execute(createNarzedzia);

        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli: Narzedzia");
            e.printStackTrace();
        }



        // Wypozyczenia

            //Prepapre Statement
        String createWypozyczenia="CREATE TABLE IF NOT EXISTS Wypozyczenia("+
                "id_wypozyczenia INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "id_narzedzia    INTEGER REFERENCES Narzedzia (id_narzedzia) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,"+
                "id_pracownik    INTEGER REFERENCES Pracownik (id_pracownik) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,"+
                "data_wyp        DATE    NOT NULL,"+
                "data_zwrot      DATE    );";

            //Execute
        try {
            stmt.execute(createWypozyczenia);

        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli: Wypozyczenia");
            e.printStackTrace();
        }
    };

    public void seedPracownik(){

        String sql;
        try {
            sql="INSERT INTO Pracownik(nazwisko, imie, narodowosc, stanowisko, data_zatr) " +
                    "VALUES ('Karczewski','Maciej','Polska','Koordynator','2015-09-01');";
            stmt.execute(sql);
            sql="INSERT INTO Pracownik(nazwisko, imie, narodowosc, stanowisko, data_zatr) " +
                    "VALUES ('Demchukov','Oleksandr','Ukraina','Spawacz','2014-12-01');";
            stmt.execute(sql);
            sql="INSERT INTO Pracownik(nazwisko, imie, narodowosc, stanowisko, data_zatr) " +
                    "VALUES ('Khvalin','Vasyl','Ukraina','Spawacz','2014-12-01');";
            stmt.execute(sql);
            // my own
        } catch (SQLException e) {
            System.err.println("Blad przy seedowaniu tabeli Pracownik");
            e.printStackTrace();
        }
    }

    public boolean IsSeedPracownik(){

        String sql = "SELECT COUNT(id_pracownik) AS Count FROM Pracownik";
        int tmp = 1;

        //execute query

        try {
            ResultSet result = stmt.executeQuery(sql);

            while(result.next()) {
                tmp = result.getInt("Count");}

        } catch (SQLException e) {
            System.err.println("Blad przy sprawdzaniu czy trzeba zaseedowac tabele pracownik");
            e.printStackTrace();
        }

        if (tmp > 0)
            return false;
        else
            return true;
    }

    public void seedNarzedzia(){

        String sql;
        try {
            sql="INSERT INTO Narzedzia(nazwa, kod, data_zakupu, cena) " +
                    "VALUES ('Młotek Spawalniczy','CR001/12','2014-01-01',19.99);";
            stmt.execute(sql);
            sql="INSERT INTO Narzedzia(nazwa, kod, data_zakupu, cena) " +
                    "VALUES ('Młotek Spawalniczy','CR002/12','2014-01-01',19.99);";
            stmt.execute(sql);
            sql="INSERT INTO Narzedzia(nazwa, kod, data_zakupu,data_utylizacji, cena) " +
                    "VALUES ('Szlifierka HEXO','CR001/10','2014-01-01','2015-03-07',1300.99);";
            stmt.execute(sql);
            sql="INSERT INTO Narzedzia(nazwa, kod, data_zakupu, cena) " +
                    "VALUES ('Pół Automat Spawalniczy','CR001/03','2014-01-01',7999.99);";
            stmt.execute(sql);
            sql="INSERT INTO Narzedzia(nazwa, kod, data_zakupu, cena) " +
                    "VALUES ('Pół Automat Spawalniczy','CR002/03','2014-01-01',7999.99);";
            stmt.execute(sql);
            sql="INSERT INTO Narzedzia(nazwa, kod, data_zakupu, cena) " +
                    "VALUES ('Pół Automat Spawalniczy','CR003/03','2014-01-01',7999.99);";
            stmt.execute(sql);
            sql="INSERT INTO Narzedzia(nazwa, kod, data_zakupu, cena) " +
                    "VALUES ('Podajnik Drutu','CR001/08','2014-01-01',4999.99);";
            stmt.execute(sql);


        } catch (SQLException e) {
            System.err.println("Blad przy seedowaniu tabeli Narzedzia");
            e.printStackTrace();
        }
    }

    public boolean IsSeedNarzedzia(){
        String sql = "SELECT COUNT(id_narzedzia) AS Count FROM Narzedzia";
        int tmp = 1;

        //execute query

        try {
            ResultSet result = stmt.executeQuery(sql);

            while(result.next()) {
                tmp = result.getInt("Count");}

        } catch (SQLException e) {
            System.err.println("Blad przy sprawdzaniu czy trzeba zaseedowac tabele narzedzia");
            e.printStackTrace();
        }

        if (tmp > 0)
            return false;
        else
            return true;
    }

    public void SeedWynagrodzenia(){

        String sql;
        try {
            sql="INSERT INTO Wynagrodzenia(id_pracownik, umowa, data_start, data_koniec, stawka_godzinowa, godziny, wyplata, przedmiot_umowy)"+
                    "VALUES(1, '2014/11/01', '2014-11-01','2014-11-30',15,200,3000,'Koordynacja prac spawalniczych na jednostce NB201');";
            stmt.execute(sql);
            sql="INSERT INTO Wynagrodzenia(id_pracownik, umowa, data_start, data_koniec, stawka_godzinowa, godziny, wyplata, przedmiot_umowy)"+
                    "VALUES(1, '2014/12/01', '2014-12-02','2014-12-27',16,200,3200,'Koordynacja prac monterskich na jednostce NB201');";
            stmt.execute(sql);
            sql="INSERT INTO Wynagrodzenia(id_pracownik, umowa, data_start, data_koniec, stawka_godzinowa, godziny, wyplata, przedmiot_umowy)"+
                    "VALUES(2, '2014/12/02', '2014-12-02','2014-12-27',25,200,5000,'Prace spawalnicze na jednostce NB201');";
            stmt.execute(sql);
            sql="INSERT INTO Wynagrodzenia(id_pracownik, umowa, data_start, data_koniec, stawka_godzinowa, godziny, wyplata, przedmiot_umowy)"+
                    "VALUES(3, '2014/12/03', '2014-12-02','2014-12-27',25,201,5025,'Prace spawalnicze na jednostce NB201');";
            stmt.execute(sql);
            sql="INSERT INTO Wynagrodzenia(id_pracownik, umowa, data_start, data_koniec, stawka_godzinowa, godziny, wyplata, przedmiot_umowy)"+
                    "VALUES(1, '2015/01/01', '2015-01-03','2015-01-27',16,200,3200,'Koordynacja prac spawalniczych na jednostce NB202');";
            stmt.execute(sql);
            sql="INSERT INTO Wynagrodzenia(id_pracownik, umowa, data_start, data_koniec, stawka_godzinowa, godziny, wyplata, przedmiot_umowy)"+
                    "VALUES(2, '2015/01/02', '2015-01-03','2015-01-27',26,200,5400,'Montaz barierer na jednostce NB23/1');";
            stmt.execute(sql);
            sql="INSERT INTO Wynagrodzenia(id_pracownik, umowa, data_start, data_koniec, stawka_godzinowa, godziny, wyplata, przedmiot_umowy)"+
                    "VALUES(3, '2015/01/03', '2015-01-03','2015-01-27',25,190,4750,'Spawanie wregow nr 11 do 23 na jednostce NB212/2');";
            stmt.execute(sql);

        } catch (SQLException e) {
            System.err.println("Blad przy seedowaniu tabeli Pracownik");
            e.printStackTrace();
        }
    }

    public boolean IsSeedWynagrodzenia(){
        String sql = "SELECT COUNT(id_umowa) AS Count FROM Wynagrodzenia";
        int tmp = 1;

        //execute query

        try {
            ResultSet result = stmt.executeQuery(sql);

            while(result.next()) {
                tmp = result.getInt("Count");}

        } catch (SQLException e) {
            System.err.println("Blad przy sprawdzaniu czy trzeba zaseedowac tabele wynagrodzenia");
            e.printStackTrace();
        }

        if (tmp > 0)
            return false;
        else
            return true;
    }

    public void SeedWypozyczenia(){

        String sql;
        try {
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (1,1,'2014-11-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (2,3,'2014-12-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (4,3,'2014-12-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp, data_zwrot) " +
                    "VALUES (3,3,'2014-12-03', '2014-12-10');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (5,2,'2014-12-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (7,2,'2014-12-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp, data_zwrot) " +
                    "VALUES (3,2,'2014-12-10','2014-12-24');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp, data_zwrot) " +
                    "VALUES (3,3,'2014-12-27','2015-03-07');";
            stmt.execute(sql);


        } catch (SQLException e) {
            System.err.println("Blad przy seedowaniu tabeli Narzedzia");
            e.printStackTrace();
        }
    }

    public boolean IsSeedWypozyczenia(){

        String sql = "SELECT COUNT(id_wypozyczenia) AS Count FROM Wypozyczenia";
        int tmp = 1;

        //execute query

        try {
            ResultSet result = stmt.executeQuery(sql);

            while(result.next()) {
                tmp = result.getInt("Count");}

        } catch (SQLException e) {
            System.err.println("Blad przy sprawdzaniu czy trzeba zaseedowac tabele wypozyczenia");
            e.printStackTrace();
        }

        if (tmp > 0)
            return false;
        else
            return true;
    }

    // Pracownik

    public ObservableList<pracownikData> getPracownicyDB(){

        ObservableList<pracownikData> output = FXCollections.observableArrayList();

        dropBoxPracownik.containerList.clear();
        dropBoxPracownik.containerListEmployeed.clear();

        int i = 1;

        //Find Data

        try {
            ResultSet result = stmt.executeQuery("SELECT * FROM Pracownik");
            String nazwisko, imie, narodowosc, stanowisko, data_zatr, data_wyp;
            int id;

            while(result.next()) {
                id = result.getInt("id_pracownik");
                nazwisko = result.getString("nazwisko");
                imie = result.getString("imie");
                narodowosc = result.getString("narodowosc");
                stanowisko = result.getString("stanowisko");
                data_zatr = result.getString("data_zatr"); // DATE -> to String
                data_wyp = result.getString("data_wyp"); // might turn out null

                pracownikData pd = new pracownikData(nazwisko, imie, narodowosc, stanowisko, data_zatr, data_wyp);
                pd.setLp(i++);

                dropBoxPracownik.containerList.add(new dropBoxPracownik(id,nazwisko,imie));
                if (data_wyp == null){
                    dropBoxPracownik.containerListEmployeed.add(new dropBoxPracownik(id,nazwisko,imie));
                }

                output.add(pd);
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wykonywaniu ladowaniu pracownikow");
            e.printStackTrace();
        }
        return output;
    }

    public void dodajPracownikaDB(pracownikData pd){

        String sql="INSERT INTO Pracownik(nazwisko, imie, narodowosc, stanowisko, data_zatr) " +
                "VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setString(1,pd.getNazwisko());
            stmtAdd.setString(2,pd.getImie());
            stmtAdd.setString(3,pd.getNarodowosc());
            stmtAdd.setString(4,pd.getStanowisko());
            //This might be troublesome
            stmtAdd.setString(5,pd.getDataZatr());

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu pracownika");
            e.printStackTrace();
        }
    }

    public int znajdzPracownikaDB(pracownikData pd){

        int output = 0;

        String sql="SELECT id_pracownik FROM Pracownik " +
                "WHERE (nazwisko = ? AND " +
                "imie = ? AND " +
                "narodowosc = ? AND " +
                "stanowisko = ? AND " +
                "data_zatr = ?)";

        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setString(1,pd.getNazwisko());
            stmtAdd.setString(2,pd.getImie());
            stmtAdd.setString(3,pd.getNarodowosc());
            stmtAdd.setString(4,pd.getStanowisko());
            stmtAdd.setString(5,pd.getDataZatr());

            ResultSet result = stmtAdd.executeQuery();

            output = result.getInt("id_pracownik");

        } catch (SQLException e) {
            System.err.println("Blad przy [szukaniu] osoby");
            e.printStackTrace();
        }
        return output;
    }

    public void usunPracownikaDB(int id_prac){

        String sql="DELETE FROM Pracownik WHERE id_pracownik = ?";

       try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setInt(1,id_prac);

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy [usuwaniu] osoby");
            e.printStackTrace();
        }
    }

    public void modyfikujPracownikaDB(pracownikData pd, pracownikData pdOld){

        // This aproach is obsolete, lookup another table for how it is properly done
        // Why is this even here? School projects have deadlines...
        int id_prac = znajdzPracownikaDB(pdOld);

        String sql = "UPDATE Pracownik " +
                "SET nazwisko = ?," +
                "imie = ?," +
                "narodowosc = ?," +
                "stanowisko = ?," +
                "data_zatr = ?," +
                "data_wyp = ?" +
                "WHERE id_pracownik = ?";

        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setString(1,pd.getNazwisko());
            stmtAdd.setString(2,pd.getImie());
            stmtAdd.setString(3,pd.getNarodowosc());
            stmtAdd.setString(4,pd.getStanowisko());
            //This might be troublesome
            stmtAdd.setString(5,pd.getDataZatr());
            stmtAdd.setString(6, pd.getDataWyp()); // This can be null right
            stmtAdd.setInt(7,id_prac);


            stmtAdd.execute();

            // my own
        } catch (SQLException e) {
            System.err.println("Blad przy [edytowaniu] osoby");
            e.printStackTrace();
        }
    }

    // Narzedzia

    public ObservableList<narzedziaData> getNarzedziaDB(){

        ObservableList<narzedziaData> output = FXCollections.observableArrayList();

        dropBoxNarzedzia.containerList.clear();
        dropBoxNarzedzia.containerListAvaliable.clear();

        int i = 1;

        //Find Data

        try {
            ResultSet result = stmt.executeQuery("SELECT * FROM Narzedzia");
            String nazwa, kod, data_zakupu, data_utylizacji;
            double cena;
            int id_narzedzia;

            while(result.next()) {
                id_narzedzia = result.getInt("id_narzedzia") ;
                nazwa = result.getString("nazwa");
                kod = result.getString("kod");
                data_zakupu = result.getString("data_zakupu");
                data_utylizacji = result.getString("data_utylizacji");
                cena = result.getDouble("cena");

                narzedziaData nd = new narzedziaData(id_narzedzia, kod, nazwa, data_zakupu, data_utylizacji, cena);
                nd.setLp(i++);

                dropBoxNarzedzia.containerList.add(new dropBoxNarzedzia(id_narzedzia, nazwa, kod));
                if (data_utylizacji == null){
                    dropBoxNarzedzia.containerListAvaliable.add(new dropBoxNarzedzia(id_narzedzia, nazwa, kod));
                }
                output.add(nd);
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wykonywaniu ladowaniu narzedzi");
            e.printStackTrace();
        }
        return output;
    }

    public void dodajNarzedzieDB(narzedziaData nd){

        String sql="INSERT INTO Narzedzia(nazwa, kod, data_zakupu, data_utylizacji, cena) " +
                "VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setString(1,nd.getNazwa());
            stmtAdd.setString(2,nd.getKod());
            stmtAdd.setString(3,nd.getDataZak());
            stmtAdd.setString(4,nd.getDataUtyl());
            stmtAdd.setDouble(5,nd.getCena());

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu Narzedzia");
            e.printStackTrace();
        }
    }

    public void usunNarzedzieDB(int id_prac){

        String sql="DELETE FROM Narzedzia WHERE id_narzedzia = ?";

        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setInt(1,id_prac);

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy [usuwaniu] narzedzia");
            e.printStackTrace();
        }
    }

    public void modyfikujNarzedzieDB(narzedziaData nd){

        String sql = "UPDATE Narzedzia SET nazwa = ?, kod = ?, data_zakupu = ?, data_utylizacji = ?, cena = ? WHERE id_narzedzia = ?";

        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setString(1,nd.getNazwa());
            stmtAdd.setString(2,nd.getKod());
            stmtAdd.setString(3,nd.getDataZak());
            stmtAdd.setString(4,nd.getDataUtyl());
            stmtAdd.setDouble(5,nd.getCena());
            stmtAdd.setInt(6,nd.getId_Narzedzie());

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy [edytowaniu] narzedzia");
            e.printStackTrace();
        }
    }

    // Wypozyczenia

    public ObservableList<wypozyczeniaData> getWypozyczeniaDB(){

        ObservableList<wypozyczeniaData> output = FXCollections.observableArrayList();

        int i = 1;

        try {

            String sql = "SELECT wy.*, pa.nazwisko, pa.imie, pa.stanowisko, na.kod, na.nazwa, na.cena FROM Wypozyczenia as wy INNER JOIN Pracownik as pa on pa.id_pracownik = wy.id_pracownik INNER JOIN Narzedzia as na on wy.id_narzedzia = na.id_narzedzia;";


            ResultSet result = stmt.executeQuery(sql);

            int id_wypozyczenia, id_narzedzia, id_pracownik;
            String nazwisko, imie, stanowisko, kod, nazwa, data_wyp, data_zwrot;
            double cena;

            while(result.next()) {
                id_wypozyczenia = result.getInt("id_wypozyczenia");
                id_narzedzia = result.getInt("id_narzedzia");
                id_pracownik = result.getInt("id_pracownik");

                nazwisko = result.getString("nazwisko");
                imie = result.getString("imie");
                stanowisko = result.getString("stanowisko");

                kod = result.getString("kod");
                nazwa = result.getString("nazwa");

                data_wyp = result.getString("data_wyp");
                data_zwrot = result.getString("data_zwrot");

                cena = result.getDouble("cena"); // DECIMAL -> BIGDECIMAL

                wypozyczeniaData wd = new wypozyczeniaData(id_wypozyczenia,id_narzedzia,id_pracownik,nazwisko,imie,stanowisko,kod,nazwa,data_wyp,data_zwrot,cena);
                wd.setLp(i++);

                if(data_zwrot == null || data_wyp == null){
                    dropBoxNarzedzia.NarzedziaWypozyczone.add(id_narzedzia);
                }

                if(data_zwrot != null){
                    dropBoxNarzedzia.NarzedziaWypozyczone.remove(id_narzedzia);
                }
                output.add(wd);
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wykonywaniu ladowaniu wypozyczenia");
            e.printStackTrace();
        }
        for (int value : dropBoxNarzedzia.NarzedziaWypozyczone){
            int index = -1;
            for(int j = 0; j < dropBoxNarzedzia.containerListAvaliable.size(); j++){
                if (value == dropBoxNarzedzia.containerListAvaliable.get(j).getId_narzedzia()){
                    index = j;
                }
            }
            if (index > 0){
                dropBoxNarzedzia.containerListAvaliable.remove(index);
            }
        }
        return output;
    }

    public void dodajWypozyczenieDB(wypozyczeniaData wd){

        String sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp, data_zwrot) " +
                "VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setInt(1,wd.getId_narzedzia());
            stmtAdd.setInt(2,wd.getId_pracownik());
            stmtAdd.setString(3,wd.getDataWyp());
            stmtAdd.setString(4,wd.getDataZwrotu());

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu Wypozyczenia");
            e.printStackTrace();
        }
    }

    public void usunWypozyczenieDB(int id_prac){

        String sql="DELETE FROM Wypozyczenia WHERE id_wypozyczenia = ?";

        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setInt(1,id_prac);

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy [usuwaniu] wypozyczenia");
            e.printStackTrace();
        }
    }

    public void modyfikujWypozyczenieDB(wypozyczeniaData wd){

        String sql = "UPDATE Wypozyczenia " +
                "SET id_narzedzia = ?," +
                "id_pracownik = ?," +
                "data_wyp = ?," +
                "data_zwrot = ?" +
                "WHERE id_wypozyczenia = ?";

        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setInt(1,wd.getId_narzedzia());
            stmtAdd.setInt(2,wd.getId_pracownik());
            stmtAdd.setString(3,wd.getDataWyp());
            stmtAdd.setString(4,wd.getDataZwrotu());
            stmtAdd.setInt(5,wd.getId_wypozyczenia());

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy [edytowaniu] wypozyczenia");
            e.printStackTrace();
        }
    }


    // Wynagrodzenie

    public ObservableList<wynagrodzenieData> getWynagrodzeniaDB(){

        ObservableList<wynagrodzenieData> output = FXCollections.observableArrayList();
        helpers.ZbiorNrUmow.clear();

        int i = 1;

        try {
            String sql = "SELECT wyn.*, pa.nazwisko, pa.imie, pa.stanowisko, pa.data_wyp FROM Wynagrodzenia as wyn INNER JOIN Pracownik as pa on pa.id_pracownik = wyn.id_pracownik;";

            ResultSet result = stmt.executeQuery(sql);

            int id_umowa, id_pracownik;
            String nazwisko, imie, stanowisko, umowa, data_start, data_koniec, przedmiot_umowy;
            double stawka, godziny, wyplata;

            while(result.next()) {
                id_umowa = result.getInt("id_umowa");
                id_pracownik = result.getInt("id_pracownik");

                nazwisko = result.getString("nazwisko");
                imie = result.getString("imie");
                stanowisko = result.getString("stanowisko");

                umowa = result.getString("umowa");
                data_start = result.getString("data_start");
                data_koniec = result.getString("data_koniec");
                przedmiot_umowy = result.getString("przedmiot_umowy");

                stawka = result.getDouble("stawka_godzinowa");
                godziny = result.getDouble("godziny");

                wyplata = result.getDouble("wyplata"); // BIGDECIMAL -> Is not supported by this driver -> I am not risking it

                wynagrodzenieData wnd = new wynagrodzenieData(id_umowa,id_pracownik,nazwisko,imie,stanowisko,umowa,data_start,data_koniec,stawka, godziny, wyplata, przedmiot_umowy);
                wnd.setLp(i++);

                helpers.ZbiorNrUmow.add(umowa);

                output.add(wnd);
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wykonywaniu ladowaniu wynagrodzenia");
            e.printStackTrace();
        }

        return output;
    }

    public void dodajWynagrodzenieDB(wynagrodzenieData wd){

        String sql="INSERT INTO Wynagrodzenia(id_pracownik, umowa, data_start, data_koniec, stawka_godzinowa, godziny, wyplata, przedmiot_umowy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setInt(1,wd.getId_pracownik());
            stmtAdd.setString(2,wd.getUmowa());
            stmtAdd.setString(3,wd.getDataStart());
            stmtAdd.setString(4,wd.getDataKoniec());
            stmtAdd.setDouble(5,wd.getStawka());
            stmtAdd.setDouble(6,wd.getLiczbaGodzin());
            stmtAdd.setDouble(7,wd.getWyplata()); // Decimal from DB gets transformed into Double
            stmtAdd.setString(8,wd.getPrzedmiot());

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu Wynagrodzenia");
            e.printStackTrace();
        }
    }

    public void usunWynagrodzenieDB(int id_prac){

        String sql="DELETE FROM Wynagrodzenia WHERE id_umowa = ?";

        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setInt(1,id_prac);

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy [usuwaniu] wynagrodzenia");
            e.printStackTrace();
        }
    }

    public void modyfikujWynagrodzenieDB(wynagrodzenieData wd){

        String sql = "UPDATE Wynagrodzenia " +
                "SET id_pracownik = ?," +
                "umowa = ?," +
                "data_start = ?," +
                "data_koniec = ?," +
                "stawka_godzinowa = ?,"+
                "godziny = ?,"+
                "wyplata = ?,"+
                "przedmiot_umowy = ?"+
                "WHERE id_umowa = ?";

        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setInt(1,wd.getId_pracownik());
            stmtAdd.setString(2,wd.getUmowa());
            stmtAdd.setString(3,wd.getDataStart());
            stmtAdd.setString(4,wd.getDataKoniec());
            stmtAdd.setDouble(5,wd.getStawka());
            stmtAdd.setDouble(6,wd.getLiczbaGodzin());
            stmtAdd.setDouble(7,wd.getWyplata()); // Decimal from DB gets transformed into Double
            stmtAdd.setString(8,wd.getPrzedmiot());
            stmtAdd.setInt(9,wd.getId_umowa());

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy [edytowaniu] wynagrodzenia");
            e.printStackTrace();
        }
    }

    public String generujNrUmowy(String rok, String miesiac){
        String output;
        String numer = "00";

        String sql = "SELECT COUNT(umowa) AS umowy FROM Wynagrodzenia WHERE umowa LIKE ('"+rok+"/"+miesiac+"/__');";

        try {
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            ResultSet result = stmtAdd.executeQuery();

            numer = result.getString("umowy");

            int tmp = Integer.valueOf(numer);
            tmp = tmp +1;
            numer = String.valueOf(tmp);

            if (numer.length() == 1){
                numer = "0" + numer;
            }

        } catch (SQLException e) {
            System.err.println("Blad przy generowaniu numeru umowy");
            e.printStackTrace();
        }

        return output = rok + "/" + miesiac + "/" + numer;
    }

    // Misc

    public void closeConnection(){
        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}

