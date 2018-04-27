package ru.macsyom.dao;

import ru.macsyom.entity.Event;

import java.util.Set;

/**
 * Interface provides methods do get access to JCR and manipulate objects
 */
public interface EventDAO {

    /**
     * Method adding event data into the database using object's properties
     * @param lat - latitude of the marker
     * @param lon - longitude of the marker
     * @param name - name of the event
     * @param description - description describing the event
     * @param parentPath - path of the page in JCR
     * @return POJO describing given object
     */
    Event addEvent(String lat, String lon, String name, String description, String parentPath);


    /**
     * Method getting event by chosen coordinates
     * @param lat latitude of the event
     * @param lon longitude of the event
     * @param parentPath parent path in JCR
     * @return
     */
    Event getEvent(String lat, String lon, String parentPath);

    /**
     *Method editing event in case of drag-and-dropping or change its properties
     * @param event - POJO describing the event object
     * @return Event instance with same path and new properties
     */
    Event editEvent(Event event);

    /**
     *Method removing event using his path in JCR as identifier
     * @param path - event's path in JCR
     */
    void removeEvent(String path);

    /**
     *Method getting al the events at the chosen page from the JCR
     * @param parentPath - path of the page which contains events
     * @return all the events at the page
     */
    Set<Event> getAll(String parentPath);
}
