package dbUtilities;

import DataModels.narzedziaData;
import DataModels.pracownikData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
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
                    "VALUES ('Demchuk','Vasyl','Ukraina','Spawacz','2014-12-01');";
            stmt.execute(sql);
            sql="INSERT INTO Pracownik(nazwisko, imie, narodowosc, stanowisko, data_zatr) " +
                    "VALUES ('Khvalin','Oleksandr','Ukraina','Spawacz','2014-12-01');";
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


            // my own
        } catch (SQLException e) {
            System.err.println("Blad przy seedowaniu tabeli Narzedzia");
            e.printStackTrace();
        }
    } // Untested

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
    } // Untested

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

            // my own
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
    } // Untested

    public void SeedWypozyczenia(){

        String sql;
        try {
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (1,1,'2014-11-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (3,2,'2014-12-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (3,4,'2014-12-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp, data_zwrot) " +
                    "VALUES (3,3,'2014-12-03', '2014-12-10');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (2,5,'2014-12-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp) " +
                    "VALUES (2,7,'2014-12-03');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp, data_zwrot) " +
                    "VALUES (2,3,'2014-12-10','2014-12-24');";
            stmt.execute(sql);
            sql="INSERT INTO Wypozyczenia(id_narzedzia, id_pracownik, data_wyp, data_zwrot) " +
                    "VALUES (3,3,'2014-12-27','2015-03-07');";
            stmt.execute(sql);


            // my own
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
    } // Untested

    // Pracownik

    public ObservableList<pracownikData> getPracownicyDB(){

        ObservableList<pracownikData> output = FXCollections.observableArrayList();

        int i = 1;

        //Find Data

        try {
            ResultSet result = stmt.executeQuery("SELECT * FROM Pracownik");
            String nazwisko, imie, narodowosc, stanowisko, data_zatr, data_wyp;

            while(result.next()) {
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
            System.err.println("Blad przy wykonywaniu ladowaniu pracownikow");
            e.printStackTrace();
        }
        return output;
    }

    public void dodajPracownikaDB(pracownikData pd){

        // Add

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

            // my own
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu pracownika");
            e.printStackTrace();
        }

        // Close
    }

    public int znajdzPracownikaDB(pracownikData pd){

        int output = 0;

        String sql="SELECT id_pracownik FROM Pracownik " +
                "WHERE (nazwisko = ? AND " +
                "imie = ? AND " +
                "narodowosc = ? AND " +
                "stanowisko = ? AND " +
                "data_zatr = ?)";
                //no data wyp as of yet

        try {
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
        return output;
    }

    public void usunPracownikaDB(int id_prac){

        String sql="DELETE FROM Pracownik WHERE id_pracownik = ?";

        // Delete
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

        int i = 1;

        //Find Data

        try {
            ResultSet result = stmt.executeQuery("SELECT * FROM Narzedzia");
            String nazwa, kod, data_zakupu, data_utylizacji;
            BigDecimal cena;

            while(result.next()) {
                nazwa = result.getString("nazwa");
                kod = result.getString("kod");
                data_zakupu = result.getString("data_zakupu");
                data_utylizacji = result.getString("data_utylizacji");
                cena = result.getBigDecimal("cena"); // DECIMAL -> BIGDECIMAL

                //Cena is being passed as Double, not Money/Decimal/BigDecimal
                narzedziaData nd = new narzedziaData(nazwa, kod, data_zakupu, data_utylizacji, cena);
                nd.setLp(i++);

                output.add(nd);
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wykonywaniu ladowaniu narzedzi");
            e.printStackTrace();
        }
        return output;
    }

    // Wypozyczenia

    // Wynagrodzenie

    public void closeConnection(){
        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

}

