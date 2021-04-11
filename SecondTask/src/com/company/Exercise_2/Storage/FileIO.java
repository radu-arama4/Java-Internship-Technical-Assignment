package com.company.Exercise_2.Storage;

import com.company.Exercise_2.Data.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
    //Method for deserializing the data from files
    public static List<User> extractUsers(File file) throws IOException, ClassNotFoundException, ClassCastException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<User> users = (ArrayList<User>) ois.readObject();
        fis.close();
        ois.close();
        return users;
    }

    //Method for serializing the current data into files
    public static void storeUsers(List<User> users, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(users);
        oos.close();
        fos.close();
    }
}
