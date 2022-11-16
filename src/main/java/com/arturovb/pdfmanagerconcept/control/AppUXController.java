package com.arturovb.pdfmanagerconcept.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AppUXController {

    @FXML
    private Button btnMerge;

    @FXML
    private Button btnSplit;

    @FXML
    void btnMergeClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/arturovb/pdfmanagerconcept/views/mergerux-view.fxml"));
        Stage window = (Stage) btnMerge.getScene().getWindow();
        window.setScene(new Scene(root));

    }

    @FXML
    void btnSplitClicked(ActionEvent event) {
    }

}