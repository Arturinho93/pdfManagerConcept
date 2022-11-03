package com.arturovb.pdfmanagerconcept.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    public static void log(Exception ex) {

        //forse il percorso è meglio sostituirlo con questo?
        //        String path = Test.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        //        String decodedPath = URLDecoder.decode(path, "UTF-8");

        File logga = new File(System.getProperty("user.dir") + "\\TempPDFApp\\log.txt");
        FileWriter fWriter = null;

        try {
            fWriter = new FileWriter(logga, true);
        } catch (IOException e) {
            System.out.println(LocalDateTime.now().toString() + " Impossibile creare file writer: " + e.getMessage());
        }

        if (fWriter != null) {
            try {
                LocalDateTime istante = LocalDateTime.now();
                fWriter.write(istante.getDayOfMonth() + "/" + istante.getMonthValue() + "/" +
                        istante.getYear() + ", " + istante.getHour() + ":" + istante.getMinute() + ":"
                        + istante.getSecond() + " = " + ex.getMessage() + '\n');
                fWriter.close();
            } catch (IOException e) {
                System.out.println("Impossibile appendere nel file writer: " + e.getMessage());
            }
        }

        System.gc();

    }

}
