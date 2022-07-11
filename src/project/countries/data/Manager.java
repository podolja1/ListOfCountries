package project.countries.data;

import javax.swing.*;
import java.io.*;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Trida spravujici zaznamy v seznamu
 */
public class Manager {
    /**
     * seznam zemi
     * agreguje zeme do seznamu
     */
    private ArrayList<Country> countries;

    /**
     * kontruktor
     * inicializuje tridu seznamu (ArrayList) zemi, tj. vytvori objekt seznamu
     */

    public Manager() {this.countries = new ArrayList<Country>();}

    /**
     * metody pro praci se seznamem
     */
    public void addCountry(Country country) {   // metoda addCountry() prida zemi do seznamu
        countries.add(country);                 // bez navratoveho typu
    }

    public void deleteCountry(int i) {  // metoda deleteCountry() smaze zemi na pozici i v seznamu
        countries.remove(i);            // bez navratoveho typu
    }

    public void setCountry(Country country, int i) {    // metoda setCountry() upravuje zemi na pozici v seznamu
        countries.set(i, country);                      // bez navratove hodnoty
    }

    public Country getCountry(int i) {  // metoda getCountry() vraci zemi na pozici i v seznamu
        return countries.get(i);         // navratovy typ objekt Country
    }

    public int numberOfCountries() {    // metoda numberOfCountries() vraci pocet zemi v seznamu
        return countries.size();        // navratovy typ int
    }

    public void deleteAll() {    // metoda clearAll() smaze vsechny zemi ze seznamu
        countries.clear();      // bez navratoveho typu
    }

    /**
     * metody pro ukladani a cteni dat do/ze seznamu
     */
    // ukladani dat do seznamu
    public void upload(OutputStream outputStream) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(countries);
    }

    // nacitani dat ze seznamu
    public void download(InputStream inputStream) throws IOException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            countries = (ArrayList<Country>) objectInputStream.readObject();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }

    public void sort() {
        Comparator<Country> comparator = new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                return Collator.getInstance().compare(c1.getName(), c2.getName());
            }
        };
        countries = (ArrayList<Country>) countries.stream().sorted(comparator).collect(Collectors.toList());
    }

    public void find() {
        String finder = String.valueOf(JOptionPane.showInputDialog("Find item"));
        countries = (ArrayList<Country>) countries.stream()
                .filter((c0) -> c0.getName().startsWith(finder)
                        || c0.getCapitol().startsWith(finder)
                        || c0.getCurrency().startsWith(finder)
                        || c0.getDomain().startsWith(finder))
                .collect(Collectors.toList());
    };
}