module com.group09.playit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.group09.playit to javafx.fxml;
    exports com.group09.playit;
}