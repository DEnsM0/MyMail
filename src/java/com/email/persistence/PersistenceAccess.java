package com.email.persistence;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess {
    private static final Path LOGIN_DATA_PATH = Path.of(System.getProperty("user.home") ,"loginData.ser");

    public List<LoginData> loadFromPersistence(){
        List<LoginData> result = new ArrayList<>();
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(LOGIN_DATA_PATH.toFile()))) {
            List<LoginData> persistedList = (List<LoginData>) objectInputStream.readObject();
            result.addAll(persistedList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void saveToPersistence(List<LoginData> loginData){
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(LOGIN_DATA_PATH.toFile()))) {
            objectOutputStream.writeObject(loginData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
