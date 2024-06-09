package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.configuration.EconomyConfig;
import bstefanov.printinghouse.data.paper.PaperPrice;
import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.DoubleStringConverter;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class PrintingHouseEconomySettingsController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private Spinner<Double> glossyPriceTextBox;

    @FXML
    private Spinner<Double> normalPriceTextBox;

    @FXML
    private Spinner<Double> newsprintPriceTextBox;

    @FXML
    private Spinner<Double> sizePercentageMultiplierTextBox;

    @FXML
    private Spinner<Double> baseSalaryTextBox;

    @FXML
    private Spinner<Double> salaryBonusTextBox;

    @FXML
    private Spinner<Double> discountTextBox;

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-settings-view.fxml");
    }

    @FXML
    protected void onClickEditButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        EconomyConfig economyConfig = selectedPrintingHouse.getEconomyConfig();
        PaperPrice paperPrice = economyConfig.paperPrice;

        paperPrice.setPrice(PaperType.GLOSSY, BigDecimal.valueOf(glossyPriceTextBox.getValue()));
        paperPrice.setPrice(PaperType.NORMAL, BigDecimal.valueOf(normalPriceTextBox.getValue()));
        paperPrice.setPrice(PaperType.NEWSPRINT, BigDecimal.valueOf(newsprintPriceTextBox.getValue()));
        paperPrice.setSizePercentageMultiplier(sizePercentageMultiplierTextBox.getValue());

        economyConfig.baseSalary = BigDecimal.valueOf(baseSalaryTextBox.getValue());
        economyConfig.bonusPercentage = salaryBonusTextBox.getValue();
        economyConfig.discountPercentage = discountTextBox.getValue();

        selectedPrintingHouse.applyEconomyConfig(economyConfig);

        sceneAndDataMng.switchPane(event, "printing-house-settings-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Economy Settings:");
            EconomyConfig economyConfig = selectedPrintingHouse.getEconomyConfig();
            PaperPrice paperPrice = economyConfig.paperPrice;

            SpinnerValueFactory<Double> glossyPriceFactory =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 10.00,
                            paperPrice.getPrice(PaperType.GLOSSY, PaperSize.A5).doubleValue(), 0.05);
            glossyPriceFactory.setConverter(DoubleStringConverter.getConverter());
            glossyPriceTextBox.setValueFactory(glossyPriceFactory);

            SpinnerValueFactory<Double> normalPriceFactory =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 10.00,
                            paperPrice.getPrice(PaperType.NORMAL, PaperSize.A5).doubleValue(), 0.05);
            normalPriceFactory.setConverter(DoubleStringConverter.getConverter());
            normalPriceTextBox.setValueFactory(normalPriceFactory);

            SpinnerValueFactory<Double> newsprintPriceFactory =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 10.00,
                            paperPrice.getPrice(PaperType.NEWSPRINT, PaperSize.A5).doubleValue(), 0.05);
            newsprintPriceFactory.setConverter(DoubleStringConverter.getConverter());
            newsprintPriceTextBox.setValueFactory(newsprintPriceFactory);

            SpinnerValueFactory<Double> sizePercentageMultiplierFactory =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00,
                            paperPrice.getSizePercentageMultiplier(), 1.00);
            sizePercentageMultiplierFactory.setConverter(DoubleStringConverter.getConverter());
            sizePercentageMultiplierTextBox.setValueFactory(sizePercentageMultiplierFactory);

            SpinnerValueFactory<Double> baseSalaryFactory2 =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(1.00, 100000.00,
                            economyConfig.baseSalary.doubleValue(), 100.00);
            baseSalaryFactory2.setConverter(DoubleStringConverter.getConverter());
            baseSalaryTextBox.setValueFactory(baseSalaryFactory2);

            SpinnerValueFactory<Double> salaryBonusFactory2 =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00,
                            economyConfig.bonusPercentage, 0.25);
            salaryBonusFactory2.setConverter(DoubleStringConverter.getConverter());
            salaryBonusTextBox.setValueFactory(salaryBonusFactory2);

            SpinnerValueFactory<Double> discountFactory2 =
                    new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00,
                            economyConfig.discountPercentage, 0.25);
            discountFactory2.setConverter(DoubleStringConverter.getConverter());
            discountTextBox.setValueFactory(discountFactory2);
        }
    }
}
