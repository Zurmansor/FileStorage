package com.teamdev.service.serialization;


import com.teamdev.service.Configuration;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializationTools {
    private static Logger logger = Logger.getLogger(SerializationTools.class.getName());

    public HashMap<String, Long> getDeadList() {
        return deserializeDeadList();
    }

    /**
     * entering a new item to the DeadList
     * @param origName
     * @param expTempMils
     */
    public void putToDeadList(String origName, long expTempMils){

        HashMap<String, Long> deadList = deserializeDeadList();
        deadList.put(origName, expTempMils);
        logger.log(Level.INFO, "DeadList size: " + deadList.size());
        serializeDeadList(deadList);
    }

    /**
     * serialize DeadList
     * @param deadList
     */
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

    /**
     *deserialize DeadList
     * @return deadList in format HashMap
     */
    private HashMap<String,Long> deserializeDeadList() {
        Configuration configuration = new Configuration();
        HashMap<String, Long> deadList = new HashMap<String, Long>();
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

    /**
     * Update dead List
     * @param tempDeadList
     */
    public void deadListUpdate(HashMap<String, Long> tempDeadList){
        HashMap<String, Long> deadList = getDeadList();

        for (String origName : tempDeadList.keySet()) {
            if (deadList.containsKey(origName)){
                deadList.remove(origName);
            }
        }

        serializeDeadList(deadList);
    }
}
