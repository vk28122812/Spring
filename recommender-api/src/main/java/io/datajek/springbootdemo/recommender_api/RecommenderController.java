package io.datajek.springbootdemo.recommender_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;


@RestController
public class RecommenderController {

    @GetMapping("/movies")
    public List<Movie> getAllMovies(){
        return Arrays.asList(new Movie(1, 4.5, "Pathaan"),
                new Movie(2,4.2, "Jawan"),
                new Movie(3, 4, "Jab tak hai jaan"));
    }

}
