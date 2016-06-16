package com.cyper;

import com.cyper.algorithm.AbstractAlgorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.util.Random;

/**
 * Created by misirghi on 3/28/2016.
 */
public class FileEncryptor {
    String inputFile;
    String outputFile;

    public FileEncryptor(String inputFile, String outputFile) throws Exception {
        //super(inputFile, outputFile);
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void encript(String password, AbstractAlgorithm algorithm) throws Exception {

        CipherHelper helper = new CipherHelper
                .Builder()
                .withCiperType(Cipher.ENCRYPT_MODE)
                .withAlgorithm(algorithm)
                .withPassword(password)
                .withInputFile(inputFile)
                .withOutputFile(outputFile)
                .build();


        helper.writeBytesToOutput(algorithm.getSalt(password, null));

        helper.perform();
    }
}
