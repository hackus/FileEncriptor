package com.cyper.algorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

/**
 * Created by misirghi on 4/3/2016.
 */
public abstract class AbstractAlgorithm {
    public final String keyAlgorithm;
    public final String transformation;
    public final int passwordLength;
    public Cipher cipher;

    public byte[] salt;

    protected AbstractAlgorithm(String algorithm, String transformation, int passwordLength) {
        this.keyAlgorithm = algorithm;
        this.transformation = transformation;
        this.passwordLength = passwordLength;

    }

    public abstract Cipher getCipher(int cipherType, Key secretKey);

    public abstract int getSaltLength(String password);

    public abstract Key getKey(String password, FileInputStream inputFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException;

    public byte[] getSalt(String password, FileInputStream inputFile) throws IOException {
        if(salt == null) {
            if(inputFile == null){
                salt = new byte[getSaltLength(password)];

                Random random = new Random();
                random.nextBytes(salt);
            } else {
                salt = new byte[getSaltLength(password)];
                inputFile.read(salt);
            }
        }
        return salt;
    }
}
