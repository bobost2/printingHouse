package bstefanov.printinghouse;

import bstefanov.printinghouse.data.configuration.EconomyConfig;
import bstefanov.printinghouse.data.paper.PaperPrice;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.TableStruct;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrintingHouseController implements Initializable {

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
    static private String currentFxml;
    static private ArrayList<PrintingHouseService> printingHouses = new ArrayList<>();

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

    @FXML
    private TableView<TableStruct> printingHouseSelectionTable;

    @FXML
    private TableColumn<TableStruct, String> printingHouseNameColumn;

    @FXML
    private TableColumn<TableStruct, String> printingHouseAddressColumn;

    private void switchPane(ActionEvent event, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        currentFxml = fxml;
        Parent root = fxmlLoader.load();
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
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Printing House Report", "*.phr"));
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
    protected void onClickCreatePrintingHouse(ActionEvent event) throws IOException {
        String name = nameTextBox.getText();
        String address = addressTextBox.getText();
        double baseSalary = baseSalaryTextBox.getValue();
        double salaryBonus = salaryBonusTextBox.getValue();
        double discount = discountTextBox.getValue();

        PrintingHouseService printingHouse = new PrintingHouseService(name, address);
        printingHouse.applyEconomyConfig(
                new EconomyConfig(salaryBonus, discount, new PaperPrice(), new BigDecimal(baseSalary)));
        printingHouses.add(printingHouse);

        onClickMenuSelectPrintingHouse(event);
    }

    @FXML
    protected void onChangedTextFieldCreateHouse() {
        createNewButton.setDisable(nameTextBox.getText().isBlank() || addressTextBox.getText().isBlank());
    }

    protected void onSelectPrintingHouse(TableStruct tableStruct) {
        int id = tableStruct.id;
        String name = tableStruct.getName();
        String address = tableStruct.getAddress();
        System.out.println("Selected printing house: " + name + " " + address + " with id: " + id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if ("create-printing-house-view.fxml".equals(currentFxml)) {
            SpinnerValueFactory<Double> baseSalaryFactory =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(1.00, 100000.00, 700.00);
            baseSalaryTextBox.setValueFactory(baseSalaryFactory);
            baseSalaryTextBox.getValueFactory().setConverter(doubleStringConverter);

            SpinnerValueFactory<Double> salaryBonusFactory =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00, 25.00, 0.25);
            salaryBonusTextBox.setValueFactory(salaryBonusFactory);
            salaryBonusTextBox.getValueFactory().setConverter(doubleStringConverter);

            SpinnerValueFactory<Double> discountFactory =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00, 10.00, 0.25);
            discountTextBox.setValueFactory(discountFactory);
            discountTextBox.getValueFactory().setConverter(doubleStringConverter);
        }
        else if ("select-printing-house-view.fxml".equals(currentFxml)) {
            printingHouseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            printingHouseAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

            printingHouseSelectionTable.getItems().clear();
            for (int i = 0; i < printingHouses.size(); i++) {
                TableStruct tableStruct = new TableStruct();
                tableStruct.id = i;
                tableStruct.setName(printingHouses.get(i).getName());
                tableStruct.setAddress(printingHouses.get(i).getAddress());
                printingHouseSelectionTable.getItems().add(tableStruct);
            }

            printingHouseSelectionTable.setRowFactory( tv -> {
                TableRow<TableStruct> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        onSelectPrintingHouse(row.getItem());
                    }
                });
                return row ;
            });
        }
    }

    private final StringConverter<Double> doubleStringConverter = new StringConverter<>() {
        private final DecimalFormat df = new DecimalFormat("#.00");

        @Override
        public String toString(Double value) {
            if (value == null) {
                return "";
            }

            return df.format(value);
        }

        @Override
        public Double fromString(String string) {
            try {
                if (string == null) {
                    return null;
                }

                string = string.trim();

                if (string.isEmpty()) {
                    return null;
                }

                return df.parse(string).doubleValue();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    };
}