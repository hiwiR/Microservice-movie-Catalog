package com.miu.moviedetailservice.resources;

import com.miu.moviedetailservice.models.Album;
import com.miu.moviedetailservice.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieDetailServiceResource {

    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/{movieId}")
    public Movie getMovieDetail(@PathVariable String movieId){
        return new Movie("Mov001","Avengers");
    }

    @GetMapping("/albums/{albumId}")
    public Album getAlbumDetail(@PathVariable String albumId){
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/albums/" + albumId, Album.class);
    }

}
