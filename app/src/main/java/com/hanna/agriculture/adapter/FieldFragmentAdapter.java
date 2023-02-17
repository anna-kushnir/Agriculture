package com.hanna.agriculture.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.hanna.agriculture.R;
import com.hanna.agriculture.controller.FieldController;
import com.hanna.agriculture.model.Field;

import java.util.ArrayList;

/**
 * FieldFragmentAdapter is responsible for what information is displayed in ListView entries.
 */
public class FieldFragmentAdapter extends ArrayAdapter<Field> {

    private LayoutInflater inflater;
    private Fragment fragment;
    private Context context;

    public FieldFragmentAdapter(Context context, ArrayList<Field> fields, Fragment fragment) {
        super(context, 0, fields);
        this.inflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Field field = getItem(position);
        FieldController field_controller = new FieldController(field);

        String title = "Назва: " + field_controller.getTitle();
        String location = "Розташування: " + field_controller.getLocation();
        String size = "Розмір: " + field_controller.getSize();
        String isSown;
        if (field_controller.isSown()) {
            isSown = "Засіяне: Так";
        }
        else {
            isSown = "Засіяне: Ні";
        }
        Bitmap thumbnail = field_controller.getImage();
        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = inflater.from(context).inflate(R.layout.field_list_field, parent, false);
        }

        TextView title_tv = (TextView) convertView.findViewById(R.id.title_tv);
        TextView location_tv = (TextView) convertView.findViewById(R.id.location_tv);
        TextView size_tv = (TextView) convertView.findViewById(R.id.size_tv);
        TextView isSown_tv = (TextView) convertView.findViewById(R.id.isSown_tv);
        ImageView photo = (ImageView) convertView.findViewById(R.id.image_view);

        if (thumbnail != null) {
            photo.setImageBitmap(thumbnail);
        } else {
            photo.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        title_tv.setText(title);
        location_tv.setText(location);
        size_tv.setText(size);
        isSown_tv.setText(isSown);

        return convertView;
    }
}
