package org.secure.apps.ui;

import org.secure.apps.service.AESEncryptionService;
import org.secure.apps.service.ContentSearchService;
import org.secure.apps.service.FileStorageService;
import org.secure.apps.service.SecureContentService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.secure.apps.utils.PanelServiceUtils.*;

public class SecureFileContentPanel extends JPanel {

    private final ContentSearchService contentSearchService;
    private final SecureContentService secureContentService;
    private final FileStorageService fileStorageService;

    public SecureFileContentPanel(String fileName, String password, String content) {
        this.contentSearchService = new ContentSearchService();
        this.secureContentService = new AESEncryptionService();
        this.fileStorageService = new FileStorageService();
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText();
            doSearch(searchQuery, textArea);
        });
        searchField.addActionListener(e -> {
            String searchQuery = searchField.getText();
            doSearch(searchQuery, textArea);
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveFile(fileName, password, textArea.getText()));

        JButton backToMainButton = new JButton("Back to Main");
        backToMainButton.addActionListener(e -> appendPanelToMainFramePanel(getByName("mainPagePanel")));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(backToMainButton);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        textArea.append(content);


    }

    private void doSearch(String searchQuery, JTextArea textArea) {
        removeHighlights(textArea);
        List<int[]> searchResult = contentSearchService
                .search(textArea.getText(), searchQuery);
        highlightContent(textArea, searchResult);
    }

    private void saveFile(String fileName, String password, String content) {

        showConfirmationDialog(getByName("mainFramePanel"), "Save Confirmation",
                "Are you sure you want to save?", () -> {
                    try {
                        byte[] encryptedContent = secureContentService.encrypt(content, password);
                        fileStorageService.saveContentFile(fileName, encryptedContent, secureContentService.getIv());
                        showInfoMessage(SecureFileContentPanel.this, "Success", "File successfully saved..");
                    } catch (Exception ex) {
                        showErrorDialog(getByName("mainFramePanel"), "Error", "Unable to save content");
                        throw new RuntimeException(ex);
                    }
                });
    }
}
