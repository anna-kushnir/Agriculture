package com.hanna.agriculture.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * FieldList class
 */
public class FieldList extends Observable {

    private static ArrayList<Field> fields;
    private final String FILENAME = "fields.sav";

    public FieldList() {
        fields = new ArrayList<Field>();
    }

    public void setFields(ArrayList<Field> field_list) {
        fields = field_list;
        notifyObservers();
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void addField(Field field) {
        fields.add(field);
        notifyObservers();
    }

    public void deleteField(Field field) {
        fields.remove(field);
        notifyObservers();
    }

    public Field getField(int index) {
        return fields.get(index);
    }

    public int getIndex(Field field) {
        int pos = 0;
        for (Field f : fields) {
            if (field.getId().equals(f.getId())) {
                return pos;
            }
            pos = pos + 1;
        }
        return -1;
    }

    public int getSize() {
        return fields.size();
    }

    public Field getFieldById(String id){
        for (Field f: fields) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public void loadFields(Context context) {

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Field>>() {
            }.getType();
            fields = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            fields = new ArrayList<Field>();
        } catch (IOException e) {
            fields = new ArrayList<Field>();
        }
        notifyObservers();
    }

    public boolean saveFields(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(fields, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
