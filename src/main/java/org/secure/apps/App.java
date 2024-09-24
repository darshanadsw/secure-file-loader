package org.secure.apps;

import org.secure.apps.ui.MainFrame;

import javax.swing.SwingUtilities;

/**
 * Entry point of the application.
 * Initializes the main frame using SwingUtilities.invokeLater().
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}