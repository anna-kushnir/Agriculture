package com.hanna.agriculture.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hanna.agriculture.R;
import com.hanna.agriculture.controller.FieldListController;
import com.hanna.agriculture.model.Field;
import com.hanna.agriculture.model.FieldList;

/**
 * Add a new Field
 */
public class AddFieldActivity extends AppCompatActivity {

    private EditText title;
    private EditText location;
    private EditText size;
    private RadioButton isSownYes;
    private RadioButton isSownNo;
    private EditText whatIsSown;

    private TextView whatIsSown_tv;

    private ImageView photo;
    private Bitmap image;
    private final int REQUEST_CODE = 1;

    private FieldList field_list= new FieldList();
    private FieldListController field_list_controller = new FieldListController(field_list);

    private Context context;

    private String title_str;
    private String location_str;
    private String size_str;
    private String whatIsSown_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_field);

        title = findViewById(R.id.title);
        location = findViewById(R.id.location);
        size = findViewById(R.id.size);
        isSownYes = findViewById(R.id.yesButton);
        isSownNo = findViewById(R.id.noButton);
        whatIsSown = findViewById(R.id.whatIsSown);
        photo = findViewById(R.id.image_view);
        whatIsSown_tv = findViewById(R.id.whatIsSown_tv);

        whatIsSown.setVisibility(View.INVISIBLE);
        whatIsSown_tv.setVisibility(View.INVISIBLE);

        photo.setImageResource(android.R.drawable.ic_menu_gallery);

        Intent intent = getIntent(); // Get intent from MainActivity
        context = getApplicationContext();
        field_list_controller.loadFields(context);
    }

    public void saveField (View view) {

        title_str = title.getText().toString();
        location_str = location.getText().toString();
        size_str = size.getText().toString();
        boolean isSown_bln = isSownYes.isChecked();
        whatIsSown_str = whatIsSown.getText().toString();

        if(!validateInput()){
            return;
        }

        Field field = new Field(title_str, location_str, size_str, isSown_bln, whatIsSown_str, image, null);
        // FieldController field_controller = new FieldController(field);

        boolean success = field_list_controller.addField(field, context);
        if (!success){
            return;
        }

        // End AddFieldActivity
        final Intent intent = new Intent(this, AllFieldsActivity.class);
        Toast.makeText(context, "Поле створено.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //noinspection deprecation
        startActivityForResult(intent, REQUEST_CODE);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            //noinspection deprecation
//            startActivityForResult(intent, REQUEST_CODE);
//        }
    }

    public void deletePhoto(View view) {
        if (image != null) {
            image = null;
            photo.setImageResource(android.R.drawable.ic_menu_gallery);
            Toast.makeText(context, "Фото видалено.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent intent) {
        super.onActivityResult(request_code, result_code, intent);
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
        // main_intent.putExtra("user_id", user_id);
        startActivity(main_intent);
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
