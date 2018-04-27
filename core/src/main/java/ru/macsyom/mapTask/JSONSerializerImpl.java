package ru.macsyom.mapTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import ru.macsyom.entity.Event;
import ru.macsyom.services.JSONSerializer;

import java.util.Set;

/**
 * Class implementing JSONSerializer Interface
 */
@Component(immediate = true)
@Service
public class JSONSerializerImpl implements JSONSerializer {
    private ObjectMapper objectMapper;

    public JSONSerializerImpl(){
        objectMapper = new ObjectMapper();
    }

    /**
     * Serializes Event's instance
     * @param event - given Event instance
     * @return - JSON value of the event
     * @throws JsonProcessingException
     */
    @Override
    public String serializeEvent(Event event) throws JsonProcessingException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(event);
    }

    /**
     *
     * @param events
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public String serializeCollection(Set<Event> events) throws JsonProcessingException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(events);
    }
}
