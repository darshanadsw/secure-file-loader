package org.secure.apps.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStorageService {

    private static final String ENCRYPTED_FILE_DIRECTORY = "files" + File.separator;

    public void saveContentFile(String fileName, byte[] encryptedBytes, byte[] defaultIv) throws IOException {
        String filePath = ENCRYPTED_FILE_DIRECTORY + fileName;

        Path dir = Path.of(ENCRYPTED_FILE_DIRECTORY);
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
        }
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(defaultIv);
            outputStream.write(encryptedBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readFileContent(String fileName) {
        try {
            String filePath = ENCRYPTED_FILE_DIRECTORY + fileName;
            return Files.readAllBytes(Paths.get(filePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> findFileNames() throws IOException {

        Path path = Paths.get(ENCRYPTED_FILE_DIRECTORY);
        if (Files.exists(path)) {
                List<String> files = new ArrayList<>();
                Files.walk(path)
                        .filter(Files::isRegularFile)
                        .forEach(file -> files.add(file.getFileName().toString()));
                return files;
        }
        return List.of();
    }

    public void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(Path.of(ENCRYPTED_FILE_DIRECTORY + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
