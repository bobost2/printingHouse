package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyEditionController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    protected void onClickEditButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "create-edition-view.fxml");
    }

    @FXML
    protected void onClickSetExpectedEditionsButton() {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse == null) return;
        int expectedEditions = selectedPrintingHouse.getExpectedEditions()
                .getOrDefault(sceneAndDataMng.getSelectedEdition(), 0);

        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Set expected editions");
        dialog.setHeaderText("Please enter the expected editions to be sold for this edition:");

        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, expectedEditions));

        VBox vbox = new VBox(spinner);
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return spinner.getValue();
            }
            return null;
        });

        Optional<Integer> result = dialog.showAndWait();
        result.ifPresent(value -> {
            if (value == 0) {
                selectedPrintingHouse.getExpectedEditions().remove(sceneAndDataMng.getSelectedEdition());
            }
            else {
                selectedPrintingHouse.getExpectedEditions().put(sceneAndDataMng.getSelectedEdition(), value);
            }
        });
    }

    @FXML
    protected void onClickRemoveEditionButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Delete edition");
        alert.setHeaderText("Are you sure you want to delete this edition?");
        alert.setContentText("This action cannot be undone.");
        Optional<ButtonType> alertResult = alert.showAndWait();
        if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
            Edition selectedEdition = sceneAndDataMng.getSelectedEdition();
            selectedPrintingHouse.getExpectedEditions().remove(selectedEdition);
            selectedPrintingHouse.getEditionsToPrint().remove(selectedEdition);
            sceneAndDataMng.switchPane(event, "list-edition-view.fxml");
        }
    }

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "list-edition-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            Edition selectedEdition = sceneAndDataMng.getSelectedEdition();
            if (selectedEdition != null) {
                actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - " + selectedEdition.getTitle() + ":");
            }
        }
    }
}
