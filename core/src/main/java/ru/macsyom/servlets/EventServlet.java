package ru.macsyom.servlets;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import ru.macsyom.services.JSONSerializer;
import ru.macsyom.services.EventDAO;

import javax.servlet.ServletException;
import java.io.IOException;

@SlingServlet(paths = "/bin/eventServlet", methods = {"POST", "DELETE"})
public class EventServlet extends SlingAllMethodsServlet {

    @Reference
    private EventDAO eventDAO;

    @Reference
    private JSONSerializer serializer;

    public void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String latitude = req.getParameter("lat");
        String longitude = req.getParameter("lng");
        String description = req.getParameter("descr");
        String text = req.getParameter("text");
        String parentPath = req.getParameter("parent");
        resp.getWriter()
                .write(serializer.serializeEvent(eventDAO
                        .addEvent(latitude, longitude, description, text, parentPath)));
    }


    public void doDelete(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String latitude = req.getParameter("lat");
        String longitude = req.getParameter("lng");
        String parentPath = req.getParameter("parent");
        eventDAO.removeEvent(latitude, longitude, parentPath);
        resp.getWriter().write(serializer.seriaizeCollection(eventDAO.getAll(parentPath)));
    }
}
