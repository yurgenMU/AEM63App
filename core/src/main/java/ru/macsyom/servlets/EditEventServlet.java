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

@SlingServlet(paths = "/bin/editEventServlet", methods = "POST")
public class EditEventServlet extends SlingAllMethodsServlet{

    @Reference
    private EventDAO eventDAO;

    @Reference
    private JSONSerializer serializer;

    public void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String latitude = req.getParameter("lat");
        String longitude = req.getParameter("lng");
        String description = req.getParameter("descr");
        String text = req.getParameter("text");
        String path = req.getParameter("path");
        resp.getWriter()
                .write(serializer.serializeEvent(eventDAO
                        .dragEvent(latitude,longitude,description,text,path)));
    }
}
