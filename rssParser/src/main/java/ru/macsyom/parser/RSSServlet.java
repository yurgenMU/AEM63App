package ru.macsyom.parser;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import ru.macsyom.service.ParserService;

import javax.servlet.ServletException;
import java.io.*;

@SlingServlet(paths = "/bin/getRSSData", methods = "GET")
public class RSSServlet extends SlingSafeMethodsServlet {

    @Reference
    private ParserService parserService;

    private String address;

    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
            String address = req.getParameter("url");
            resp.getWriter().write(parserService.getMessages(address));

    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
