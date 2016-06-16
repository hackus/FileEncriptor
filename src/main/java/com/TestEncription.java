package com;

import com.cyper.FileDecryptor;
import com.cyper.FileEncryptor;
import com.cyper.algorithm.AES;
import com.cyper.algorithm.AbstractAlgorithm;
import com.cyper.algorithm.PBEWithMD5AndDESAlgo;

/**
 * Created by misirghi on 3/28/2016.
 */
public class TestEncription {
    public static void main(String[] args) throws Exception {
        FileEncryptor encriptor = new FileEncryptor("test.txt", "test.des");
        FileDecryptor decriptor = new FileDecryptor("test.des", "testAux.txt");
        String password = "java123123jugkjhgkjhgkjhgkj";

        AbstractAlgorithm encriptionAlgorithm = getAlgorithm();

        encriptor.encript(password, encriptionAlgorithm);

        encriptionAlgorithm = getAlgorithm();

        decriptor.decript(password, encriptionAlgorithm);
    }

    public static AbstractAlgorithm getAlgorithm(){
        return new PBEWithMD5AndDESAlgo();
    }
}
