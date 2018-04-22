package ru.macsyom.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.jcr.Node;
import java.util.Objects;

@Model(adaptables = {Resource.class, Node.class})
public class Marker {

    @Inject
    private String latitude;

    @Inject
    private String longitude;

    @Inject
    private String text;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marker marker = (Marker) o;
        return Objects.equals(latitude, marker.latitude) &&
                Objects.equals(longitude, marker.longitude) &&
                Objects.equals(text, marker.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(latitude, longitude, text);
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
