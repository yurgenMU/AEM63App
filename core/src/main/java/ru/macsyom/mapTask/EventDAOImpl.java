package ru.macsyom.mapTask;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import ru.macsyom.entity.Event;
import ru.macsyom.services.EventDAO;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import java.util.*;

@Component(immediate = true)
@Service
public class EventDAOImpl implements EventDAO {
    @Reference
    private SlingRepository repository;

    @Override
    public Event addEvent(String lat, String lon, String descr, String text, String path) {
        Event marker = null;
        try {
            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            Node parent = session.getNode(path);
            String markerPath = "marker_" + generatePostfix();
            Node node = parent.addNode(markerPath);
            node.setPrimaryType("nt:unstructured");
            node.setProperty("latitude", lat);
            node.setProperty("longitude", lon);
            node.setProperty("description", descr);
            node.setProperty("text", text);
            marker = adaptNode(node);
            marker.setPath(path + "/" + markerPath);
            session.save();
            session.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marker;
    }

    @Override
    public Event editEvent(String lat, String lon, String descr, String text, String path) {
        Event marker = null;
        try {
            Session session = repository
                    .login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            Node node = session.getNode(path);
            if (!descr.equals("")) {
                node.setProperty("description", descr);
            }
            if (!text.equals("")) {
                node.setProperty("text", text);
            }
            node.setProperty("longitude", lon);
            node.setProperty("latitude", lat);
            marker = adaptNode(node);
            marker.setPath(path);
            session.save();
            session.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marker;
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

    @Override
    public String removeEvent(String lat, String lon, String parentPath) {
        String path = null;
        try {
            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            Node parent = session.getNode(parentPath);
            NodeIterator nodeIterator = parent.getNodes();
            while (nodeIterator.hasNext()) {
                Node currentNode = nodeIterator.nextNode();
                String currentLat = currentNode.getProperty("latitude").getString();
                String currentLen = currentNode.getProperty("longitude").getString();
                path = currentNode.getPath();
                if ((currentLat.equals(lat)) && (currentLen.equals(lon))) {
                    currentNode.remove();
                }
            }
            session.save();
            session.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(path);
        return path;
    }

    @Override
    public Set<Event> getAll(String parentPath) {
        Set<Event> markers = new HashSet<>();
        try {
            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            QueryManager queryManager = session.getWorkspace().getQueryManager();
            Query query = queryManager.createQuery("SELECT p.* FROM [nt:unstructured] AS p\n" +
                    "WHERE ISDESCENDANTNODE(p, [" + parentPath + "])", Query.JCR_SQL2);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext()) {
                Node nextNode = nodeIterator.nextNode();
                Event marker = adaptNode(nextNode);
                marker.setPath(nextNode.getPath());
                markers.add(marker);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return markers;
    }


    private String generatePostfix() {
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
        String generatedString = buffer.toString();
        return generatedString;
    }

    private Event adaptNode(Node node) throws RepositoryException {
        Event event = new Event();
        event.setLatitude(node.getProperty("latitude").getString());
        event.setLongitude(node.getProperty("longitude").getString());
        event.setDescription(node.getProperty("description").getString());
        event.setText(node.getProperty("text").getString());
        return event;
    }

}
