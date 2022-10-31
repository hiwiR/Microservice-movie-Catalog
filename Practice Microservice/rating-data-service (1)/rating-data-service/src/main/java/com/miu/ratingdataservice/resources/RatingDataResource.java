package com.miu.ratingdataservice.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingDataResource {
    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId){
        return new Rating("mov001",4);
    }
    @GetMapping("user/{userId}")
    public UserRating getListOfRatings(@PathVariable String userId){
     return new UserRating( Arrays.asList(
              new Rating("1", 4),
              new Rating("2", 5)
      ));
    }
}
