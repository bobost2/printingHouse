module bstefanov.printinghouse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens bstefanov.printinghouse to javafx.fxml;
    opens bstefanov.printinghouse.ui to javafx.base;

    exports bstefanov.printinghouse;
}