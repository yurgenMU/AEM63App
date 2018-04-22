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

@SlingServlet(paths = "/bin/editMarkerServlet", methods = "POST")
public class EditMarketServlet extends SlingAllMethodsServlet{

    @Reference
    private MarkerDAO markerService;

    @Reference
    private JSONSerializer serializer;

    public void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String latitude = req.getParameter("lat");
        String longitude = req.getParameter("lng");
        String description = req.getParameter("descr");
        String path = req.getParameter("path");
        resp.getWriter()
                .write(serializer.serializeMarker(markerService
                        .editMarker(latitude,longitude,description,path)));
    }
}
