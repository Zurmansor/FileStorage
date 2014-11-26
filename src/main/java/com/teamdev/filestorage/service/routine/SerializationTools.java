package com.teamdev.filestorage.service.routine;


import com.teamdev.filestorage.service.Configuration;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializationTools {
    private static Logger LOG = Logger.getLogger(SerializationTools.class.getName());

    public HashMap<String, Long> getExpirationTimeList() {
        return deserializeExpirationTimeList();
    }

    /**
     * entering a new item to the ExpirationTimeList
     * @param key
     * @param expirationTempMillis
     */
    public void putToExpirationTimeList(String key, long expirationTempMillis){

        HashMap<String, Long> expirationTimeList = deserializeExpirationTimeList();
        expirationTimeList.put(key, expirationTempMillis);
        if (LOG.isLoggable(Level.INFO)) {
            LOG.log(Level.INFO, "ExpirationTimeList size: " + expirationTimeList.size());
        }
        serializeExpirationTimeList(expirationTimeList);
    }

    /**
     * serialize ExpirationTimeList
     * @param expirationTimeList
     */
    private void serializeExpirationTimeList(HashMap<String, Long> expirationTimeList){
        Configuration configuration = new Configuration();
        try
        {
            FileOutputStream fos =
                    new FileOutputStream(configuration.getRootPath() + "/" + configuration.getExpirationTimeList());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(expirationTimeList);
            oos.close();
            fos.close();
            if (LOG.isLoggable(Level.INFO)) {
                LOG.log(Level.INFO, "ExpirationTimeList has been serialized");
            }
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /**
     *deserialize ExpirationTimeList
     * @return expirationTimeList in format HashMap
     */
    private HashMap<String,Long> deserializeExpirationTimeList() {
        Configuration configuration = new Configuration();
        HashMap<String, Long> expirationTimeList = new HashMap<String, Long>();
        try {
            FileInputStream fis = new FileInputStream(configuration.getRootPath() + "/" + configuration.getExpirationTimeList());
            ObjectInputStream ois = new ObjectInputStream(fis);
            expirationTimeList = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
//            ioe.printStackTrace();
            return expirationTimeList;
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            return expirationTimeList;
        }
        if (LOG.isLoggable(Level.INFO)) {
            LOG.log(Level.INFO, "ExpirationTimeList has been deserialized");
        }

        return expirationTimeList;
    }

    /**
     * Update dead List
     * @param tempExpirationTimeList
     */
    public void expirationTimeListUpdate(HashMap<String, Long> tempExpirationTimeList){
        HashMap<String, Long> expirationTimeList = getExpirationTimeList();

        for (String key : tempExpirationTimeList.keySet()) {
            if (expirationTimeList.containsKey(key)){
                expirationTimeList.remove(key);
            }
        }

        serializeExpirationTimeList(expirationTimeList);
    }
}
