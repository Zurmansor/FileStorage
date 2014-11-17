package com.teamdev.service.serialization;


import com.teamdev.service.Configuration;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializationTools {
    private static Logger logger = Logger.getLogger(SerializationTools.class.getName());

    public HashMap<String, Long> getDeadList() {
        return deserializeDeadList();
    }

    public void putToDeadList(String origName, long expTempMils){

        HashMap<String, Long> deadList = deserializeDeadList();
        deadList.put(origName, expTempMils);
        logger.log(Level.INFO, "DeadList size: " + deadList.size());
        serializeDeadList(deadList);
    }

    private void serializeDeadList(HashMap<String, Long> deadList){
        Configuration configuration = new Configuration();
        try
        {
            FileOutputStream fos =
                    new FileOutputStream(configuration.getRotPath() + "/" + configuration.getDeadList());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(deadList);
            oos.close();
            fos.close();
            logger.log(Level.INFO, "DeadList has been serialized");
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    private HashMap<String,Long> deserializeDeadList() {
        Configuration configuration = new Configuration();
        HashMap<String, Long> deadList = null;
        try {
            FileInputStream fis = new FileInputStream(configuration.getRotPath() + "/" + configuration.getDeadList());
            ObjectInputStream ois = new ObjectInputStream(fis);
            deadList = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
//            ioe.printStackTrace();
            return deadList;
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            return deadList;
        }

        logger.log(Level.INFO, "DeadList has been deserialized");

        return deadList;
    }

    public void deadListUpdate(HashMap<String, Long> tempDeadList){
        HashMap<String, Long> deadList = getDeadList();
        HashMap<String, Long> newDeadList = new HashMap<String, Long>();

        for (String origName : deadList.keySet()) {
            // если временный файл не был удален, помешаем его в новый список
            if (!tempDeadList.containsKey(origName)){
                newDeadList.put(origName, deadList.get(origName));
            }
        }

//        сохраняем новый список
        serializeDeadList(newDeadList);
    }
}
