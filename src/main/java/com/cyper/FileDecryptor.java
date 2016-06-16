package com.cyper;

import com.cyper.algorithm.AbstractAlgorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by misirghi on 3/28/2016.
 */
public class FileDecryptor {
    String inputFile;
    String outputFile;
    public FileDecryptor(String inputFile, String outputFile) throws Exception {
        //super(inputFile, outputFile);
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void decript(String password, AbstractAlgorithm algorithm) throws Exception {
        CipherHelper helper = new CipherHelper
                .Builder()
                .withCiperType(Cipher.DECRYPT_MODE)
                .withAlgorithm(algorithm)
                .withPassword(password)
                .withInputFile(inputFile)
                .withOutputFile(outputFile)
                .build();

        helper.perform();
    }
}
