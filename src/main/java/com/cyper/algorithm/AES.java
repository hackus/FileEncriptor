package com.cyper.algorithm;

import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by misirghi on 4/3/2016.
 */
public class AES extends AbstractAlgorithm {
    public AES() {
        super("AES", "AES", 16);    }

    @Override
    public Cipher getCipher(int cipherType, Key secretKey) {
        if(cipher == null) {
            try {
                cipher = Cipher.getInstance(transformation);
                cipher.init(cipherType, secretKey);
            } catch (NoSuchPaddingException e) {
                System.out.println("EXCEPTION: NoSuchPaddingException");
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                System.out.println("EXCEPTION: NoSuchAlgorithmException");
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                System.out.println("EXCEPTION: InvalidKeyException");
                e.printStackTrace();
            }
        }
        return cipher;
    }

    @Override
    public int getSaltLength(String password) {
        int saltLength = passwordLength - password.length() % passwordLength;
        return saltLength;
    }
    @Override
    public Key getKey(String password, FileInputStream inputFile) throws IOException {
        salt = getSalt(password, inputFile);
        byte[] pass = ArrayUtils.addAll(password.getBytes(), salt);

        Key secretKey;
        secretKey = new SecretKeySpec(pass, keyAlgorithm);
        return secretKey;
    }


}
