package bstefanov.printinghouse;

import bstefanov.printinghouse.service.PrintingHouseService;

@SuppressWarnings("commented-out-code")
// JAVA FX CODE
/*
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMinWidth(320);
        stage.setMinHeight(240);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}*/

// CONSOLE CODE
public class HelloApplication {
    public static void main(String[] args) {
        // Note: If uploading is not allowed after the deadline, the code will be updated on this
        // repository: https://github.com/bobost2/printingHouse
        // Another Note: The main method is not finished, but it will be finished after the deadline (with JavaFX UI).
        PrintingHouseService service = new PrintingHouseService("Test Printing House", "Ovcha Kupel 2");
    }
}