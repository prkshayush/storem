package com.icici.FirstRestApiApplication.services;

import org.springframework.stereotype.Service;

import com.icici.FirstRestApiApplication.entities.PasswordEntry;
import com.icici.FirstRestApiApplication.repos.PasswordRepository;

import lombok.Data;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Data
@Service
public class PasswordService {

    private final PasswordRepository passwordRepository;
    private static final String SECRET_KEY = "MySuperSecretKey"; // 16 chars for AES

    // AES Encrypt
    private String encrypt(String strToEncrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting password", e);
        }
    }

    // AES Decrypt
    private String decrypt(String strToDecrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting password", e);
        }
    }

    // Save password securely
    public PasswordEntry savePassword(PasswordEntry passwordEntity) {
        passwordEntity.setPassword(encrypt(passwordEntity.getPassword()));
        return passwordRepository.save(passwordEntity);
    }

    // Get password (with decryption)
    public String getDecryptedPassword(Long id) {
        PasswordEntry entity = passwordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Password record not found"));
        return decrypt(entity.getPassword());
    }

    // Get all without revealing passwords
    public java.util.List<PasswordEntry> getAllPasswords() {
        return passwordRepository.findAll();
    }
}
