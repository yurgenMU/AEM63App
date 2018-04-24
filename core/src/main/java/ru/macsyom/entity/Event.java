package ru.macsyom.entity;

import java.util.Objects;

public class Event {

    private String latitude;

    private String longitude;

    private String text;

    private String description;

    private String path;


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event marker = (Event) o;
        return Objects.equals(latitude, marker.latitude) &&
                Objects.equals(longitude, marker.longitude) &&
                Objects.equals(text, marker.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(latitude, longitude, text);
    }



}
