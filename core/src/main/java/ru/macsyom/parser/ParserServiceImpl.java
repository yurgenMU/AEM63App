package ru.macsyom.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import ru.macsyom.services.ParserService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Component(immediate = true)
@Service
public class ParserServiceImpl implements ParserService {


    @Override
    public String getMessages(String address) {
        URL url = null;
        XmlReader reader;
        String arrayToJson = "";
        try {
            url = new URL(address);
            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);
            arrayToJson = jsonSerialize(feed);
        } catch (Exception e) {
            e.printStackTrace();
            arrayToJson = e.getMessage();
        }
        return arrayToJson;
    }


    public List<FeedMessage> getFeedMessages(SyndFeed feed) {
        List<FeedMessage> feedMessages = new ArrayList<>();
        feed.getEntries().stream()
                .forEach(x -> {
                    FeedMessage feedMessage = new FeedMessage();
                    feedMessage.setAuthor(x.getAuthor());
                    feedMessage.setLink(x.getLink());
                    feedMessage.setDescription(x.getDescription().getValue());
                    feedMessage.setTitle(x.getTitle());
                    feedMessage.setPubDate(x.getPublishedDate().toString());
                    feedMessages.add(feedMessage);
                });
        return feedMessages;
    }

    public String jsonSerialize(SyndFeed feed) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(getFeedMessages(feed));
    }


}


