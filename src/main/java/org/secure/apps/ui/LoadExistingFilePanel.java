package org.secure.apps.ui;

import org.secure.apps.service.AESEncryptionService;
import org.secure.apps.service.FileStorageService;
import org.secure.apps.service.SecureContentService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static org.secure.apps.utils.PanelServiceUtils.*;

public class LoadExistingFilePanel extends JPanel {

    private final SecureContentService secureContentService;
    private final FileStorageService fileStorageService;

    public LoadExistingFilePanel() {
        this.secureContentService = new AESEncryptionService();
        this.fileStorageService = new FileStorageService();

        setLayout(new GridBagLayout());
        GridBagConstraints gbcPanel2 = new GridBagConstraints();
        gbcPanel2.insets = new Insets(10, 10, 10, 10);

        JLabel fileNameLabelPanel2 = new JLabel("File Name:");
        gbcPanel2.gridx = 0;
        gbcPanel2.gridy = 0;
        gbcPanel2.anchor = GridBagConstraints.WEST;
        add(fileNameLabelPanel2, gbcPanel2);

        List<String> fileNames = getFileNames();
        JComboBox<String> fileNameDropdown = new JComboBox<>(fileNames.toArray(new String[0]));
        gbcPanel2.gridx = 1;
        gbcPanel2.gridy = 0;
        gbcPanel2.fill = GridBagConstraints.HORIZONTAL;
        add(fileNameDropdown, gbcPanel2);

        JButton deleteButton = new JButton("Delete File");
        gbcPanel2.gridx = 2;
        gbcPanel2.gridy = 0;
        gbcPanel2.anchor = GridBagConstraints.WEST;
        add(deleteButton, gbcPanel2);
        deleteButton.addActionListener(e -> {
            String selectedFileName = (String) fileNameDropdown.getSelectedItem();
            deleteFile(selectedFileName);
        });

        JLabel passwordLabelPanel = new JLabel("Password:");
        gbcPanel2.gridx = 0;
        gbcPanel2.gridy = 1;
        gbcPanel2.anchor = GridBagConstraints.WEST;
        add(passwordLabelPanel, gbcPanel2);

        JPasswordField passwordFieldPanel = new JPasswordField(20);
        gbcPanel2.gridx = 1;
        gbcPanel2.gridy = 1;
        gbcPanel2.fill = GridBagConstraints.HORIZONTAL;
        add(passwordFieldPanel, gbcPanel2);
        passwordFieldPanel.addActionListener(e -> {
            String selectedFileName = (String) fileNameDropdown.getSelectedItem();
            String password = new String(passwordFieldPanel.getPassword());
            loadFile(selectedFileName, password);
        });

        JButton createButtonPanel = new JButton("Load File");
        gbcPanel2.gridx = 0;
        gbcPanel2.gridy = 2;
        gbcPanel2.gridwidth = 2;
        gbcPanel2.anchor = GridBagConstraints.CENTER;
        add(createButtonPanel, gbcPanel2);
        createButtonPanel.addActionListener(e -> {
            String selectedFileName = (String) fileNameDropdown.getSelectedItem();
            String password = new String(passwordFieldPanel.getPassword());
            loadFile(selectedFileName, password);
        });

        JButton backToMainBtn = new JButton("Back to Main");
        gbcPanel2.gridx = 0;
        gbcPanel2.gridy = 3;
        gbcPanel2.gridwidth = 2;
        gbcPanel2.anchor = GridBagConstraints.CENTER;
        add(backToMainBtn, gbcPanel2);
        backToMainBtn.addActionListener(e -> appendPanelToMainFramePanel(getByName("mainPagePanel")));

    }

    private void loadFile(String selectedFileName, String password) {
        try {
            byte[] content = fileStorageService.readFileContent(selectedFileName);
            String decryptedContent = secureContentService.decrypt(content, password);
            JPanel panel = new SecureFileContentPanel(selectedFileName, password, decryptedContent);
            appendPanelToMainFramePanel(panel);
        } catch (Exception e1) {
            showErrorDialog(getByName("mainFramePanel"), "Error", "Unable to load the file. Please check the password");
        }
    }

    private void deleteFile(String selectedFileName) {
        if (selectedFileName != null && !selectedFileName.isBlank()) {
            showConfirmationDialog(getByName("mainFramePanel"), "Delete File",
                    "Are you sure you want to delete the file ?", () -> {
                        try {
                            fileStorageService.deleteFile(selectedFileName); // Implement delete logic
                            reloadSecureFileDropBox(getByName("loadExistingFilePanel"), fileStorageService.findFileNames());
                            showInfoMessage(LoadExistingFilePanel.this, "Success", "File deleted..");
                        } catch (Exception ex) {
                            showErrorDialog(LoadExistingFilePanel.this, "Error", "Unable to delete the file.");
                        }
                    });


        }
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
