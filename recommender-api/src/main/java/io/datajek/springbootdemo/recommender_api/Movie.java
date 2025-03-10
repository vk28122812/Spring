package io.datajek.springbootdemo.recommender_api;

public class Movie {
    int id;
    double rating;
    String name;


    public Movie() {
    }

    public Movie(int id, double rating, String name) {
        super();
        this.id = id;
        this.rating = rating;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "Movie [id=" + id + ", rating=" + rating + ", name=" + name + "]";
    }
}
