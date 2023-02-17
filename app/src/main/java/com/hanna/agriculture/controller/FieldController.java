package com.hanna.agriculture.controller;

import android.graphics.Bitmap;

import com.hanna.agriculture.model.Category;
import com.hanna.agriculture.model.Field;
import com.hanna.agriculture.model.Observer;

import java.util.List;

/**
 * FieldController is responsible for all communication between views and Field model
 */
public class FieldController {

    private Field field;

    public FieldController(Field field){
        this.field = field;
    }

    public String getId(){
        return field.getId();
    }

    public void setId() {
        field.setId();
    }

    public String getTitle() {
        return field.getTitle();
    }

    public void setTitle(String title) {
        field.setTitle(title);
    }

    public Float getSize() {
        return field.getSize();
    }

    public void setSize(Float size) {
        field.setSize(size);
    }

    public String getLocation() {
        return field.getLocation();
    }

    public void setLocation(String location) {
        field.setLocation((location));
    }

    public boolean isSown() {
        return field.isSown();
    }

    public void setSown(boolean sown) {
        field.setSown((sown));
    }

    public String getWhatIsSown() {
        return field.getWhatIsSown();
    }

    public void setWhatIsSown(String whatIsSown) {
        field.setWhatIsSown(whatIsSown);
    }

    public List<Category> getCategories() {
        return field.getCategories();
    }

    public void setCategories(List<Category> categories) {
        field.setCategories(categories);
    }

    public void addImage(Bitmap image){
        field.addImage(image);
    }

    public Bitmap getImage(){
        return field.getImage();
    }

    public Field getField() { return this.field; }

    public void addObserver(Observer observer) {
        field.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        field.removeObserver(observer);
    }
}
