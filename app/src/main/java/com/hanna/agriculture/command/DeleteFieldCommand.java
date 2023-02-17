package com.hanna.agriculture.command;

import android.content.Context;

import com.hanna.agriculture.model.Field;
import com.hanna.agriculture.model.FieldList;

/**
 * Command to delete a field
 */
public class DeleteFieldCommand extends Command {

    private FieldList field_list;
    private Field field;
    private Context context;

    public DeleteFieldCommand(FieldList field_list, Field field, Context context) {
        this.field_list = field_list;
        this.field = field;
        this.context = context;
    }

    // Delete the item locally
    public void execute() {
        field_list.deleteField(field);
        super.setIsExecuted(field_list.saveFields(context));
    }
}
