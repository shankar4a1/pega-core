// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.util;

import java.io.IOException;
import java.io.FileNotFoundException;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.testng.Reporter;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class PDFUtil
{
    public static String readDatafromPDF(final String path) {
        String pdfFileInText = null;
        try {
            Throwable t = null;
            try {
                final PDDocument document = PDDocument.load(new File(path));
                try {
                    Reporter.log("Reading PDF file: " + path, true);
                    document.getClass();
                    if (!document.isEncrypted()) {
                        final PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                        stripper.setSortByPosition(true);
                        final PDFTextStripper tStripper = new PDFTextStripper();
                        pdfFileInText = tStripper.getText(document);
                    }
                }
                finally {
                    if (document != null) {
                        document.close();
                    }
                }
            }
            finally {
                if (t == null) {
                    final Throwable exception =new Throwable();
                    t = exception;
                }
                else {
                    final Throwable exception =new Throwable();
                    if (t != exception) {
                        t.addSuppressed(exception);
                    }
                }
            }
        }
        catch (InvalidPasswordException pe) {
            Reporter.log("Invalid password exception::", true);
            pe.printStackTrace();
        }
        catch (FileNotFoundException fe) {
            Reporter.log("File not found exception::", true);
            fe.printStackTrace();
        }
        catch (IOException ie) {
            Reporter.log("IO exception::", true);
            ie.printStackTrace();
        }
        return pdfFileInText;
    }
}
