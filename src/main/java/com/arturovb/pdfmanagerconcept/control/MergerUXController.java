package com.arturovb.pdfmanagerconcept.control;

import com.arturovb.pdfmanagerconcept.control.elements.FilePDF;
import com.arturovb.pdfmanagerconcept.utils.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MergerUXController {
//TODO FORSE TUTTO DEVE DIVENTARE "OBSERVABLE VIEW"? (PDF E PAGINE TRANNE LA VBOX) IL RESTO BISOGNEREBBE CREARLO QUI?
    @FXML
    private Button btnBack;

    //TODO? cambiare in un altro tipo di Controllo?
    @FXML
    private VBox vboxPDFs;

    @FXML
    private Label labelDRPH;

    @FXML
    ToggleButton tbtnModality;

    @FXML
    void btnBackClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/arturovb/pdfmanagerconcept/views/appux-view.fxml"));
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void handleOnDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
            labelDRPH.setVisible(false);
        }

    }

/*    @FXML
    void handleDrop(DragEvent event) throws IOException {

        //TODO salvare riferimento (url o oggetto in memoria) alla pagina, al pdf? per poi poterle referenziare per il merge e il riordinamento e tutte le cose
        List<File> files = event.getDragboard().getFiles();
        for (File f : files) {
            ArrayList<WritableImage> pagine = PDFUtility.convertToImage(f);
            FilePDF doc = new FilePDF(f.getPath(), pagine);
            if(pagine == null)
                continue;
            VBox contenitore = new VBox();
            for (Image i : pagine){
                ImageView visPag = new ImageView(i);
                contenitore.getChildren().add(visPag);
            }
            vboxPDFPages.getChildren().add(contenitore);
        }
        System.gc();
    }*/

    @FXML
    void handleDrop(DragEvent event) throws Exception {
        //TODO? rendere asincrono il caricamento delle immagini
        //TODO diminuire dimensione delle pagine visualizzate?
        //TODO le variabili statiche di PagePDF creano scompenso

        List<File> files = event.getDragboard().getFiles();
        for (File f : files) {
            String perc = f.getPath();

            FilePDF doc = new FilePDF(perc);

            int conv;
            if(tbtnModality.isSelected()) {
                try {
                    conv = doc.convertToImage();
                } catch (Exception ex) {
                    Logger.log(ex);//Logger.modalita?
                }
            }else if(!tbtnModality.isSelected()){

                try {
                    conv = doc.convertToCopertina();
                } catch (Exception ex) {
                    Logger.log(ex);
                }
                vboxPDFs.getChildren().add(doc);
            }
            vboxPDFs.getChildren().add(doc);
        }
    }


}
