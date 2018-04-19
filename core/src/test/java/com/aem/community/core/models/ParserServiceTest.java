//package com.aem.community.core.models;
//
//import com.rometools.rome.feed.synd.SyndFeed;
//import com.rometools.rome.io.FeedException;
//import com.rometools.rome.io.ParsingFeedException;
//import com.rometools.rome.io.SyndFeedInput;
//import com.rometools.rome.io.XmlReader;
//import org.junit.Assert;
//import org.junit.Test;
//import ru.macsyom.parser.FeedMessage;
//import ru.macsyom.parser.ParserServiceImpl;
//
//import java.io.*;
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//
//
//public class ParserServiceTest {
//    private ParserServiceImpl parserService = new ParserServiceImpl();
//
//
//    @Test
//    public void serializingValidation() throws IOException, FeedException {
//        SyndFeed feed = getFeed("nasaRSS.xml");
//        String feedContent = parserService.jsonSerialize(feed).
//                replaceAll("\n", "").
//                replaceAll("\r", "");
//        String response = getResponse("response.json").
//                replaceAll("\n", "").
//                replaceAll("\r", "");
//        Assert.assertEquals(feedContent, response);
//
//    }
//
//
//    @Test
//    public void invalidFeedTest() throws IOException, FeedException {
//        SyndFeed feed = getFeed("invalidRSS.xml");
//        List<FeedMessage> feedMessages = parserService.getFeedMessages(feed);
//        System.out.println(feedMessages.get(1));
//
//    }
//
//
//    @Test
//    public void feedMessageTest() throws IOException, FeedException {
//        SyndFeed feed = getFeed("nasaRSS.xml");
//        List<FeedMessage> feedMessages = parserService.getFeedMessages(feed);
//        FeedMessage message = new FeedMessage();
//        message.setTitle("Frostburg State Students to Speak with NASA Astronaut in Space");
//        message.setLink("http://www.nasa.gov/press-release/frostburg-state-students-to-speak-with-nasa-astronaut-in-space");
//        message.setPubDate("Thu Apr 05 23:04:00 MSK 2018");
//        message.setAuthor("");
//        Assert.assertEquals(feedMessages.get(1), message);
//
//    }
//
//
//    private SyndFeed getFeed(String path) throws IOException, FeedException {
//        XmlReader reader = new XmlReader(getClass().getResourceAsStream(path));
//        SyndFeed feed = new SyndFeedInput().build(reader);
//        return feed;
//    }
//
//
//    private String getResponse(String path) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        try (BufferedReader bufferedReader = new BufferedReader(
//                new InputStreamReader(
//                        getClass().getResourceAsStream(path)
//                ))) {
//            String sCurrentLine;
//            while ((sCurrentLine = bufferedReader.readLine()) != null) {
//                stringBuilder.append(sCurrentLine).append("\n");
//            }
//        }
//        int last = stringBuilder.lastIndexOf("\n");
//        if (last >= 0) {
//            stringBuilder.delete(last, stringBuilder.length());
//        }
//        return stringBuilder.toString();
//    }
//}
