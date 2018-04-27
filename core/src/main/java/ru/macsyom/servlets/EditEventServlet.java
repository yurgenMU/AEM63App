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
 * Servlet processing requests for editing the event
 */
@SlingServlet(paths = "/bin/editEventServlet", methods = "POST")
public class EditEventServlet extends SlingAllMethodsServlet {

    @Reference
    private EventService eventService;

    @Reference
    private JSONSerializer serializer;

    public void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String latitude = req.getParameter("lat");
        String longitude = req.getParameter("lng");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String path = req.getParameter("path");
        Event event = eventService
                .editEvent(latitude, longitude, name, description, path);
        if (event == null) {
            resp.getWriter().write("Value of request parameter is null");
        } else {
            resp.getWriter()
                    .write(serializer.serializeEvent(event));

        }
    }
}