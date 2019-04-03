package Main;

import dbUtilities.DBconnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindowFXML.fxml"));

        //primaryStage.initModality(Modality.APPLICATION_MODAL);
        //This turns a window into a messagebox-like window

        primaryStage.setTitle("Shipyard Employee & Equipment Management Tool");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(500);
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}


