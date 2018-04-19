package ru.macsyom.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.macsyom.models.Marker;

import java.util.Set;

public interface JSONSerializer {
    String serializeMarker(Marker marker) throws JsonProcessingException;
    String seriaizeCollection(Set<Marker> markers) throws JsonProcessingException;

}
