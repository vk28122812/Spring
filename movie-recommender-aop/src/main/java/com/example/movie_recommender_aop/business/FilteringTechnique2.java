package com.example.movie_recommender_aop.business;

import com.example.movie_recommender_aop.data.Movie;
import com.example.movie_recommender_aop.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilteringTechnique2 {

    @Autowired
    private User user;

    public String collaborativeFiltering(){
        String userDetails = user.getUserDetails();
        return userDetails;
    }

}
