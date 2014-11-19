package com.teamdev.service;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HashCalculator {
    private static Logger logger = Logger.getLogger(HashCalculator.class.getName());

    /**
     * Getting file checksum
     * @param path path to file to get hash
     * @return checksum
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String getHash(String path) throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream fis = new FileInputStream(path);
        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };

        byte[] mdbytes = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public  String getHashName (String name) throws NoSuchAlgorithmException {
//        logger.log(Level.INFO, "IN: " + name);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(name.getBytes());

        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();

        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

//        logger.log(Level.INFO, "OUT: " + sb.toString());

        return sb.toString();
    }
}
