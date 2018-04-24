package ru.macsyom.mapTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import ru.macsyom.entity.Event;
import ru.macsyom.services.JSONSerializer;

import java.util.Set;


@Component(immediate = true)
@Service
public class JSONSerializerImpl implements JSONSerializer {
    private ObjectMapper objectMapper;

    public JSONSerializerImpl(){
        objectMapper = new ObjectMapper();
    }


    @Override
    public String serializeEvent(Event marker) throws JsonProcessingException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(marker);
    }

    @Override
    public String seriaizeCollection(Set<Event> markers) throws JsonProcessingException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(markers);
    }
}
