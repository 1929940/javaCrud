package DataModels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class dataLists {


/*    public static ObservableList<narzedziaData> NarzedziaDataList = FXCollections.observableArrayList(
            new narzedziaData(1,"Szlifierka Hexo", "2014-01-01","2016-02-01", 1400),
            new narzedziaData(2,"Polautomat Spawalniczy","2016-05-11",7999.99),
            new narzedziaData(3,"Mlotek Spawalniczy", "2017-12-24",59.99 ),
            new narzedziaData(4,"Mlotek Spawalniczy", "2017-12-24","2018-01-27",59.99 ),
            new narzedziaData(5,"Mlotek Spawalniczy", "2017-12-24",59.99 ),
            new narzedziaData(6,"Mlotek Spawalniczy", "2017-12-24",59.99 ),
            new narzedziaData(7, "Frez Hexo", "2019-01-25",1400)
    );*/

    public static ObservableList<wynagrodzenieData> WynagrodzenieDataList = FXCollections.observableArrayList(
            new wynagrodzenieData("Demchuk","Mykhailo","Spawacz",1,"2014-01-01",20,215,
                    "Wykonanie wręgów 122-201 na jednostce NB231"),
            new wynagrodzenieData("Demchuk","Vasyl","Spawacz",2,"2014-01-01",21,219,
                    "Wykonanie wręgów 1-121 na jednostce NB231"),
            new wynagrodzenieData("Khvalin","Oleksandr","Spawacz",2,"2014-01-01",21,218,
                    "Wykonanie otworów w zbiornikach wody pitnej na jednostce NB231"),
            new wynagrodzenieData("Demchuk","Mykhailo","Spawacz",3,"2014-03-01",20,215,
                    "Wykonanie trapu na jednostce NB231/F")
    );
}
