package ru.macsyom.dao.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.macsyom.entity.Event;
import ru.macsyom.dao.EventDAO;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import java.util.*;

/**
 * Custom implementation of EventDAO
 */
@Component(immediate = true)
@Service
public class EventDAOImpl implements EventDAO {

    @Reference
    private SlingRepository repository;

    private final Logger logger = LoggerFactory.getLogger(EventDAOImpl.class);


    /**
     * Method adding event data into the database using object's properties
     *
     * @param lat         - latitude of the marker
     * @param lon         - longitude of the marker
     * @param name        - name of the event
     * @param description - text describing the event
     * @param parentPath  - path of the page in JCR
     * @return POJO describing given object
     */
    @Override
    public Event addEvent(String lat, String lon, String name, String description, String parentPath) {
        Event event = null;
        try {
            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");

            String[] methodArgs = {lat, lon, name, description, parentPath};

//            if (!checkIfNullParams(methodArgs)) {
            if(getEvent(lat, lon, parentPath) == null){
                Node parent = session.getNode(parentPath);
                String markerPath = "marker_" + generatePostfix();
                Node node = parent.addNode(markerPath);
                node.setPrimaryType("nt:unstructured");
                if (!"".equals(name)) {
                    node.setProperty("name", name);
                }
                if (!"".equals(description)) {
                    node.setProperty("description", description);
                }
                node.setProperty("latitude", lat);
                node.setProperty("longitude", lon);
                logger.debug("Created node: ", node);
                event = adaptNode(node);
                event.setPath(parentPath + "/" + markerPath);
                session.save();
                session.logout();
            }

//            }
        } catch (RepositoryException e) {
            logger.error("Asdas", e);
        }
        return event;
    }


    /**
     * Method editing event in case of drag-and-dropping or change its properties
     *
     * @param event - POJO describing the event object
     * @return Event instance with same path and new properties
     */
    @Override
    public Event editEvent(Event event) {
        try {
            String name = event.getName();
            String description = event.getDescription();
            String path = event.getPath();
            String[] props = {name, description, path};
//            if (!checkIfNullParams(props)) {
                Session session = repository
                        .login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
                if(getEvent(event.getLatitude(), event.getLongitude(), path )== null){
                    Node node = session.getNode(path);
                    logger.debug("Successfully accessed the node {}", node.getName());
                    if (!"".equals(name)) {
                        //TODO Avoid NPE ???
                        node.setProperty("name", name);
                    }
                    if (!"".equals(description)) {
                        node.setProperty("description", description);
                    }
                    node.setProperty("latitude", event.getLatitude());
                    node.setProperty("longitude", event.getLongitude());
                    event = adaptNode(node);
                    event.setPath(path);
                    session.save();
                    session.logout();
//                }
            } else {
                return null;
            }
        } catch (RepositoryException e) {
            logger.error("Error", e);
        }
        return event;
    }


    @Override
    public Event getEvent(String lat, String lon, String parentPath) {
        Set<Event> markers = getAll(parentPath);
        Optional<Event> matchingObject = markers.stream()
                .filter(x -> (x.getLatitude().equals(lat) && x.getLongitude().equals(lon)))
                .findFirst();
        Event marker = matchingObject.orElse(null);
        System.out.println(marker);
        return marker;
    }

    /**
     * Method removing event using his path in JCR as identifier
     *
     * @param path - event's path in JCR
     */
    @Override
    public void removeEvent(String path) {
        try {
            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            Node node = session.getNode(path);
            node.remove();
            session.save();
            session.logout();
        } catch (RepositoryException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * Method getting al the events at the chosen page from the JCR
     *
     * @param parentPath - path of the page which contains events
     * @return all the events at the page
     */
    @Override
    public Set<Event> getAll(String parentPath) {
        Set<Event> events = new HashSet<>();
        try {
            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            QueryManager queryManager = session.getWorkspace().getQueryManager();
            Query query = queryManager.createQuery("SELECT p.* FROM [nt:unstructured] AS p\n" +
                    "WHERE ISDESCENDANTNODE(p, [" + parentPath + "])", Query.JCR_SQL2);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext()) {
                Node nextNode = nodeIterator.nextNode();
                Event event = adaptNode(nextNode);
                event.setPath(nextNode.getPath());
                events.add(event);
                System.out.println(event);
            }
        } catch (RepositoryException e) {
            logger.error(e.getMessage());
        }
        return events;
    }


    /**
     * Method generating unique path's postfix for the new node in JCR using letter Characters
     *
     * @return generated postfix
     */
    protected String generatePostfix() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }



    /**
     * Method performing the similar function as adaptTo() for SLing Model
     *
     * @param node - Node from JCR with given properties
     * @return Event instance as wrapper for Node
     * @throws RepositoryException
     */
    protected Event adaptNode(Node node) throws RepositoryException {
        Event event = new Event();
        event.setLatitude(node.getProperty("latitude").getString());
        event.setLongitude(node.getProperty("longitude").getString());
        event.setName(node.getProperty("name").getString());
        event.setDescription(node.getProperty("description").getString());
        return event;
    }


    /**
     * @param array Array of String which is being checked to null elements
     * @return result of checking
     */
    protected boolean checkIfNullParams(String[] array) {
        return Arrays.stream(array).anyMatch(Objects::isNull);
    }

}
