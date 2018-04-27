package ru.macsyom.services;

import ru.macsyom.entity.Event;

import java.util.Set;

public interface EventService {

    /**
     * Adds event to JCR using parameters given from the servlet
     * @param lat - latitude of the marker
     * @param lon - longitude of the marker
     * @param descr - name of the event
     * @param text - text describing the event
     * @param path - path of the page in JCR
     * @return added Event instance
     */
    Event addEvent(String lat, String lon, String descr, String text, String path);

    /**
     *
     * @param lat - new latitude of the marker (if changed)
     * @param lon - new longitude of the marker (if changed)
     * @param descr - new name of the event (if changed)
     * @param text - new text describing the event (if changed)
     * @param path - path of the page in JCR (if changed)
     * @return edited Event instance
     */
    Event editEvent(String lat, String lon, String descr, String text, String path);

    /**
     *Method removing event using his path in JCR as identifier
     * @param path - event's path in JCR given from the servlet
     */
    String removeEvent(String path);

    /**
     *Method getting al the events at the chosen page from the JCR
     * @param parentPath - path of the page which contains events (given from the servlet)
     * @return all the events at the page
     */
    Set<Event> getAll(String parentPath);
}
