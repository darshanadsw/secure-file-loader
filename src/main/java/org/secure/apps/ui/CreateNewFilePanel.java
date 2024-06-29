package org.secure.apps.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.secure.apps.utils.PanelServiceUtils.*;

public class CreateNewFilePanel extends JPanel {

    public CreateNewFilePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel fileNameLabel = new JLabel("File Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(fileNameLabel, gbc);

        JTextField fileNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(fileNameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);

        JButton createButton = new JButton("Create File");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createButton, gbc);
        createButton.addActionListener(e -> {
            String fileName = fileNameField.getText();
            String password = new String(passwordField.getPassword());
            createNewFile(fileName, password);
        });

        JButton backToMainBtn = new JButton("Back to Main");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backToMainBtn, gbc);
        backToMainBtn.addActionListener(e -> appendPanelToMainFramePanel(getByName("mainPagePanel")));
    }

    private void createNewFile(String fileName, String password) {
        if (Objects.isNull(fileName) || password.isBlank() || fileName.isBlank()) {
            showErrorDialog(getByName("mainFramePanel"), "Error", "file name and password should not be blank");
        } else {
            JPanel panel = new SecureFileContentPanel(fileName, password, "");
            appendPanelToMainFramePanel(panel);
        }
    }
}
