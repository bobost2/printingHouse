package bstefanov.printinghouse.ui.utils;

import bstefanov.printinghouse.data.audit.FinalReport;
import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.printer.Printer;
import bstefanov.printinghouse.service.PrintingHouseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SceneAndDataManagerSingleton {
    private static SceneAndDataManagerSingleton instance;

    // Window offset
    private final double offsetX;
    private final double offsetY;

    // Static fields
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<PrintingHouseService> printingHouses = new ArrayList<>();
    private PrintingHouseService selectedPrintingHouse;
    private Printer selectedPrinter;
    private Employee selectedEmployee;
    private Edition selectedEdition;
    private FinalReport finalReport;
    private boolean reportOpenedFromFile = false;

    private SceneAndDataManagerSingleton() {
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

    public static SceneAndDataManagerSingleton getInstance() {
        if (instance == null) {
            instance = new SceneAndDataManagerSingleton();
        }
        return instance;
    }

    public ArrayList<PrintingHouseService> getPrintingHouses() {
        return printingHouses;
    }

    public void setPrintingHouses(ArrayList<PrintingHouseService> printingHouses) {
        this.printingHouses = printingHouses;
    }

    public PrintingHouseService getSelectedPrintingHouse() {
        return selectedPrintingHouse;
    }

    public void setSelectedPrintingHouse(PrintingHouseService selectedPrintingHouse) {
        this.selectedPrintingHouse = selectedPrintingHouse;
    }

    public Printer getSelectedPrinter() {
        return selectedPrinter;
    }

    public void setSelectedPrinter(Printer selectedPrinter) {
        this.selectedPrinter = selectedPrinter;
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public Edition getSelectedEdition() {
        return selectedEdition;
    }

    public void setSelectedEdition(Edition selectedEdition) {
        this.selectedEdition = selectedEdition;
    }

    public FinalReport getFinalReport() {
        return finalReport;
    }

    public void setFinalReport(FinalReport finalReport) {
        this.finalReport = finalReport;
    }

    public boolean isReportOpenedFromFile() {
        return reportOpenedFromFile;
    }

    public void setReportOpenedFromFile(boolean reportOpenedFromFile) {
        this.reportOpenedFromFile = reportOpenedFromFile;
    }

    public void switchPane(ActionEvent event, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bstefanov/printinghouse/" + fxml));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, stage.getWidth() - offsetX, stage.getHeight() - offsetY);
        stage.setScene(scene);
        stage.show();
    }
}
