package ru.macsyom.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.macsyom.entity.Event;

import java.util.Set;

public interface JSONSerializer {
    String serializeEvent(Event event) throws JsonProcessingException;
    String seriaizeCollection(Set<Event> events) throws JsonProcessingException;

}
