package com.teamdev.service;

import java.io.*;
import java.util.*;

public class FileStorageContext {
    Configuration configuration = new Configuration();
    private final String REGISTER = configuration.getRotPath() + "/register";
    private final String REGISTER_SEPARATOR = " ";

    private LinkedHashMap<String, Long> fileRegister = new LinkedHashMap<String, Long>();

    public LinkedHashMap<String, Long> getFileRegister() {
        return fileRegister;
    }

    public FileStorageContext() throws IOException {
//        syncToRegister();
    }

    public void syncToFile () throws IOException {
        // clear file
        FileWriter writer = new FileWriter(REGISTER);
        writer.write("");

        //write to file all items of collection
        writer = new FileWriter(REGISTER, true);
        for (String key : fileRegister.keySet()) {
            writer.write(key + REGISTER_SEPARATOR + fileRegister.get(key) + "\n");
        }
        writer.close();
    }

    public void syncToRegister () throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(REGISTER));
        String line;
        while ((line = br.readLine()) != null) {
            String[] item = line.split(REGISTER_SEPARATOR);
            fileRegister.put(item[0], Long.valueOf(item[1]));
        }
        br.close();
    }

}
