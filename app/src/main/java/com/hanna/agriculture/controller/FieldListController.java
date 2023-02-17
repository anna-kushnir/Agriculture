package com.hanna.agriculture.controller;

import android.content.Context;

import com.hanna.agriculture.command.AddFieldCommand;
import com.hanna.agriculture.command.DeleteFieldCommand;
import com.hanna.agriculture.command.EditFieldCommand;
import com.hanna.agriculture.model.Field;
import com.hanna.agriculture.model.FieldList;
import com.hanna.agriculture.model.Observer;

import java.util.ArrayList;

/**
 * FieldListController is responsible for all communication between views and FieldList model
 */
public class FieldListController {

    private FieldList field_list;

    public FieldListController(FieldList field_list){
        this.field_list = field_list;
    }

    public void setFields(ArrayList<Field> field_list) {
        this.field_list.setFields(field_list);
    }

    public ArrayList<Field> getFields() {
        return field_list.getFields();
    }

    public boolean addField(Field field, Context context){
        AddFieldCommand add_field_command = new AddFieldCommand(field_list, field, context);
        add_field_command.execute();
        return add_field_command.isExecuted();
    }

    public boolean deleteField(Field field, Context context) {
        DeleteFieldCommand delete_field_command = new DeleteFieldCommand(field_list, field, context);
        delete_field_command.execute();
        return delete_field_command.isExecuted();
    }

    public boolean editField(Field field, Field updated_field, Context context){
        EditFieldCommand edit_field_command = new EditFieldCommand(field_list, field, updated_field, context);
        edit_field_command.execute();
        return edit_field_command.isExecuted();
    }

    public Field getField(int index) {
        return field_list.getField(index);
    }

    public int getIndex(Field field) {
        return field_list.getIndex(field);
    }

    public int getSize() {
        return field_list.getSize();
    }

    public Field getFieldById(String id){
        return field_list.getFieldById(id);
    }

    public void loadFields(Context context) {
        field_list.loadFields(context);
    }

    public void addObserver(Observer observer) {
        field_list.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        field_list.removeObserver(observer);
    }
}
