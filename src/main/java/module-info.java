module bstefanov.printinghouse.printinghouse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens bstefanov.printinghouse.printinghouse to javafx.fxml;
    exports bstefanov.printinghouse.printinghouse;
}