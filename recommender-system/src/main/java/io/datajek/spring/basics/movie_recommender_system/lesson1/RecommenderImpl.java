package io.datajek.spring.basics.movie_recommender_system.lesson1;

public class RecommenderImpl {

    public String[] recommendMovies(String movie){
        ContentBasedFilter filter = new ContentBasedFilter();
        String[]results = filter.getRecommendations(movie);
        return results;
    }

}
