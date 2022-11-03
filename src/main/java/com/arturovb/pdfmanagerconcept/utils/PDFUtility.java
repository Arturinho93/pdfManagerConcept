package com.arturovb.pdfmanagerconcept.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFUtility {

    public static ArrayList<String> filesPaths = new ArrayList<>();
    public static String destinationPath = null;
    public static PDFMergerUtility pmu = new PDFMergerUtility();

    /**
     *
     * Sets the url of the destination merged file.
     * @return True if successful, False otherwise
     *
     */
    public static boolean setMergeDestination() {
        try {
            if (destinationPath != null)
                pmu.setDestinationFileName(destinationPath);
            else {
                String destinazione = System.getProperty("user.dir") + "\\uniti.pdf";
                pmu.setDestinationFileName(destinazione);
            }

        } catch (Exception ex) {
            Logger.log(ex);
            return false;
        }

        return true;

    }


    /**
     *
     * Sets the urls of the files to be merged.
     * @return True if successful, False otherwise
     *
     */
    public static boolean setMergeSources() {

        try {

            if (filesPaths.size() > 1)
                for (String nome : filesPaths) {
                    File f = new File(nome);
                    pmu.addSource(f);
                }
            else return false;

        } catch (Exception ex) {
            Logger.log(ex);
            return false;
        }

        return true;

    }



    /**
     *
     * Merges many PDF files into a single one.
     * @return True if successful, False otherwise
     *
     */
    public static boolean merge(){

        long useRAM = Runtime.getRuntime().freeMemory() * 80 / 100; // use up to 80% of JVM free memory
        long useTempFile = 2_000_000_000;                           // use up to ~2GB of temp file
        try {
            pmu.mergeDocuments(MemoryUsageSetting.setupMixed(useRAM, useTempFile));
        }
        catch (Exception ex){
            Logger.log(ex);
            return false;
        }

        System.gc();    //Force gc
        return true;

    }


    public static ArrayList<Image> convertToImage(File source) throws IOException {

        if(!source.getName().toLowerCase().endsWith("pdf"))
            return null;

        PDDocument document = PDDocument.load(source);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        ArrayList<Image> converted = new ArrayList<>();
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(
                    page, 300, ImageType.RGB);
            //ImageIOUtil.writeImage(bim, String.format(System.getProperty("user.dir")+ "\\temp\\" + "pdf-%d.%s", page + 1, extension), 300);
            Image toFx = SwingFXUtils.toFXImage(bim, null);
            converted.add(toFx);
        }
        document.close();

        return converted;

    }


}
