package com.cyper.algorithm;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Created by misirghi on 4/3/2016.
 */
public class PBEWithMD5AndDESAlgo extends AbstractAlgorithm {
    int iterationCount = 19;
    public PBEWithMD5AndDESAlgo() {
        super("PBEWithMD5AndDES", "PBEWithMD5AndDES", 16);
    }

    @Override
    public Cipher getCipher(int cipherType, Key secretKey) {
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        if(cipher == null) {
            try {
                cipher = Cipher.getInstance(transformation);
                cipher.init(cipherType, secretKey, paramSpec);
            } catch (NoSuchPaddingException e) {
                System.out.println("EXCEPTION: NoSuchPaddingException");
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                System.out.println("EXCEPTION: NoSuchAlgorithmException");
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                System.out.println("EXCEPTION: InvalidKeyException");
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                System.out.println("EXCEPTION: InvalidAlgorithmParameterException");
                e.printStackTrace();
            }
        }
        return cipher;
    }

    @Override
    public int getSaltLength(String password) {
        int saltLength = 8;
        return saltLength;
    }

    @Override
    public Key getKey(String password, FileInputStream inputFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        salt = getSalt(password, inputFile);

        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance(keyAlgorithm).generateSecret(keySpec);

        return key;
    }
}
