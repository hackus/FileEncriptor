package com.cyper;

import com.cyper.algorithm.AbstractAlgorithm;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

/**
 * Created by misirghi on 3/28/2016.
 */
public class CipherHelper {
    FileInputStream inFile = null;
    FileOutputStream outFile = null;

    private Cipher cipher;
    AbstractAlgorithm algorithm;

    private CipherHelper(int cipherType,
                         AbstractAlgorithm algorithm,
                         String password,
                         String inputFile,
                         String outputFile) throws Exception {
        try {
            inFile = new FileInputStream(inputFile);
            outFile = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            throw new Exception("File not found." + e.getMessage());
        }

        Key secretKey = algorithm.getKey(password, inFile);
        cipher = algorithm.getCipher(cipherType, secretKey);
    }

    public Cipher getCipher() throws Exception {
        if (cipher == null) {
            throw new Exception("Cipher cannot be null.");
        }
        return cipher;
    }

    protected void writeBytesToOutput(byte[] value) throws IOException {
        outFile.write(value);
    }

    protected void perform() throws Exception {
        byte[] input = new byte[64];
        int bytesRead;
        try {
            while ((bytesRead = inFile.read(input)) != -1) {
                byte[] output = getCipher().update(input, 0, bytesRead);
                if (output != null) {
                    writeBytesToOutput(output);
                }
            }

            byte[] output = getCipher().doFinal();

            if (output != null) {
                writeBytesToOutput(output);
            }
        } catch (IOException e) {
            throw new Exception("Writing to file throws an error." + e.getMessage());
        } finally {
            outFile.flush();
            inFile.close();
            outFile.close();
        }
    }

    public static class Builder {
        private int cipherType;
        private String password;
        private AbstractAlgorithm algorithm;
        private String inputFile;
        private String outputFile;

        public CipherHelper build() throws Exception {
            CipherHelper helper = new CipherHelper(cipherType, algorithm, password, inputFile, outputFile);
            return helper;
        }

        public Builder withCiperType(int cipherType) {
            this.cipherType = cipherType;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withInputFile(String inputFile) {
            this.inputFile = inputFile;
            return this;
        }

        public Builder withOutputFile(String outputFile) {
            this.outputFile = outputFile;
            return this;
        }

        public Builder withAlgorithm(AbstractAlgorithm algorithm) {
            this.algorithm = algorithm;
            return this;
        }
    }
}
