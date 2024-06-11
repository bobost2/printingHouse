package bstefanov.printinghouse.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SellEditionsController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: What tf should I do tomorrow?
        // Duplicate Edition List view, use the same table struct without the expected edition column.
        // On double-click, it should open a simple dialog that notifies that the edition has been sold.
    }
}
