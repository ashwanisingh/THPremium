package com.netoperation.model;

import java.util.ArrayList;

public class PreferencesModel {
    private ArrayList<String> city;
    private ArrayList<String> author;
    private ArrayList<String> topics;
    private ArrayList<String> topic;
    private ArrayList<String> section;

    public ArrayList<String> getCity() {
        return city;
    }

    public void addSelectedCities(String city) {
        this.city.add(city);
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public void addSelectedAuthor(String author) {
        this.author.add(author);
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public void addSelectedTopics(String topics) {
        this.topics.add(topics);
    }

    public ArrayList<String> getTopic() {
        return topic;
    }

    public void addSelectedTopic(String topic) {
        this.topics.add(topic);
    }

    public ArrayList<String> getSection() {
        return section;
    }

    public void addSelectedSection(String section) {
        this.section.add(section);
    }
}
