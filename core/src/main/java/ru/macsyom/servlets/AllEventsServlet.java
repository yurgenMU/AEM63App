package ru.macsyom.servlets;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import ru.macsyom.services.JSONSerializer;
import ru.macsyom.dao.EventDAO;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Servlet processing requests for getting all the events
 */
@SlingServlet(paths = "/bin/allEventsServlet", methods = "GET")
public class AllEventsServlet extends SlingAllMethodsServlet {

    @Reference
    private EventDAO eventService;

    @Reference
    private JSONSerializer serializer;

    public void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String parentPath = req.getParameter("parent");
        resp.getWriter().write(serializer.serializeCollection(eventService.getAll(parentPath)));

    }
}
