package ru.macsyom.servlets;


import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import ru.macsyom.models.RSSModel;
import ru.macsyom.services.ParserService;

import javax.servlet.ServletException;
import java.io.IOException;

@SlingServlet(paths = "/bin/getRSSData", methods = "GET")
public class RSSServlet extends SlingSafeMethodsServlet {

    @Reference
    private ParserService parserService;



    private String address;

    public void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        String address = req.getParameter("address");
        ResourceResolver resourceResolver = req.getResourceResolver();
        Resource resource = resourceResolver.getResource(address);
        RSSModel model = resource.adaptTo(RSSModel.class);
        resp.getWriter().write(parserService.getMessages(model.getAddress()));

    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
