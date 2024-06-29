package org.secure.apps.ui;

import org.secure.apps.service.FileStorageService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static org.secure.apps.utils.PanelServiceUtils.*;


public class MainPagePanel extends JPanel {

    private final FileStorageService fileStorageService;

    public MainPagePanel() {
        this.fileStorageService = new FileStorageService();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton createSecureFileBtn = new JButton("Create Secure File");
        createSecureFileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        createSecureFileBtn.addActionListener(e -> openCreateNewFilePanel());

        JButton loadSecureFileBtn = new JButton("Load Secure File");
        loadSecureFileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadSecureFileBtn.addActionListener(e -> openLoadSecureFilePanel());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(createSecureFileBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(loadSecureFileBtn);
        buttonPanel.add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());
        add(buttonPanel);
        add(Box.createVerticalGlue());

    }

    private void openLoadSecureFilePanel() {
        JPanel loadExistingFilePanel = getByName("loadExistingFilePanel");
        resetFields(loadExistingFilePanel);
        List<String> fileNames = getFileNames();
        reloadSecureFileDropBox(loadExistingFilePanel, fileNames);
        appendPanelToMainFramePanel(loadExistingFilePanel);
    }

    private void openCreateNewFilePanel() {
        JPanel createNewFilePanel = getByName("createNewFilePanel");
        resetFields(createNewFilePanel);
        appendPanelToMainFramePanel(createNewFilePanel);
    }

    private List<String> getFileNames() {
        try {
            return fileStorageService.findFileNames();
        } catch (IOException e) {
            showErrorDialog(getByName("mainFramePanel"), "Error", "Unable to load the files");
        }
        return List.of();
    }

}
