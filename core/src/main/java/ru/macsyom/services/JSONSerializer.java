package ru.macsyom.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.macsyom.entity.Event;

import java.util.Set;


/**
 * Interface for serialize Event object and Collection of Event objects
 */
public interface JSONSerializer {

    /**
     * Serializes Event's instance
     * @param event - given Event instance
     * @return - JSON value of the event
     * @throws JsonProcessingException
     */
    String serializeEvent(Event event) throws JsonProcessingException;

    /**
     *
     * @param events
     * @return
     * @throws JsonProcessingException
     */
    String serializeCollection(Set<Event> events) throws JsonProcessingException;

}
