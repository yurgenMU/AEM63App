package ru.macsyom.services;

import ru.macsyom.models.Marker;

import java.util.Set;

public interface MarkerDAO {
    void addMarker(String lat, String lon, String descr, String parentPath);
    void editMarker(String lat, String lon, String descr,String parentPath);
    Marker getMarker(String lat, String lon,String parentPath);
    void removeMarker(String lat, String lon,String parentPath);
    Set<Marker> getAll(String parentPath);
}
