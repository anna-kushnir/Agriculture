package com.hanna.agriculture.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hanna.agriculture.MainActivity;
import com.hanna.agriculture.R;
import com.hanna.agriculture.adapter.FieldActivityAdapter;
import com.hanna.agriculture.controller.FieldListController;
import com.hanna.agriculture.model.Field;
import com.hanna.agriculture.model.FieldList;
import com.hanna.agriculture.model.Observer;

public class AllFieldsActivity extends AppCompatActivity implements Observer {

    private FieldList field_list = new FieldList();
    private FieldListController field_list_controller = new FieldListController(field_list);

    private ListView fields_lv;
    private ArrayAdapter<Field> adapter;
    private Context context;

    private TextView main_menu, fields_menu;
    private ImageButton addField_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_fields_activity);

        Intent intent = getIntent(); // Get intent from MainActivity
        context = getApplicationContext();

        field_list_controller.addObserver(this);
        field_list_controller.loadFields(context);
        field_list_controller.setFields(field_list_controller.getFields());


        main_menu = findViewById(R.id.main_menu);
        fields_menu = findViewById(R.id.fields_menu);
        addField_button = findViewById(R.id.addField_button);
        fields_lv = findViewById(R.id.fields_lv);

        main_menu.setOnClickListener(
                this::mainActivity
        );

        fields_menu.setOnClickListener(
                this::allFieldsActivity
        );

        addField_button.setOnClickListener(
                this::addFieldActivity
        );

        fields_lv.setOnItemClickListener((parent, v, position, id) -> editFieldActivity(v, position));
    }

    public void addFieldActivity(View view) {
        Intent intent = new Intent(this, AddFieldActivity.class);
        startActivity(intent);
    }

    public void mainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void allFieldsActivity(View view) {
        Intent intent = new Intent(this, AllFieldsActivity.class);
        startActivity(intent);
    }

    public void editFieldActivity(View view, int position) {
        Intent intent = new Intent(this, EditFieldActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void update(){
        fields_lv = findViewById(R.id.fields_lv);
        adapter = new FieldActivityAdapter(this, field_list_controller.getFields());
        fields_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
