package project.countries.data;

import javax.swing.table.AbstractTableModel;

/**
 * trida, ktera zabezpecuje zobrazeni dat v tabulce
 * trida Table dedi od tridy AbstractTableModel, ktera obsahuje komponentu JTable
 */
public class Table extends AbstractTableModel {
    /**
     * odkaz na seznam zemi ve tride Manager
     * odkaz na objekt Manager se seznamem zemi
     */
    Manager countries;

    /**
     * konstruktor tridy Table s parametrem odkazujici na tridu Manager
     * inicializuje tridu Manager se seznamem zemi
     */
    public Table(Manager manager) {
        countries = manager;
    }

    /**
     * zakladni definice tabulky JTable
     * metody definujici tabulku
     */
    // nazvy sloupcu tabulky pomoci pole stringu
    String[] columns = {
            "Country",
            "Capitol",
            "Currency",
            "Internet domain"
    };

    // prizazeni nazvu do sloupcu tabulky
    public String getColumnName(int i) {
        return columns[i];
    }

    // pocet radku tabulky
    // vraci odkaz na seznam zemi a jejich pocet
    @Override
    public int getRowCount() {
        return countries.numberOfCountries();
    }

    // pocet sloupcu tabulky
    // pozn. mame 4 promenne
    @Override
    public int getColumnCount() {
        return 4;
    }

    // vraci hodnotu bunky z tabulky
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String item = null;

        // mapuje odpovidajici radek
        Country country = countries.getCountry(rowIndex);

        // mapuje odpovidajici sloupec
        switch (columnIndex) {
            case 0:
                item = country.getName();
                break;
            case 1:
                item = country.getCapitol();
                break;
            case 2:
                item = country.getCurrency();
                break;
            case 3:
                item = country.getDomain();
                break;
        }
        return item;
    }

    // obnoveni polozek v tabulce
    // obnovi data v tabulce a informuje JTable o zmenach
    public void refresh() {
        fireTableDataChanged();
    }
}