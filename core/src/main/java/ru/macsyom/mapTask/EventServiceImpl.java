package ru.macsyom.mapTask;


import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import ru.macsyom.dao.EventDAO;
import ru.macsyom.entity.Event;
import ru.macsyom.services.EventService;

import java.util.Set;

@Component(immediate = true)
@Service
public class EventServiceImpl implements EventService{

    @Reference
    private EventDAO eventDAO;


    /**
     * Adds event to JCR using parameters given from the servlet
     * @param lat - latitude of the marker
     * @param lon - longitude of the marker
     * @param descr - name of the event
     * @param text - text describing the event
     * @param parentPath - path of the page in JCR
     * @return added Event instance
     */
    @Override
    public Event addEvent(String lat, String lon, String descr, String text, String parentPath) {
        Event event = eventDAO.addEvent(lat,lon,descr,text, parentPath);
        return event;
    }


    /**
     *
     * @param lat - new latitude of the marker (if changed)
     * @param lon - new longitude of the marker (if changed)
     * @param descr - new name of the event (if changed)
     * @param text - new text describing the event (if changed)
     * @param path - path of the page in JCR (if changed)
     * @return edited Event instance
     */
    @Override
    public Event editEvent(String lat, String lon, String descr, String text, String path) {
        Event event = new Event();
        event.setLatitude(lat);
        event.setLongitude(lon);
        event.setName(descr);
        event.setDescription(text);
        event.setPath(path);
        return eventDAO.editEvent(event);
    }


    /**
     *Method removing event using his path in JCR as identifier
     * @param path - event's path in JCR given from the servlet
     */
    @Override
    public String removeEvent(String path) {
        eventDAO.removeEvent(path);
        return path;
    }

    /**
     *Method getting al the events at the chosen page from the JCR
     * @param parentPath - path of the page which contains events (given from the servlet)
     * @return all the events at the page
     */
    @Override
    public Set<Event> getAll(String parentPath) {
        return eventDAO.getAll(parentPath);
    }
}
