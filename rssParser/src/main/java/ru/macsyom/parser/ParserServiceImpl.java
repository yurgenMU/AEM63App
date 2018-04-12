package ru.macsyom.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Component;
import ru.macsyom.service.ParserService;

import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Component(immediate = true)
@Service
public class ParserServiceImpl implements ParserService {


    @Override
    public String getMessages(String address) {
        String arrayToJSon = "";
        URL url = null;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try (Reader reader = new XmlReader(url)){
            SyndFeed feed = new SyndFeedInput().build(reader);
            List<FeedMessage> messages = new ArrayList<>();
            feed.getEntries().stream().forEach(x-> messages.add(new FeedMessage((SyndEntry) x)));
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String arrayToJson = objectMapper.writeValueAsString(messages);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return arrayToJSon;
    }
}
