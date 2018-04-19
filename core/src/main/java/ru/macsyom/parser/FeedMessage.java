package ru.macsyom.parser;


import java.util.Comparator;
import java.util.Objects;

public class FeedMessage {

    private String title;
    private String description;
    private String link;
    private String author;
    private String guid;
    private String pubDate;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedMessage that = (FeedMessage) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(link, that.link) &&
                Objects.equals(author, that.author) &&
                Objects.equals(guid, that.guid) &&
                Objects.equals(pubDate, that.pubDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, link, author, guid, pubDate);
    }

    @Override
    public String toString() {
        return "FeedMessage{" +
                "title='" + title + '\'' +

                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", guid='" + guid + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }


}