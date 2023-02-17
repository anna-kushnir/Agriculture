package com.hanna.agriculture.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Field class
 */
public class Field  extends Observable {

    private String title;
    private String location;
    private Float size;                      //hectare
    private boolean isSown;
    private String whatIsSown;
    private List<Category> categories;
    protected transient Bitmap image;
    protected String image_base64;
    private String id;

    public Field(String title, String location, String size, boolean isSown, String whatIsSown, Bitmap image, String fieldId) {
        this.title = title;
        this.location = location;
        this.size = Float.valueOf(size);
        this.isSown = isSown;
        this.whatIsSown = whatIsSown;
        this.categories = null;
        addImage(image);

        if (id == null){
            setId();
        } else {
            updateId(id);
        }
    }

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
        notifyObservers();
    }

    public void updateId(String id){
        this.id = id;
        notifyObservers();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
        notifyObservers();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        notifyObservers();
    }

    public boolean isSown() {
        return isSown;
    }

    public void setSown(boolean sown) {
        isSown = sown;
        notifyObservers();
    }

    public String getWhatIsSown() {
        return whatIsSown;
    }

    public void setWhatIsSown(String whatIsSown) {
        this.whatIsSown = whatIsSown;
        notifyObservers();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyObservers();
    }

    public void addImage(Bitmap new_image){
        if (new_image != null) {
            image = new_image;
            ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
            new_image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);

            byte[] b = byteArrayBitmapStream.toByteArray();
            image_base64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
        notifyObservers();
    }

    public Bitmap getImage(){
        if (image == null && image_base64 != null) {
            byte[] decodeString = Base64.decode(image_base64, Base64.DEFAULT);
            image = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return image;
    }
}
