package me.xiaoying.livegetauthorize.server.utils;

import java.io.*;

public class SerializableUtil {
    public static void serializable(File file, Object object) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        if (!file.exists())
            file.createNewFile();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public static Object deserializable(File file) throws IOException, ClassNotFoundException {
        if (!file.exists())
            return null;
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        Object object = inputStream.readObject();
        inputStream.close();
        return object;
    }
}