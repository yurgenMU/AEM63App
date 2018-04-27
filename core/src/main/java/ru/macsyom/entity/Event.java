package ru.macsyom.entity;

import java.util.Objects;

public class Event {

    private String latitude;

    private String longitude;

    private String description;

    private String name;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                Objects.equals(description, marker.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, description);
    }


    @Override
    public String toString() {
        return "Event{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
