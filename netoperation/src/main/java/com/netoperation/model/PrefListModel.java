package com.netoperation.model;

import java.util.ArrayList;
import java.util.List;

public class PrefListModel {
    private List<String> topics;
    private List<String> cities;
    private List<String> authors;

    public List<String> getTopics() {
        return topics;
    }

    public List<String> getCities() {
        return cities;
    }

    public List<String> getAuthors() {
        return authors;
    }

    private ArrayList<PersonaliseModel> topicsModels = new ArrayList<>();
    private ArrayList<PersonaliseModel> citiesModels = new ArrayList<>();
    private ArrayList<PersonaliseModel> authorsModels = new ArrayList<>();



    public void addTopicsModels(PersonaliseModel topic) {
        this.topicsModels.add(topic);
    }

    public ArrayList<PersonaliseModel> getTopicsModels() {
        return topicsModels;
    }

    public void addCitiesModels(PersonaliseModel cities) {
        this.citiesModels.add(cities);
    }

    public ArrayList<PersonaliseModel> getCitiesModels() {
        return citiesModels;
    }

    public void addAuthorsModels(PersonaliseModel authors) {
        this.authorsModels.add(authors);
    }

    public ArrayList<PersonaliseModel> getAuthorsModels() {
        return authorsModels;
    }

    public void setAuthorsModels(ArrayList<PersonaliseModel> authorsModels) {
        this.authorsModels = authorsModels;
    }
}
