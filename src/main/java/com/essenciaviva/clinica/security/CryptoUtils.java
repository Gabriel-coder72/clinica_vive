package com.essenciaviva.clinica.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Component
public class CryptoUtils {

    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 12;

    @Value("${app.security.encryption-key}")
    private String encryptionKeyHex;

    @Value("${app.security.hmac-secret}")
    private String hmacSecretHex;

    // Criptografa usando AES-256-GCM com IV único de 12 bytes
    public byte[] encrypt(String plaintext) throws Exception {
        if (plaintext == null) return null;

        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        SecretKey key = new SecretKeySpec(hexToBytes(encryptionKeyHex), "AES");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        byte[] cipherText = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);
        return byteBuffer.array();
    }

    // Descriptografa os dados armazenados no banco
    public String decrypt(byte[] encryptedData) throws Exception {
        if (encryptedData == null || encryptedData.length == 0) return null;

        ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedData);
        byte[] iv = new byte[GCM_IV_LENGTH];
        byteBuffer.get(iv);
        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);

        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        SecretKey key = new SecretKeySpec(hexToBytes(encryptionKeyHex), "AES");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        byte[] decryptedText = cipher.doFinal(cipherText);
        return new String(decryptedText, StandardCharsets.UTF_8);
    }

    // Gera o HMAC-SHA256 do CPF (somente dígitos) para buscas
    public String generateCpfHmac(String rawCpf) throws Exception {
        String cleanCpf = rawCpf.replaceAll("\\D", "");
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(hexToBytes(hmacSecretHex), "HmacSHA256");
        sha256HMAC.init(secretKey);
        byte[] hash = sha256HMAC.doFinal(cleanCpf.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}