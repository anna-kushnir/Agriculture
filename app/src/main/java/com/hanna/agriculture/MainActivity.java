package com.hanna.agriculture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanna.agriculture.activity.AllFieldsActivity;
import com.hanna.agriculture.adapter.CategoryAdapter;
import com.hanna.agriculture.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecycler;
    CategoryAdapter categoryAdapter;

    TextView main_menu, fields_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_menu = (TextView) findViewById(R.id.main_menu);
        fields_menu = (TextView) findViewById(R.id.fields_menu);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Посів", ""));
        categoryList.add(new Category(2, "Збір\nурожаю", ""));
        categoryList.add(new Category(3, "Внесення\nдобрив", ""));
        categoryList.add(new Category(4, "Дискування", ""));
        categoryList.add(new Category(5, "Закриття\nвологи", ""));
        categoryList.add(new Category(6, "Глибоке\nрихлення", ""));

        setCategoryRecycler(categoryList);

        main_menu.setOnClickListener(
                this::mainActivity
        );

        fields_menu.setOnClickListener(
                this::allFieldsActivity
        );
    }

    private void setCategoryRecycler(List<Category> categoryList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecycler.setAdapter(categoryAdapter);

    }

    public void mainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void allFieldsActivity(View view) {
        Intent intent = new Intent(this, AllFieldsActivity.class);
        startActivity(intent);
    }
}