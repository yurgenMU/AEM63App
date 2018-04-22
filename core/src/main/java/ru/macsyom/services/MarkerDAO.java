package ru.macsyom.services;

import ru.macsyom.models.Marker;

import java.util.Set;

public interface MarkerDAO {
    Marker addMarker(String lat, String lon, String descr, String parentPath);
    Marker editMarker(String lat, String lon, String descr,String path);
    Marker getMarker(String lat, String lon,String parentPath);
    void removeMarker(String lat, String lon,String parentPath);
    Set<Marker> getAll(String parentPath);
}
