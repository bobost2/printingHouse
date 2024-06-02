package bstefanov.printinghouse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PrintingHouseController {

    // Windows offset (MacOS and Linux may need different values)
    private static final double offsetX = 16.5;
    private static final double offsetY = 39;

//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
    @FXML
    protected void onClickMenuSelectPrintingHouse(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("select-printing-house-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, stage.getWidth() - offsetX, stage.getHeight() - offsetY);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onClickMenuLoadReport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Printing House Report", "*.phr"));
        fileChooser.setTitle("Load Report File");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getPath());
        } else {
            System.out.println("File selection cancelled.");
        }
    }

    @FXML
    protected void onClickGoBackToMainView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, stage.getWidth() - offsetX, stage.getHeight() - offsetY);
        stage.setScene(scene);
        stage.show();
    }
}