package project.countries.ui;

import project.countries.data.Country;
import project.countries.data.Manager;
import project.countries.data.Table;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class MainFrame extends JFrame implements ActionListener {
    /**
     * propojeni tabulky se seznamem
     * data v tabulce
     */
    Manager items = new Manager();          // data
    Table pattern = new Table(items);       // prirazeni dat do tabulky
    JTable tblItems = new JTable(pattern);  // propojeni tabulky s datami

    /**
     * hlavni tlacitka
     * inicializace
     */
    JButton btnAddItem = new JButton("Add item");           // pridat polozku
    JButton btnChangeItem = new JButton("Change item");     // zmenit polozku
    JButton btnDeleteItem = new JButton("Delete item");     // smazat polozku
    JButton btnSortItem = new JButton("Sort countries");    // seradit polozky dle nazvu zeme
    JButton btnFindItem = new JButton("Find item");         // najit plozku

    /**
     * textove pole pro zadavani dat
     * inicilaizace
     */
    JTextField tfdName = new JTextField(20);        // nazev zeme
    JTextField tfdCapitol = new JTextField(20);     // hlavni mesto
    JTextField tfdCurrency = new JTextField(10);    // mena
    JTextField tfdDomain = new JTextField(5);       // internetova domena

    /**
     * akce
     */
    Action  actionNew,      // novy
            actionOpen,     // otevrit
            actionSave,     // ulozit
            actionExit;     // zavrit

    /**
     * definovani barev
     */
    public static final Color GRAY = new Color(200,200,200);
    public static final Color VERY_LIGHT_YELLOW = new Color(255,255,200);
    public static final Color VERY_LIGHT_GREEN = new Color(100,255,100);
    public static final Color VERY_LIGHT_ORANGE = new Color(255,220,200);
    public static final Color VERY_LIGHT_RED = new Color(255,100,100);
    public static final Color VERY_LIGHT_BLUE = new Color(50,200,255);

    /**
     * vychozi konstruktor bez parametru
     * inicializuje tridu MainFrame, tj. vytvori objekt tridy
     */
    public MainFrame() {
        setTitle("List of Europe countries");           // nastaveni popisku hlavniho okna

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // vypnuti aplikace po uzavreni okna

        runMainFrame();                                 // metoda spousti uzivetelske rozhrani
    }

    protected void runMainFrame(){
        /**
         * panel nastroju
         */
        // metoda definuje rolovatelne polozky v panely nastroju (novy, otevrit, ulozit, zavrit)
        setToolbar();

        // vytvari panel nastroju, inicilizace
        JToolBar jToolBar = runToolbar();
        add(jToolBar,"North");

        /**
         * menu
         * metoda vytvari menu
         */
        runMenu();

        /**
         * panely pro usporadani textoveho pole, tlacitek a tabulky
         */
        // inicializace hlavniho panelu aplikace
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, "Center");
        // nastaveni hranice
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black,2));

        // panel nahore
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        /**
         * editace textoveho pole
         * nastaveni textovych poli
         */
        FocusListener autoSelectListener = new FocusAdapter() {
            public void focusGained(FocusEvent focusEvente) {
                JTextComponent jTextComponent = (JTextComponent) focusEvente.getSource();
                jTextComponent.selectAll();
            }
        };

        // nazev zeme
        tfdName.addFocusListener(autoSelectListener);
        tfdName.setBorder(BorderFactory.createLineBorder(Color.black,1));
        tfdName.setBackground(VERY_LIGHT_YELLOW);
        tfdName.setToolTipText("Write name of country");

        // hlavni mesto
        tfdCapitol.addFocusListener(autoSelectListener);
        tfdCapitol.setBorder(BorderFactory.createLineBorder(Color.black,1));
        tfdCapitol.setBackground(VERY_LIGHT_YELLOW);
        tfdCapitol.setToolTipText("Write name of capitol");

        // mena
        tfdCurrency.addFocusListener(autoSelectListener);
        tfdCurrency.setBorder(BorderFactory.createLineBorder(Color.black,1));
        tfdCurrency.setBackground(VERY_LIGHT_YELLOW);
        tfdCurrency.setToolTipText("Write currency");

        // internetova domea
        tfdDomain.addFocusListener(autoSelectListener);
        tfdDomain.setBorder(BorderFactory.createLineBorder(Color.black,1));
        tfdDomain.setBackground(VERY_LIGHT_YELLOW);
        tfdDomain.setToolTipText("Write internet domain");

        /**
         * nastaveni hlavnich tlacitek
         * panel nahore
         */
        // vytvoreni objektu JPanel s nazvem inNorthPanelUp
        JPanel inNorthPanelUp = new JPanel();

        // pridani polozky do seznamu
        btnAddItem.addActionListener(this);
        btnAddItem.setMnemonic('A');
        btnAddItem.setBackground(VERY_LIGHT_GREEN);
        btnAddItem.setToolTipText("Fill item and then click on \"Add item\"");

        // zmena polozky do seznamu
        btnChangeItem.addActionListener(this);
        btnChangeItem.setMnemonic('C');
        btnChangeItem.setBackground(VERY_LIGHT_ORANGE);
        btnChangeItem.setToolTipText("Chose item, change it and than click on \"Change item\"");

        // smazani polozky v seznamu
        btnDeleteItem.addActionListener(this);
        btnDeleteItem.setMnemonic('D');
        btnDeleteItem.setBackground(VERY_LIGHT_RED);
        btnDeleteItem.setToolTipText("Chose item, and than click on \"Delete item\"");

        // serazeni seznamu dle nazvu zeme
        btnSortItem.addActionListener(this);
        btnSortItem.setMnemonic('S');
        btnSortItem.setBackground(VERY_LIGHT_BLUE);
        btnSortItem.setToolTipText("Sort list by name of country");

        // hledani zeme v seznamu
        btnFindItem.addActionListener(this);
        btnFindItem.setMnemonic('F');
        btnFindItem.setBackground(VERY_LIGHT_BLUE);
        btnFindItem.setToolTipText("Find item from the list");

        // pridani tlacitek do panelu
        inNorthPanelUp.add(btnAddItem);
        inNorthPanelUp.add(btnChangeItem);
        inNorthPanelUp.add(btnDeleteItem);
        inNorthPanelUp.add(btnSortItem);
        inNorthPanelUp.add(btnFindItem);

        /**
         * popisky (k textovemu poly)
         * vytvoreni popisku v panelu
         * panel dole
         */
        // vytvoreni objektu JPanel s nazvem inNorthPanelDown
        JPanel inNorthPanelDown = new JPanel();
        inNorthPanelDown.setLayout(new GridLayout(1,4,1,5));

//        inNorthPanelDown.add(new JLabel("Name"));               // nazev zeme
//        inNorthPanelDown.add(new JLabel("Capitol"));            // hlavni mesto
//        inNorthPanelDown.add(new JLabel("Currency"));           // mena
//        inNorthPanelDown.add(new JLabel("Internet domain"));    // internetova domena

        // pridani textovych poli do panelu
        inNorthPanelDown.add(tfdName);
        inNorthPanelDown.add(tfdCapitol);
        inNorthPanelDown.add(tfdCurrency);
        inNorthPanelDown.add(tfdDomain);

        /**
         * rozlozeni panelu
         */
        // panel (inNorthPanelUp) uvnitr nahore panelu (northPanel)
        northPanel.add(inNorthPanelUp);

        // panel (inNorthPanelDown) uvnitr dole panelu (northPanel)
        northPanel.add(inNorthPanelDown);

        // panel (northPanel) pridan nahoru do panelu (mainPanel)
        mainPanel.add(northPanel, "North");

        /**
         * tabulka
         */
        tblItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // po kliknuti na bunku ji oznaci
        tblItems
                .getSelectionModel()
                .addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int i = tblItems.getSelectedRow();
                        if (i != -1) {
                            Country country = items.getCountry(i);
                            tfdName.setText(country.getName());
                            tfdCapitol.setText(country.getCapitol());
                            tfdCurrency.setText(country.getCurrency());
                            tfdDomain.setText(country.getDomain());
                        }
                    }
                });
        tblItems.setToolTipText("List of countries");
        JScrollPane southJSP = new JScrollPane(tblItems);
        southJSP.setBorder(BorderFactory.createLineBorder(GRAY,2));
        mainPanel.add(southJSP, "Center");

        /**
         * ikona aplikace
         */
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(
                "/project/countries/icon/app.png")));
    }

    /**
     * vytvoreni a nastaveni panelu nastroju
     */
    // inicializace
    private JToolBar runToolbar() {
        JToolBar actionToolBar = new JToolBar("Tools", JToolBar.HORIZONTAL);
        actionToolBar.setFloatable(false);
        actionToolBar.add(actionNew);
        actionToolBar.add(actionOpen);
        actionToolBar.add(actionSave);

        return actionToolBar;
    }

    /**
     * nastaveni udalosti pro tlacitka a menu
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int i;

        if(e.getSource() == btnAddItem) {
            Country country = new Country(
                    tfdName.getText(),
                    tfdCapitol.getText(),
                    tfdCurrency.getText(),
                    tfdDomain.getText());
            items.addCountry(country);
            pattern.refresh();  // obnovi zobrazeni tabulky
            deleteItems();
            tfdName.grabFocus();
        } else if (e.getSource() == btnChangeItem) {
            if((i = tblItems.getSelectedRow()) != -1) {
                Country country = new Country(
                        tfdName.getText(),
                        tfdCapitol.getText(),
                        tfdCurrency.getText(),
                        tfdDomain.getText());
                items.setCountry(country, i);
                pattern.refresh();  // obnovi zobrazeni tabulky
            }
        } else if (e.getSource() == btnDeleteItem) {
            if((i = tblItems.getSelectedRow()) != -1) {
                items.deleteCountry(i);
                pattern.refresh();  // obnovi zobrazeni tabulky
            }
        } else if (e.getSource() == btnSortItem) {
            items.sort();
            pattern.refresh();  // obnovi zobrazeni tabulky
        } else if (e.getSource() == btnFindItem) {
            items.find();
            pattern.refresh();  // obnovi zobrazeni tabulky
        }
    }

    /**
     * metoda pro smazani polozek
     */
    private void deleteItems() {
        tfdName.setText("");
        tfdCapitol.setText("");
        tfdCurrency.setText("");
        tfdDomain.setText("");
    }

    /**
     * metoda pro zavreni aplikace
     */
    private void exit() {
        System.exit(0);
    }

    // metoda setToolbar() pro nastaveni akci
    private void setToolbar() {
        // novy soubor
        actionNew = new AbstractAction("New",
                new ImageIcon(getClass().getResource("/project/countries/icon/new.png")) ) {
            @Override
            public void actionPerformed(ActionEvent e) {
                items.deleteAll();
                pattern.refresh();
                deleteItems();
            }
        };
        actionNew.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('N',InputEvent.CTRL_MASK ));
        actionNew.putValue(Action.SHORT_DESCRIPTION, "Create new file");

        // otevrit soubor
        actionOpen = new AbstractAction("Open",
                new ImageIcon(getClass().getResource("/project/countries/icon/open.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
                pattern.refresh();
            }
        };
        actionOpen.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('O',InputEvent.CTRL_MASK ));
        actionOpen.putValue(Action.SHORT_DESCRIPTION,"Open file");

        // ulozit soubor
        actionSave = new AbstractAction("Save As",
                new ImageIcon(getClass().getResource("/project/countries/icon/save.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        };
        actionSave.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('S',InputEvent.CTRL_MASK ));
        actionSave.putValue(Action.SHORT_DESCRIPTION,"Save data into the file");

        // zavreni aplikace
        actionExit = new AbstractAction("Close",
                new ImageIcon(getClass().getResource("/project/countries/icon/exit.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        };
        actionExit.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_MASK ));
        actionExit.putValue(Action.SHORT_DESCRIPTION,"Close application");
    }

    /**
     * vytvoreni a nastaveni menu
     */
    private void runMenu() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu jMenu = new JMenu("File");

        jMenu.add(actionNew);
        jMenu.add(actionOpen);
        jMenu.add(actionSave);
        jMenu.addSeparator();
        jMenu.add(actionExit);

        jMenuBar.add(jMenu);
        setJMenuBar(jMenuBar);
    }

    // metoda otevira soubour
    private void open() {
        JFileChooser jFileChooserOpen = new FileManager();
        if (jFileChooserOpen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fileInputStream = new FileInputStream(jFileChooserOpen.getSelectedFile());
                items.download(fileInputStream);
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this,"File cannot be open","Error message",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    // metoda uklada soubor
    private void save() {
        JFileChooser jFileChooserSave = new FileManager();
        if (jFileChooserSave.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String file = jFileChooserSave.getSelectedFile().getAbsolutePath();
                if (!file.endsWith(".table"))
                    file += ".table";
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                items.upload(fileOutputStream);
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this,"File cannot be save","Error message",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (IOException e) {e.printStackTrace();}
        }
    }
}