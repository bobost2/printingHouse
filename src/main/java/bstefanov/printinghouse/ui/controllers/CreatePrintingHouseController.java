package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.configuration.EconomyConfig;
import bstefanov.printinghouse.data.paper.PaperPrice;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.DoubleStringConverter;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class CreatePrintingHouseController implements Initializable {

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
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "select-printing-house-view.fxml");
    }

    @FXML
    protected void onClickCreateNewButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();

        String name = nameTextBox.getText();
        String address = addressTextBox.getText();
        double baseSalary = baseSalaryTextBox.getValue();
        double salaryBonus = salaryBonusTextBox.getValue();
        double discount = discountTextBox.getValue();

        PrintingHouseService printingHouse = new PrintingHouseService(name, address);
        printingHouse.applyEconomyConfig(
                new EconomyConfig(salaryBonus, discount, new PaperPrice(), new BigDecimal(baseSalary)));
        sceneAndDataMng.getPrintingHouses().add(printingHouse);

        sceneAndDataMng.switchPane(event, "select-printing-house-view.fxml");
    }

    @FXML
    protected void onChangedTextField() {
        createNewButton.setDisable(nameTextBox.getText().isBlank() || addressTextBox.getText().isBlank());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Double> baseSalaryFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1.00, 100000.00, 700.00);
        baseSalaryTextBox.setValueFactory(baseSalaryFactory);
        baseSalaryTextBox.getValueFactory().setConverter(DoubleStringConverter.getConverter());

        SpinnerValueFactory<Double> salaryBonusFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00, 25.00, 0.25);
        salaryBonusTextBox.setValueFactory(salaryBonusFactory);
        salaryBonusTextBox.getValueFactory().setConverter(DoubleStringConverter.getConverter());

        SpinnerValueFactory<Double> discountFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00, 10.00, 0.25);
        discountTextBox.setValueFactory(discountFactory);
        discountTextBox.getValueFactory().setConverter(DoubleStringConverter.getConverter());
    }
}
