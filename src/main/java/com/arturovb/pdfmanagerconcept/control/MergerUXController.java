package com.arturovb.pdfmanagerconcept.control;

import com.arturovb.pdfmanagerconcept.utils.PDFUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MergerUXController {

    @FXML
    private Button btnBack;

    @FXML
    private VBox vboxPDFPages;

    @FXML
    void btnBackClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/arturovb/pdfmanagerconcept/views/appux-view.fxml"));
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void handleOnDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles())
            event.acceptTransferModes(TransferMode.ANY);
    }

    @FXML
    void handleDrop(DragEvent event) throws IOException {
        //TODO rendere asincrono il caricamento delle immagini
        //TODO diminuire dpi e dimensione delle pagine visualizzate
        //TODO salvare riferimento (url o oggetto in memoria) alla pagina, al pdf? per poi poterle referenziare per il merge e il riordinamento e tutte le cose
        List<File> files = event.getDragboard().getFiles();
        for (File f : files) {
            ArrayList<Image> pagine = PDFUtility.convertToImage(f);
            if(pagine == null)
                continue;
            VBox contenitore = new VBox();
            for (Image i : pagine){
                ImageView visPag = new ImageView(i);
                contenitore.getChildren().add(visPag);
            }
            vboxPDFPages.getChildren().add(contenitore);
        }
    }

}
