package com.hanna.agriculture.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hanna.agriculture.R;
import com.hanna.agriculture.model.Field;

import java.util.ArrayList;

/**
 * Displays a list of all fields
 */
public class AllFieldsFragment  extends FieldsFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        super.setVariables(R.layout.all_fields_activity, R.id.fields_lv);
        super.loadFields(AllFieldsFragment.this);
        super.setFragmentOnFieldLongClickListener();

        return rootView;
    }

    public ArrayList<Field> filterFields() {
        return field_list_controller.getFields();
    }

}
