package com.teamdev.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public interface FileStorage {

    public void saveFile(String key, FileInputStream fileInputStream) throws Exception;
   // TODO: saveFile(String key, FileInputStream fileInputStream, long expTempMils)

    public void deleteFile(String key);

    public File searchFile(String key);

    public InputStream readFile(String key);

    public void purge(Integer percent);

}
