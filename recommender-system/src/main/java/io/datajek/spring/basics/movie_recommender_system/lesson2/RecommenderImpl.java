package io.datajek.spring.basics.movie_recommender_system.lesson2;


public class RecommenderImpl {

    private Filter filter;

    public RecommenderImpl(Filter filter) {
        super();
        this.filter = filter;
    }

    public String[] recommendMovies(String movie){
        System.out.println("Filter in use: "+ filter);

        String[]results = filter.getRecommendations(movie);
        return results;
    }

}
