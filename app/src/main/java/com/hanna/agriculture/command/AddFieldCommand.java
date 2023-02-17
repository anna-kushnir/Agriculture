package com.hanna.agriculture.command;

import android.content.Context;

import com.hanna.agriculture.model.Field;
import com.hanna.agriculture.model.FieldList;

/**
 * Command to add a field
 */
public class AddFieldCommand extends Command{

    private FieldList field_list;
    private Field field;
    private Context context;

    public AddFieldCommand(FieldList field_list, Field field, Context context) {
        this.field_list = field_list;
        this.field = field;
        this.context = context;
    }

    // Save the field locally
    public void execute(){
        field_list.addField(field);
        super.setIsExecuted(field_list.saveFields(context));
    }
}
