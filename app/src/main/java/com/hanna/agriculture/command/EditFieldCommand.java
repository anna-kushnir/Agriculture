package com.hanna.agriculture.command;

import android.content.Context;

import com.hanna.agriculture.model.Field;
import com.hanna.agriculture.model.FieldList;

/**
 * Command to edit pre-existing field
 */
public class EditFieldCommand extends Command {

    private FieldList field_list;
    private Field old_field;
    private Field new_field;
    private Context context;

    public EditFieldCommand(FieldList field_list, Field old_field, Field new_field, Context context) {
        this.field_list = field_list;
        this.old_field = old_field;
        this.new_field = new_field;
        this.context = context;
    }

    // Delete the old item locally, save the new item locally
    public void execute() {
        field_list.deleteField(old_field);
        field_list.addField(new_field);
        super.setIsExecuted(field_list.saveFields(context));
    }
}
