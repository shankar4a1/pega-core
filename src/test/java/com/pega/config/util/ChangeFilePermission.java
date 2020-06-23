// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config.util;

import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileFilter;
import java.io.File;

public class ChangeFilePermission
{
    String COPYRIGHT;
    private static final String VERSION = "$Id: ChangeFilePermission.java 209205 2016-09-29 02:40:12Z SachinVellanki $";
    
    public ChangeFilePermission() {
        this.COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    }
    
    public static void main(final String[] args) throws IOException, InterruptedException {
        File scriptsDir = new File(String.valueOf(System.getenv("WORKSPACE")) + File.separator + "Tests" + File.separator + "scripts");
        final File newScriptsDir = new File(String.valueOf(System.getenv("WORKSPACE")) + File.separator + "Tests" + File.separator + "new_scripts");
        if (!newScriptsDir.exists()) {
            newScriptsDir.mkdirs();
        }
        final File[] filesList = scriptsDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.getName().toLowerCase().endsWith("sh");
            }
        });
        BufferedReader reader = null;
        PrintWriter writer = null;
        System.out.println("Length: " + filesList.length);
        for (int i = 0; i < filesList.length; ++i) {
            final File f = filesList[i];
            final File f2 = new File(String.valueOf(newScriptsDir.getAbsolutePath()) + File.separator + f.getName());
            f2.createNewFile();
            try {
                writer = new PrintWriter(new FileOutputStream(f2));
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.println(line.trim());
                }
            }
            catch (Exception e) {
                System.out.println("Exception is  " + e);
                continue;
            }
            finally {
                writer.close();
                reader.close();
            }
            writer.close();
            reader.close();
        }
        FileUtils.deleteDirectory(scriptsDir);
        scriptsDir = new File(String.valueOf(System.getenv("WORKSPACE")) + File.separator + "Tests" + File.separator + "scripts");
        FileUtils.copyDirectory(newScriptsDir, scriptsDir);
        final File[] list = scriptsDir.listFiles();
        for (int j = 0; j < list.length; ++j) {
            list[j].setExecutable(true);
            System.out.println("Setting " + list[j].getName() + " to as executable");
            System.out.println(FileUtils.readFileToString(list[j]));
        }
    }
    
    public static void instalDos2Unix() {
        try {
            final Process p = Runtime.getRuntime().exec("yum install dos2unix");
            final BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            p.waitFor();
        }
        catch (Exception e) {
            System.out.println("Exception installing do2unix command");
        }
    }
}
