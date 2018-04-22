package ru.macsyom.mapTask;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import ru.macsyom.models.Marker;
import ru.macsyom.services.MarkerDAO;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import java.util.*;

@Component(immediate = true)
@Service
public class MarkerService implements MarkerDAO {
    @Reference
    private SlingRepository repository;

    @Override
    public Marker addMarker(String lat, String len, String descr, String parentPath) {
        Marker marker = null;
        try {
            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            Node parent = session.getNode(parentPath);
            String markerPath = "marker_" + generatePostfix();
            Node node = parent.addNode(markerPath);
            node.setPrimaryType("nt:unstructured");
            node.setProperty("latitude", lat);
            node.setProperty("longitude", len);
            node.setProperty("description", descr);
            marker = adaptNode(node);
            marker.setPath(parentPath + "/" + markerPath);
            session.save();
            session.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marker;
    }

    @Override
    public Marker editMarker(String lat, String lon, String descr, String path) {
        Marker marker = null;
        try {
            Session session = repository
                    .login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            Node node = session.getNode(path);
            node.setProperty("latitude", lat);
            node.setProperty("longitude", lon);
            node.setProperty("description", descr);
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
    public Marker getMarker(String lat, String len, String parentPath) {
        Set<Marker> markers = getAll(parentPath);
        Optional<Marker> matchingObject = markers.stream()
                .filter(x -> (x.getLatitude().equals(lat) && x.getLongitude().equals(len)))
                .findFirst();
        Marker marker = matchingObject.orElse(null);
        return marker;
    }

    @Override
    public void removeMarker(String lat, String len, String parentPath) {
        try {
            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            Node parent = session.getNode(parentPath);
            NodeIterator nodeIterator = parent.getNodes();
            while (nodeIterator.hasNext()) {
                Node currentNode = nodeIterator.nextNode();
                String currentLat = currentNode.getProperty("latitude").getString();
                String currentLen = currentNode.getProperty("longitude").getString();
                System.out.println("lat = " + currentLat);
                System.out.println("len=" + currentLen);
                if ((currentLat.equals(lat)) && (currentLen.equals(len))) {
                    currentNode.remove();
                }
            }
            session.save();
            session.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Marker> getAll(String parentPath) {
        Set<Marker> markers = new HashSet<>();
        try {
            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
            QueryManager queryManager = session.getWorkspace().getQueryManager();
            Query query = queryManager.createQuery("SELECT p.* FROM [nt:unstructured] AS p\n" +
                    "WHERE ISDESCENDANTNODE(p, [" + parentPath + "])", Query.JCR_SQL2);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext()) {
                Node nextNode = nodeIterator.nextNode();
                String lat = nextNode.getProperty("latitude").getString();
                String len = nextNode.getProperty("longitude").getString();

//                    String descr = x.getProperty("description").getString();
                Marker marker = adaptNode(nextNode);
                marker.setPath(nextNode.getPath());
//                    marker.setText(descr);
                markers.add(marker);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return markers;
    }

//    @Override
//    public Set<Marker> getAll(String parentPath) {
//        Set<Marker> markers = new HashSet<>();
//        try {
//            Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "crx.default");
//            Node parent = session.getNode(parentPath);
//            NodeIterator nodeIterator = parent.getNodes();
//            Set<Node> children = new HashSet<>();
//            while (nodeIterator.hasNext()) {
//                children.add(nodeIterator.nextNode());
//            }
//            children.stream().forEach(x -> {
//                try {
//                    String lat = x.getProperty("latitude").getString();
//                    String len = x.getProperty("longitude").getString();
////                    String descr = x.getProperty("description").getString();
//                    Marker marker = new Marker();
//                    marker.setLatitude(lat);
//                    marker.setLongitude(len);
////                    marker.setText(descr);
//                    markers.add(marker);
//                } catch (RepositoryException e) {
//                    e.printStackTrace();
//                }
//            });
//            session.save();
//            session.logout();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return markers;
//    }

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

    //    SELECT * FROM [nt:unstructured] AS node
//    WHERE ISDESCENDANTNODE(node, "/content/mappage/jcr:content")
//    AND CONTAINS([latitude], "55.79123889811954") AND CONTAINS([longitude], "36.09705224999993")
    private Marker adaptNode(Node node) throws RepositoryException {
        Marker marker = new Marker();
        marker.setLatitude(node.getProperty("latitude").getString());
        marker.setLongitude(node.getProperty("longitude").getString());
//        node.setProperty("description", descr)
        return marker;
    }

}
