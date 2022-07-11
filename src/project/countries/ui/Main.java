package project.countries.ui;

import javax.swing.*;

import static java.awt.Frame.MAXIMIZED_BOTH;

/**
 * Spousteci trida
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jfMain = new MainFrame();

                jfMain.setExtendedState(MAXIMIZED_BOTH);
                //jfMain.pack();
                jfMain.setVisible(true);
            }
        });
    }
}
