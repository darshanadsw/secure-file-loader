package org.secure.apps.ui;

import org.secure.apps.service.PanelLoaderService;

import javax.swing.*;

import static org.secure.apps.utils.PanelServiceUtils.addPanel;


public class MainFrame extends JFrame {

    public MainFrame() {
        PanelLoaderService.getInstance().load();
        setTitle("Secure File Loader");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new MainFramePanel();

        addPanel("mainFramePanel", mainPanel);

        add(mainPanel);

        setVisible(true);
    }
}
