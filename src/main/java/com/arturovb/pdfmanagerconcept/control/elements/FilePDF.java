package com.arturovb.pdfmanagerconcept.control.elements;

import com.arturovb.pdfmanagerconcept.utils.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FilePDF extends VBox {
    public String path;
    public File document;
    public PDDocument pdfDocument;

    public FilePDF(String path) throws Exception {
        super();
        try {
//            this.getStylesheets().add(getClass().getResource("/com/arturovb/pdfmanagerconcept/views/CSS/Sample.css").toExternalForm());
//            this.getStyleClass().clear();
//            this.getStyleClass().add("filepdf");
            this.path = path;
            this.setFile();
        } catch (Exception ex) {
            //TODO  log
            System.out.println(ex.getMessage());
        }
    }


    public File getFile() {
        try {
            if (path != null && this.document == null)
                this.document = new File(this.path);
        } catch (Exception ex) {
            Logger.log(ex);
            return null;
        }
        return document;
    }

    public void setFile() {
        try {
            if (path != null)
                this.document = new File(this.path);
        } catch (Exception ex) {
            Logger.log(ex);
        }
    }



    public int loadPDDocument() {
        try {
            if (document != null) {
                pdfDocument = PDDocument.load(document);
                return 1;
            }
        } catch (Exception ex) {
            Logger.log(ex);
            return 0;
        }
        return 0;
    }

//TODO QUESTI DUE METODI OPPURE QUACHE ELEMENTO NASCOSTO O DENTRO LA VBOX? o nelle listview??? LA SCOMPENSANO MOSTRANDO 3 ELEMENTI (di cui due forse null?)
//FORSE è DOVUTO ALLE VARIABILI STATICHE DENTRO A PagePDF?
    public int convertToImage() throws IOException {

        if (path == null || !path.toLowerCase().endsWith("pdf"))
            return 0;

        int loadDoc = -1;

        if (pdfDocument == null) {
            loadDoc = this.loadPDDocument();
        }

        if (loadDoc > 0) {
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
            ObservableList<String> pages = FXCollections.observableArrayList();
            int pagenumbers = pdfDocument.getNumberOfPages();
            for (int i = 0; i < pagenumbers; i++) {
                pages.add(Integer.toString(i));
                //TODO pensare se e dove aggiungere pagine PDPage e PDDocument per poi poter recuperare i riferimenti
                //TODO controllare o settare dimensioni listview che è troppo piccola, e le sue celle(imageView?) troppo larghe

            }



            try {
                for (int page = 0; page < pagenumbers; ++page) {
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 30, ImageType.RGB);
                    //writable image
                    if(bim != null){
                        Image toFx = SwingFXUtils.toFXImage(bim, null);
                        PagePDF.pagesImg.add(toFx);
                    }
                }
            }catch (Exception ex){
                System.out.println(ex.toString() +"MSG: " + ex.getMessage());
            }

            ListView<String> filePd = new ListView<>(pages);
            filePd.setCellFactory(param -> new PagePDF());
            filePd.setPrefWidth(180);



            if (loadDoc == 1)
                pdfDocument.close();

            this.getChildren().add(filePd);

            return 1;
        }
        return 0;
    }


    public int convertToCopertina() throws IOException {

        if (path == null || !path.toLowerCase().endsWith("pdf"))
            return 0;

        int loadDoc = -1;

        if (pdfDocument == null) {
            loadDoc = this.loadPDDocument();
        }

        if (loadDoc > 0) {

            PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
            ObservableList<String> pages = FXCollections.observableArrayList();
            pages.add(Integer.toString(0));


            ListView<String> filePd = new ListView<>(pages);
            filePd.setCellFactory(param -> new PagePDF());
            filePd.setPrefWidth(180);

            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 30, ImageType.RGB);
            WritableImage toFx = SwingFXUtils.toFXImage(bim, null);
            PagePDF.pagesImg.add(toFx);

            if (loadDoc == 1)
                pdfDocument.close();


            this.getChildren().add(filePd);
            return 1;
        }
        return 0;
    }



}
