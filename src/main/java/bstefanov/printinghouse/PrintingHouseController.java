package bstefanov.printinghouse;

import bstefanov.printinghouse.data.configuration.EconomyConfig;
import bstefanov.printinghouse.data.paper.PaperPrice;
import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.TableStruct;
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
import java.util.Optional;
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

    // Static fields
    static private String currentFxml;
    @SuppressWarnings("FieldMayBeFinal")
    static private ArrayList<PrintingHouseService> printingHouses = new ArrayList<>();
    static private PrintingHouseService selectedPrintingHouse;

    // Create Printing House
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

    // Printing House Selection
    @FXML
    private TableView<TableStruct> printingHouseSelectionTable;

    @FXML
    private TableColumn<TableStruct, String> printingHouseNameColumn;

    @FXML
    private TableColumn<TableStruct, String> printingHouseAddressColumn;

    // Title for the selected printing house
    @FXML
    private Label actionsMenuLabel;

    // General Settings
    @FXML
    private TextField nameEditTextBox;

    @FXML
    private TextField addressEditTextBox;

    @FXML
    private Button editGenSettingsButton;

    // Economy Settings
    @FXML
    private Spinner<Double> glossyPriceEditTextBox;

    @FXML
    private Spinner<Double> normalPriceEditTextBox;

    @FXML
    private Spinner<Double> newsprintPriceEditTextBox;

    @FXML
    private Spinner<Double> sizePercentageMultiplierEditTextBox;

    @FXML
    private Spinner<Double> baseSalaryEditTextBox;

    @FXML
    private Spinner<Double> salaryBonusEditTextBox;

    @FXML
    private Spinner<Double> discountEditTextBox;

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

    @FXML
    protected void onChangedTextFieldEditGenSettingsPrintingHouse() {
        editGenSettingsButton.setDisable(nameEditTextBox.getText().isBlank() || addressEditTextBox.getText().isBlank());
    }

    @SuppressWarnings("ClassEscapesDefinedScope")
    protected void onSelectPrintingHouse(ActionEvent actionEvent, TableStruct tableStruct) throws IOException {
        selectedPrintingHouse = printingHouses.get(tableStruct.id);
        onClickMenuPrintingHouseActions(actionEvent);
    }

    @FXML
    protected void onClickMenuPrintingHouseActions(ActionEvent event) throws IOException {
        switchPane(event, "printing-house-actions-view.fxml");
    }

    @FXML
    protected void onClickMenuSettings(ActionEvent event) throws IOException {
        switchPane(event, "printing-house-settings-view.fxml");
    }

    @FXML
    protected void onClickDestroyPrintingHouse(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Delete Printing House");
        alert.setHeaderText("Are you sure you want to delete this printing house?");
        alert.setContentText("This action cannot be undone.");
        Optional<ButtonType> alertResult = alert.showAndWait();
        if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
            printingHouses.remove(selectedPrintingHouse);
            onClickMenuSelectPrintingHouse(event);
        }
    }

    @FXML
    protected void onClickEditGeneralSettings(ActionEvent event) throws IOException {
        switchPane(event, "printing-house-general-settings-view.fxml");
    }

    @FXML
    protected void onClickSaveGeneralSettings(ActionEvent event) throws IOException {
        selectedPrintingHouse.setName(nameEditTextBox.getText());
        selectedPrintingHouse.setAddress(addressEditTextBox.getText());
        switchPane(event, "printing-house-settings-view.fxml");
    }

    @FXML
    protected void onClickEditEconomySettings(ActionEvent event) throws IOException {
        switchPane(event, "printing-house-economy-settings-view.fxml");
    }

    @FXML
    protected void onClickSaveEconomySettings(ActionEvent event) throws IOException {
        EconomyConfig economyConfig = selectedPrintingHouse.getEconomyConfig();
        PaperPrice paperPrice = economyConfig.paperPrice;

        paperPrice.setPrice(PaperType.GLOSSY, BigDecimal.valueOf(glossyPriceEditTextBox.getValue()));
        paperPrice.setPrice(PaperType.NORMAL, BigDecimal.valueOf(normalPriceEditTextBox.getValue()));
        paperPrice.setPrice(PaperType.NEWSPRINT, BigDecimal.valueOf(newsprintPriceEditTextBox.getValue()));
        paperPrice.setSizePercentageMultiplier(sizePercentageMultiplierEditTextBox.getValue());

        economyConfig.baseSalary = BigDecimal.valueOf(baseSalaryEditTextBox.getValue());
        economyConfig.bonusPercentage = salaryBonusEditTextBox.getValue();
        economyConfig.discountPercentage = discountEditTextBox.getValue();

        selectedPrintingHouse.applyEconomyConfig(economyConfig);

        switchPane(event, "printing-house-settings-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (currentFxml == null) {
            currentFxml = "main-view.fxml";
        }
        switch (currentFxml)
        {
            case "create-printing-house-view.fxml":
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
                break;

            case "select-printing-house-view.fxml":
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
                            try {
                                onSelectPrintingHouse(new ActionEvent(row, null), row.getItem());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    return row;
                });
                break;

            case "printing-house-actions-view.fxml":
                if (selectedPrintingHouse != null) {
                    actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Select Operation:");
                }
                break;

            case "printing-house-settings-view.fxml":
                if (selectedPrintingHouse != null) {
                    actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Settings:");
                }
                break;

            case "printing-house-general-settings-view.fxml":
                if (selectedPrintingHouse != null) {
                    actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - General Settings:");
                    nameEditTextBox.setText(selectedPrintingHouse.getName());
                    addressEditTextBox.setText(selectedPrintingHouse.getAddress());
                }
                break;

            case "printing-house-economy-settings-view.fxml":
                if (selectedPrintingHouse != null) {
                    actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Economy Settings:");
                    EconomyConfig economyConfig = selectedPrintingHouse.getEconomyConfig();
                    PaperPrice paperPrice = economyConfig.paperPrice;

                    SpinnerValueFactory<Double> glossyPriceFactory =
                            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 10.00,
                                    paperPrice.getPrice(PaperType.GLOSSY, PaperSize.A5).doubleValue(), 0.05);
                    glossyPriceFactory.setConverter(doubleStringConverter);
                    glossyPriceEditTextBox.setValueFactory(glossyPriceFactory);

                    SpinnerValueFactory<Double> normalPriceFactory =
                            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 10.00,
                                    paperPrice.getPrice(PaperType.NORMAL, PaperSize.A5).doubleValue(), 0.05);
                    normalPriceFactory.setConverter(doubleStringConverter);
                    normalPriceEditTextBox.setValueFactory(normalPriceFactory);

                    SpinnerValueFactory<Double> newsprintPriceFactory =
                            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 10.00,
                                    paperPrice.getPrice(PaperType.NEWSPRINT, PaperSize.A5).doubleValue(), 0.05);
                    newsprintPriceFactory.setConverter(doubleStringConverter);
                    newsprintPriceEditTextBox.setValueFactory(newsprintPriceFactory);

                    SpinnerValueFactory<Double> sizePercentageMultiplierFactory =
                            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00,
                                    paperPrice.getSizePercentageMultiplier(), 1.00);
                    sizePercentageMultiplierFactory.setConverter(doubleStringConverter);
                    sizePercentageMultiplierEditTextBox.setValueFactory(sizePercentageMultiplierFactory);

                    SpinnerValueFactory<Double> baseSalaryFactory2 =
                            new SpinnerValueFactory.DoubleSpinnerValueFactory(1.00, 100000.00,
                                    economyConfig.baseSalary.doubleValue(), 100.00);
                    baseSalaryFactory2.setConverter(doubleStringConverter);
                    baseSalaryEditTextBox.setValueFactory(baseSalaryFactory2);

                    SpinnerValueFactory<Double> salaryBonusFactory2 =
                            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00,
                                    economyConfig.bonusPercentage, 0.25);
                    salaryBonusFactory2.setConverter(doubleStringConverter);
                    salaryBonusEditTextBox.setValueFactory(salaryBonusFactory2);

                    SpinnerValueFactory<Double> discountFactory2 =
                            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00,
                                    economyConfig.discountPercentage, 0.25);
                    discountFactory2.setConverter(doubleStringConverter);
                    discountEditTextBox.setValueFactory(discountFactory2);
                }
                break;
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