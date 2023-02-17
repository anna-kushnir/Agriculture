package com.hanna.agriculture.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hanna.agriculture.activity.EditFieldActivity;
import com.hanna.agriculture.adapter.FieldFragmentAdapter;
import com.hanna.agriculture.controller.FieldListController;
import com.hanna.agriculture.model.Field;
import com.hanna.agriculture.model.FieldList;
import com.hanna.agriculture.model.Observer;

import java.util.ArrayList;

/**
 * Superclass of AllFieldsFragment
 */
public abstract class FieldsFragment extends Fragment implements Observer {

    private FieldList field_list = new FieldList();

    FieldListController field_list_controller = new FieldListController(field_list);
    View rootView;

    private ListView list_view;
    private ArrayAdapter<Field> adapter;
    private ArrayList<Field> selected_fields;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;
    private Fragment fragment;
    private boolean update = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getContext();

        field_list_controller.loadFields(context); // Call to update() suppressed
        update = true; // Future calls to update() permitted

        this.inflater = inflater;
        this.container = container;

        return rootView;
    }

    public void setVariables(int resource, int id ) {
        rootView = inflater.inflate(resource, container, false);
        list_view = rootView.findViewById(id);
        selected_fields = filterFields();
    }

    public void loadFields(Fragment fragment){
        this.fragment = fragment;
        field_list_controller.addObserver(this);
        field_list_controller.loadFields(context);
    }

    public void setFragmentOnFieldLongClickListener(){
        // When field is long clicked, this starts EditFieldActivity
        list_view.setOnItemClickListener((parent, view, pos, id) -> {
            Field field = adapter.getItem(pos);

            int meta_pos = field_list_controller.getIndex(field);
            if (meta_pos >= 0) {

                Intent edit = new Intent(context, EditFieldActivity.class);
                edit.putExtra("position", meta_pos);
                startActivity(edit);
            }
        });
    }

    public abstract ArrayList<Field> filterFields();

    /**
     * Called when the activity is destroyed, thus we remove this fragment as an observer
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        field_list_controller.removeObserver(this);
    }

    /**
     * Update the view
     */
    public void update(){
        if (update) {
            selected_fields = filterFields(); // Ensure fields are filtered
            adapter = new FieldFragmentAdapter(context, selected_fields, fragment);
            list_view.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
