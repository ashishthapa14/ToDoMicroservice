package com.project.utility;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptUtils {
    private final BasicTextEncryptor textEncryptor;

    public EncryptUtils() {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("SecretEncryptionKey123");
    }

    public String encrypt(String data) {
        return textEncryptor.encrypt(data);
    }

    public String decrypt(String encryptData) {
        return textEncryptor.decrypt(encryptData);
    }
}
