package com.example.movie_recommender_aop.data;

import org.springframework.stereotype.Repository;

@Repository
public class User {
    public String getUserDetails() {
        return "User details.";
    }
}
