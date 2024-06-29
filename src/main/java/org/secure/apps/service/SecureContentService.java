package org.secure.apps.service;

public interface SecureContentService {

    byte[] encrypt(String data, String password) throws Exception;

    String decrypt(byte[] encryptedBytes, String password) throws Exception;

    byte[] getIv();
}
