package com.example.hemera;

import java.io.Serializable;

public class Planet implements Serializable {
    private String name;
    private float distance;
    private final String orderOfMagnitude;
    private String objectType;

    private int backgroundDrawable;
    private String description;
    private String imageUri;


    public Planet(String name, float distance, String orderOfMagnitude, String objectType, String description, String imageUri) {
        this.name = name;
        this.distance = distance;
        this.orderOfMagnitude = orderOfMagnitude;
        this.objectType = objectType;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public float getDistance() {
        return distance;
    }

    public String getOrderOfMagnitude() {
        return orderOfMagnitude;
    }

    public String getObjectType() {
        return objectType;
    }
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public int getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(int backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUri() {
        return imageUri;
    }

}
