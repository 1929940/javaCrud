package DataModels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class dataLists {
    public static ObservableList<pracownikData> PracownicyDataList = FXCollections.observableArrayList(
            new pracownikData("Demchuk","Vasyl","Ukraina","Spawacz",
                    "2014-01-01"),
            new pracownikData("Demchuk","Mykhailo","Ukraina","Spawacz",
                    "2014-01-01"),
            new pracownikData("Khvalin","Oleksandr","Ukraina","Spawacz",
                    "2014-01-01"),
            new pracownikData("Sas","Grzegorz","Polska","Szlifierz","2012-04-23", "2013-11-12")
    );
}
