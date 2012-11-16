package com.agileapes.powerpack.reflection.beans.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public class SerializableBeanWrapper extends MappedBeanWrapper implements Serializable {

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(map);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        //noinspection unchecked
        map = (Map<String, Object>) in.readObject();
    }

}
