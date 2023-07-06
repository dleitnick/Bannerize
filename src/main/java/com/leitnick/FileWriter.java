package com.leitnick;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileWriter {
    private static PrintWriter pw = null;
    private String fileLocation = "";

    public FileWriter(String fileLocation) {
        if (fileLocation.lastIndexOf(File.separator) != fileLocation.length() - 1) {
            this.fileLocation = fileLocation + File.separator;
        } else this.fileLocation = fileLocation;
    }

    public FileWriter() {
    }

    public void makeBannerTxt(String[] message) {
        try {
            if (pw == null) {
                String logFilename = fileLocation + "banner.txt";
                pw = new PrintWriter(new FileOutputStream(logFilename, false));
            }
            for (String s : message) {
                pw.println(s);
            }
            pw.flush();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
