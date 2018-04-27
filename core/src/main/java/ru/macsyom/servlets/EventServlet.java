package ru.macsyom.servlets;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import ru.macsyom.entity.Event;
import ru.macsyom.services.EventService;
import ru.macsyom.services.JSONSerializer;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Servlet processing requests for creation and deletion events
 */
@SlingServlet(paths = "/bin/eventServlet", methods = {"POST", "DELETE"})
public class EventServlet extends SlingAllMethodsServlet {

    @Reference
    private EventService eventService;

    @Reference
    private JSONSerializer serializer;

    public void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String latitude = req.getParameter("lat");
        String longitude = req.getParameter("lng");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String parentPath = req.getParameter("parent");
        Event event = eventService
                .addEvent(latitude, longitude, name, description, parentPath);
        if(event == null){
            resp.getWriter().write("Value of request parameter is null");
        } else{
            resp.getWriter()
                    .write(serializer.serializeEvent(event));
        }
    }


    public void doDelete(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("path");
        resp.getWriter().write(eventService.removeEvent(path));
    }
}
