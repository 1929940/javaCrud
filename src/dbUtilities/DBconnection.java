package dbUtilities;

import DataModels.pracownikData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
;

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
    }

    public void initilizeDB(){

        // Pracownik

        //Connect

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem połączenia");
            e.printStackTrace();
        }

        //Create Statement

        String createPracownik="CREATE TABLE IF NOT EXISTS Pracownik(" +
                "    id_pracownik INTEGER      PRIMARY KEY AUTOINCREMENT," +
                "    nazwisko     VARCHAR (20) NOT NULL," +
                "    imie         VARCHAR (20) NOT NULL," +
                "    narodowosc   VARCHAR (20) NOT NULL," +
                "    stanowisko   VARCHAR (20) NOT NULL," +
                "    data_zatr    DATE         NOT NULL," +
                "    data_wyp     DATE)";

        //Execute

        try {
            stmt.execute(createPracownik);

        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
        }

    };

    public void seedPracownik(){

        //Connect

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem połączenia");
            e.printStackTrace();
        }


        //Populate with test data

        String sql;
        try {
            sql="INSERT INTO Pracownik(nazwisko, imie, narodowosc, stanowisko, data_zatr) " +
                    "VALUES ('Karczewski','Maciej','Polska','Koordynator','2015-09-01');";
            stmt.execute(sql);
            sql="INSERT INTO Pracownik(nazwisko, imie, narodowosc, stanowisko, data_zatr) " +
                    "VALUES ('Demchuk','Vasyl','Ukraina','Spawacz','2014-12-01');";
            stmt.execute(sql);
            sql="INSERT INTO Pracownik(nazwisko, imie, narodowosc, stanowisko, data_zatr) " +
                    "VALUES ('Khvalin','Oleksandr','Ukraina','Spawacz','2014-12-01');";
            stmt.execute(sql);

            // my own
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu osoby");
            e.printStackTrace();
        }

        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }


    }

    public boolean IsSeedPracownik(){

        String sql = "SELECT COUNT(id_pracownik) AS Count FROM Pracownik";
        int tmp = 1;

        //connect

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem połączenia");
            e.printStackTrace();
        }

        //execute query

        try {
            ResultSet result = stmt.executeQuery(sql);

            while(result.next()) {
                tmp = result.getInt("Count");}



        } catch (SQLException e) {
            System.err.println("Blad przy wykonywaniu SELECT");
            e.printStackTrace();
        }
        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        if (tmp > 0)
            return false;
        else
            return true;


    }

    public ObservableList<pracownikData> getPracownicyDB(){

        ObservableList<pracownikData> output = FXCollections.observableArrayList();

        int i = 1;

        //Connect

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem połączenia");
            e.printStackTrace();
        }

        //Find Data

        try {
            ResultSet result = stmt.executeQuery("SELECT * FROM Pracownik");
            int id;
            String nazwisko, imie, narodowosc, stanowisko, data_zatr, data_wyp;


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

                output.add(pd);
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wykonywaniu SELECT");
            e.printStackTrace();
        }
        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return output;


    }

    public void dodajPracownikaDB(pracownikData pd){

        // Add

        String sql="INSERT INTO Pracownik(nazwisko, imie, narodowosc, stanowisko, data_zatr) " +
                "VALUES (?, ?, ?, ?, ?);";
        try {
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setString(1,pd.getNazwisko());
            stmtAdd.setString(2,pd.getImie());
            stmtAdd.setString(3,pd.getNarodowosc());
            stmtAdd.setString(4,pd.getStanowisko());
            //This might be troublesome
            stmtAdd.setString(5,pd.getDataZatr());

            stmtAdd.execute();

            // my own
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu osoby");
            e.printStackTrace();
        }

        // Close

        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }



    }

    public int znajdzPracownika(pracownikData pd){

        int output = 0;

        String sql="SELECT id_pracownik FROM Pracownik " +
                "WHERE (nazwisko = ? AND " +
                "imie = ? AND " +
                "narodowosc = ? AND " +
                "stanowisko = ? AND " +
                "data_zatr = ?)";
                //no data wyp as of yet

        try {
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setString(1,pd.getNazwisko());
            stmtAdd.setString(2,pd.getImie());
            stmtAdd.setString(3,pd.getNarodowosc());
            stmtAdd.setString(4,pd.getStanowisko());
            //This might be troublesome
            stmtAdd.setString(5,pd.getDataZatr());

            ResultSet result = stmtAdd.executeQuery();

            output = result.getInt("id_pracownik");

        } catch (SQLException e) {
            System.err.println("Blad przy [szukaniu] osoby");
            e.printStackTrace();
        }

        // Close

        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return output;
    }

    // param id_pracownik
    public void usunPracownikaDB(int id_prac){

        String sql="DELETE FROM Pracownik WHERE id_pracownik = ?";

        // Delete
       try {
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setInt(1,id_prac);

            stmtAdd.execute();

        } catch (SQLException e) {
            System.err.println("Blad przy [usuwaniu] osoby");
            e.printStackTrace();
        }

        // Close

       try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    // param id_pracownik
    public void modyfikujPracownikaDB(pracownikData pd, pracownikData pdOld){

        int id_prac = znajdzPracownika(pdOld);

        String sql = "UPDATE Pracownik " +
                "SET nazwisko = ?," +
                "imie = ?," +
                "narodowosc = ?," +
                "stanowisko = ?," +
                "data_zatr = ?," +
                "data_wyp = ?" +
                "WHERE id_pracownik = ?";

        try {
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmtAdd = conn.prepareStatement(sql);

            stmtAdd.setString(1,pd.getNazwisko());
            stmtAdd.setString(2,pd.getImie());
            stmtAdd.setString(3,pd.getNarodowosc());
            stmtAdd.setString(4,pd.getStanowisko());
            //This might be troublesome
            stmtAdd.setString(5,pd.getDataZatr());
            stmtAdd.setString(6, pd.getDataWyp()); // This can be null right
            stmtAdd.setInt(7,id_prac);

            System.out.println(sql);

            stmtAdd.execute();

            // my own
        } catch (SQLException e) {
            System.err.println("Blad przy [edytowaniu] osoby");
            e.printStackTrace();
        }

        // Close

        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }



    }

}

