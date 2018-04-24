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

@SlingServlet(paths = "/bin/allEventsServlet", methods = "GET")
public class AllEventsServlet extends SlingAllMethodsServlet {

    @Reference
    private EventDAO eventDAO;

    @Reference
    private JSONSerializer serializer;

    public void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String parentPath = req.getParameter("parent");
        resp.getWriter().write(serializer.seriaizeCollection(eventDAO.getAll(parentPath)));

    }
}
