module bstefanov.printinghouse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens bstefanov.printinghouse to javafx.fxml, javafx.base;
    exports bstefanov.printinghouse;

    opens bstefanov.printinghouse.ui.structs to javafx.base;
    exports bstefanov.printinghouse.ui.structs;

    opens bstefanov.printinghouse.ui.controllers to javafx.base, javafx.fxml;
    exports bstefanov.printinghouse.ui.controllers;

    exports bstefanov.printinghouse.service;
    exports bstefanov.printinghouse.ui.utils;
    opens bstefanov.printinghouse.ui.utils to javafx.base, javafx.fxml;
}