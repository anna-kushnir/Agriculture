package com.hanna.agriculture.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hanna.agriculture.R;
import com.hanna.agriculture.controller.FieldController;
import com.hanna.agriculture.controller.FieldListController;
import com.hanna.agriculture.model.Field;
import com.hanna.agriculture.model.FieldList;
import com.hanna.agriculture.model.Observer;

/**
 * Editing a pre-existing field consists of deleting the old field and adding a new field with the old
 * field's id.
 */
public class EditFieldActivity extends AppCompatActivity implements Observer {

    private FieldList field_list = new FieldList();
    private FieldListController field_list_controller = new FieldListController(field_list);

    private Field field;
    private FieldController field_controller;

    private Context context;

    private Bitmap image;
    private int REQUEST_CODE = 1;
    private ImageView photo;

    private EditText title;
    private EditText location;
    private EditText size;
    private RadioButton isSownYes;
    private RadioButton isSownNo;
    private EditText whatIsSown;
    private TextView whatIsSown_tv;

    private ImageButton add_image_button;
    private ImageButton cancel_image_button;
    private Button delete_field_button;
    private Button save_field_button;

    private boolean on_create_update;
    private int pos;

    private String title_str;
    private String location_str;
    private String size_str;
    private boolean isSown_bln;
    private String whatIsSown_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_field);

        title = (EditText) findViewById(R.id.title);
        location = (EditText) findViewById(R.id.location);
        size = (EditText) findViewById(R.id.size);
        isSownYes = (RadioButton) findViewById(R.id.yesButton);
        isSownNo = (RadioButton) findViewById(R.id.noButton);
        whatIsSown = (EditText) findViewById(R.id.whatIsSown);
        photo = (ImageView) findViewById(R.id.image_view);
        whatIsSown_tv = (TextView) findViewById(R.id.whatIsSown_tv);

        add_image_button = (ImageButton) findViewById(R.id.add_image_button);
        cancel_image_button = (ImageButton) findViewById(R.id.cancel_image_button);
        delete_field_button = (Button) findViewById(R.id.delete_field_button);
        save_field_button = (Button) findViewById(R.id.save_field_button);

        if (isSownYes.isChecked()) {
            whatIsSown.setVisibility(View.VISIBLE);
            whatIsSown_tv.setVisibility(View.VISIBLE);
        }
        else {
            whatIsSown.setVisibility(View.INVISIBLE);
            whatIsSown_tv.setVisibility(View.INVISIBLE);
        }

        Intent intent = getIntent(); // Get intent from FieldsFragment
        pos = intent.getIntExtra("position", 0);

        context = getApplicationContext();

        on_create_update = false; // Suppress first call to update()
        field_list_controller.addObserver(this);
        field_list_controller.loadFields(context);

        on_create_update = true;

        on_create_update = false; // Suppress any further calls to update()
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            //noinspection deprecation
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    public void deletePhoto(View view) {
        if (image != null) {
            image = null;
            photo.setImageResource(android.R.drawable.ic_menu_gallery);
            Toast.makeText(context, "Фото видалено.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int request_code, int result_code, Intent intent) {
        if (request_code == REQUEST_CODE && result_code == RESULT_OK) {
            Bundle extras = intent.getExtras();
            image = (Bitmap) extras.get("data");
            photo.setImageBitmap(image);
            Toast.makeText(context, "Фото додано.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent main_intent = new Intent(this, AllFieldsActivity.class);
        startActivity(main_intent);
    }

    public void deleteField(View view) {
        boolean success = field_list_controller.deleteField(field, context);
        if (!success){
            return;
        }

        // End EditItemActivity
        field_list_controller.removeObserver(this);

        final Intent intent = new Intent(this, AllFieldsActivity.class);

        // Delay launch of new activity to allow server more time to process request
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Поле видалено.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }, 750);
    }

    public void saveField(View view) {
        title_str = title.getText().toString();
        location_str = location.getText().toString();
        size_str = size.getText().toString();
        isSown_bln = isSownYes.isChecked();
        whatIsSown_str = whatIsSown.getText().toString();

        if(!validateInput()){
            return;
        }

        String field_id = field_controller.getId(); // Reuse the field id

        Field updated_field = new Field(title_str, location_str, size_str, isSown_bln, whatIsSown_str, image, field_id);
        FieldController updated_field_controller = new FieldController(updated_field);

        boolean success = field_list_controller.editField(field, updated_field, context);
        if (!success){
            return;
        }

        field_list_controller.removeObserver(this);

        // End EditFieldActivity
        final Intent intent = new Intent(this, AllFieldsActivity.class);
        Toast.makeText(context, "Поле збережено.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    /**
     * Only need to update the view once from the onCreate method
     */
    @SuppressLint("SetTextI18n")
    public void update() {
        if (on_create_update){

            // For all status options we do the following
            field = field_list_controller.getField(pos);
            field_controller = new FieldController(field);

            title.setText(field_controller.getTitle());
            location.setText(field_controller.getLocation());
            size.setText(field_controller.getSize().toString());
            isSownYes.setChecked(field_controller.isSown());
            isSownNo.setChecked(!field_controller.isSown());
            whatIsSown.setText(field_controller.getWhatIsSown());

            image = field_controller.getImage();
            if (image != null) {
                photo.setImageBitmap(image);
            } else {
                photo.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }
    }

    public boolean validateInput(){
        if (title_str.equals("")) {
            title.setError("Порожнє значення!");
            return false;
        }

        if (location_str.equals("")) {
            location.setError("Порожнє значення!");
            return false;
        }

        if (size_str.equals("")) {
            size.setError("Порожнє значення!");
            return false;
        }

        if (Float.parseFloat(size_str) <= 0) {
            size.setError("Розмір поля має бути більшим за нуль!");
            return false;
        }

        if (!isSownYes.isChecked() && !isSownNo.isChecked()) {
            isSownYes.setError("Оберіть одне зі значень!");
            return false;
        }

        if (isSownYes.isChecked() && whatIsSown_str.equals("")) {
            whatIsSown.setError("Порожнє значення!");
            return false;
        }

        return true;
    }

    public void isSownClickYes(View view) {
        whatIsSown.setVisibility(View.VISIBLE);
        whatIsSown_tv.setVisibility(View.VISIBLE);
    }

    public void isSownClickNo(View view) {
        whatIsSown.setVisibility(View.INVISIBLE);
        whatIsSown_tv.setVisibility(View.INVISIBLE);

        whatIsSown.setText("");
    }
}
