package ru.macsyom.servlets;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import ru.macsyom.services.JSONSerializer;
import ru.macsyom.services.MarkerDAO;

import javax.servlet.ServletException;
import java.io.IOException;

@SlingServlet(paths = "/bin/markerServlet", methods = {"POST","DELETE"})
public class MarkerServlet extends SlingAllMethodsServlet {

    @Reference
    private MarkerDAO markerService;

    @Reference
    private JSONSerializer serializer;

    public void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String latitude = req.getParameter("lat");
        String longitude = req.getParameter("lng");
        String description = req.getParameter("descr");
        String parentPath = req.getParameter("parent");
        markerService.addMarker(latitude,longitude,description,parentPath);
    }



    public void doDelete(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String latitude = req.getParameter("lat");
        String longitude = req.getParameter("lng");
        String parentPath = req.getParameter("parent");
        markerService.removeMarker(latitude,longitude,parentPath);
        resp.getWriter().write(serializer.seriaizeCollection(markerService.getAll(parentPath)));
    }
}
