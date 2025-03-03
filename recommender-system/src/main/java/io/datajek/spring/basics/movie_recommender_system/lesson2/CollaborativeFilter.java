package io.datajek.spring.basics.movie_recommender_system.lesson2;

public class CollaborativeFilter implements  Filter{
    public String[] getRecommendations(String movie) {
        //logic of collaborative filter
        return new String[] {"My name is Khan", "Pathaan", "Jawan"};
    }
}
