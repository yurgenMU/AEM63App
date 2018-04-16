package ru.macsyom.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import ru.macsyom.service.ParserService;


@Component(immediate = true)
@Service
public class ParserServiceImpl implements ParserService {


    @Override
    public String getMessages(String address) {
        RSSFeedParser feedParser = new RSSFeedParser(address);
        Feed feed = feedParser.readFeed();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String arrayToJson = "";
        try {
            arrayToJson = objectMapper.writeValueAsString(feed.getMessages());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return arrayToJson;
    }

}
