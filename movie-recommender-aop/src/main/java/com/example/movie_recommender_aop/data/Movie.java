package com.example.movie_recommender_aop.data;

import org.springframework.stereotype.Repository;

@Repository
public class Movie {

    public String getMovieDetails(){
        return "Movie details";
    }
}
