module bstefanov.printinghouse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens bstefanov.printinghouse to javafx.fxml;
    exports bstefanov.printinghouse;
}