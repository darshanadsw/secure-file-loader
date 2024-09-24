package org.secure.apps.utils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelServiceUtils {

    private static final Map<String, JPanel> PANELS = new HashMap<>();

    public static void addPanel(String name, JPanel panel) {
        PANELS.put(name, panel);
    }

    public static JPanel getByName(String name) {
        return PANELS.get(name);
    }

    /**
     * Resets the text fields within the provided JPanel by setting their text to an empty string.
     * 
     * @param panel the JPanel containing the text fields to be reset
     */
    public static void resetFields(JPanel panel) {
        if (panel != null) {
            Arrays.stream(panel.getComponents())
                    .filter(JTextField.class::isInstance)
                    .forEach(component -> ((JTextField) component).setText(""));
        }
    }

    /**
 * Reloads the items in a JComboBox component within the provided JPanel with the given list of file names.
 * If the panel is not null, iterates through the components of the panel, clears the existing items in any JComboBox,
 * and adds each file name from the list as a new item to the JComboBox.
 *
 * @param panel the JPanel containing the JComboBox components to be reloaded
 * @param fileNames the list of file names to be added to the JComboBox components
 */
    public static void reloadSecureFileDropBox(JPanel panel, List<String> fileNames) {
        if (panel != null) {
            Component[] components = panel.getComponents();
            for (Component component : components) {
                if (component instanceof JComboBox<?>) {
                    ((JComboBox) component).removeAllItems();
                    fileNames.forEach(((JComboBox) component)::addItem);
                }
            }
        }
    }

    public static void showErrorDialog(JPanel mainPanel, String title, String message) {
        JOptionPane.showMessageDialog(mainPanel, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoMessage(JPanel mainPanel, String title, String message) {
        JOptionPane.showMessageDialog(mainPanel, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static void showConfirmationDialog(JPanel parentPanel, String title, String message, Runnable task) {
        int option = JOptionPane.showConfirmDialog(parentPanel,
                message, title, JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            task.run();
        }
    }

    public static void removeHighlights(JTextArea textArea) {
        Highlighter highlighter = textArea.getHighlighter();
        Highlighter.Highlight[] highlights = highlighter.getHighlights();

        for (Highlighter.Highlight highlight : highlights) {
            if (highlight.getPainter() instanceof DefaultHighlighter.DefaultHighlightPainter) {
                highlighter.removeHighlight(highlight);
            }
        }
    }

    public static void highlightContent(JTextArea textArea, List<int[]> searchResult) {
        Highlighter highlighter = textArea.getHighlighter();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

        searchResult.forEach(a -> {
            try {
                highlighter.addHighlight(a[0], a[1], painter);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        });
        textArea.requestFocusInWindow();
    }

    public static void appendPanelToMainFramePanel(JPanel panel){
        JPanel mainFramePanel = getByName("mainFramePanel");
        mainFramePanel.removeAll();
        mainFramePanel.add(panel, BorderLayout.CENTER);
        mainFramePanel.revalidate();
        mainFramePanel.repaint();
    }

}
