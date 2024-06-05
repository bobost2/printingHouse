package bstefanov.printinghouse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PrintingHouseController {

    // Window offset
    private static final double offsetX;
    private static final double offsetY;

    static {
        String userOS = System.getProperty("os.name").toLowerCase();
        if (userOS.contains("win")) {
            offsetX = 16.5;
            offsetY = 39;
        }
        else // For Mac and Linux (Linux is not tested)
        {
            offsetX = 0;
            offsetY = 28;
        }
    }

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField addressTextBox;

    @FXML
    private Spinner<Double> baseSalaryTextBox;

    @FXML
    private Spinner<Double> salaryBonusTextBox;

    @FXML
    private Spinner<Double> discountTextBox;

    @FXML
    private Button createNewButton;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
    private void switchPane(ActionEvent event, String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, stage.getWidth() - offsetX, stage.getHeight() - offsetY);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onClickMenuSelectPrintingHouse(ActionEvent event) throws IOException {
        switchPane(event, "select-printing-house-view.fxml");
    }

    @FXML
    protected void onClickGoBackToMainView(ActionEvent event) throws IOException {
        switchPane(event, "main-view.fxml");
    }

    @FXML
    protected void onClickCreatePrintingHouseMenu(ActionEvent event) throws IOException {
        switchPane(event, "create-printing-house-view.fxml");
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
}