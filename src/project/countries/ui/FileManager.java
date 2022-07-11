package project.countries.ui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileManager extends JFileChooser {
    /**
     * konstruktor tridy, ktery inicializuje vytvoreni objektu z tridy
     */
    public FileManager() {
        // konstruktor predka dokaze prednastavit adresar
        // home.dir odkazuje na domovsky adresar
        super(System.getProperty("home.dir"));

        // metoda filtruje soubory, ktere je mozno pouzivat
        addChoosableFileFilter(new FileFilter() {
            // mozno pouzit adresar se souborem s priponou .table
            // nebo soubor s priponou .table
            @Override
            public boolean accept(File file) {
                if (file.isDirectory() || file.getName().endsWith(".table")) {
                    return true;
                }
                return false;
            }

            // vraci popisek filtru
            @Override
            public String getDescription() {
                return "Usable file (.table)";
            }
        });
    }
}