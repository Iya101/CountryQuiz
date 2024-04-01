package edu.uga.cs.countryquiz;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private long id;
    private String countryName;
    private String correctContinent;
    private List<String> options;


    public Question() {
        this.id = -1;
        this.countryName = null;
        this.correctContinent = null;
        this.options = new ArrayList<>(2);
    }

    public Question(String countryName, String correctContinent, List<String> options) {
        this.id = -1;
        this.countryName = countryName;
        this.correctContinent = correctContinent;
        this.options = new ArrayList<>(options);
    }

    public String getQuestionText() {
        return "Which continent does " + countryName + " belong to?";
    }




    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }
    public String getCorrectContinent() {
        return correctContinent;
    }

    public void getCorrectContinent(String correctContinent)
    {
        this.correctContinent = correctContinent;
    }

    public List<String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return countryName + ": " + options.toString();
    }
}