package ru.macsyom.services;

import ru.macsyom.entity.Event;

import java.util.Set;

public interface EventDAO {
    Event addEvent(String lat, String lon, String descr, String text, String path);
    Event dragEvent(String lat, String lon, String descr, String text, String path);
    Event getEvent(String lat, String lon, String parentPath);
    String removeEvent(String lat, String lon,String parentPath);
    Set<Event> getAll(String parentPath);
}
