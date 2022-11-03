module com.arturovb.pdfmanagerconcept {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires org.apache.pdfbox;
    requires org.apache.pdfbox.tools;


    opens com.arturovb.pdfmanagerconcept to javafx.fxml;
    exports com.arturovb.pdfmanagerconcept;
    exports com.arturovb.pdfmanagerconcept.control;
    opens com.arturovb.pdfmanagerconcept.control to javafx.fxml;
}