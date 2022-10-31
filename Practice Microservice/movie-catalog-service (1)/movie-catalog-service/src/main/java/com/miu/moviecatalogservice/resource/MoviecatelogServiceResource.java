package com.miu.moviecatalogservice.resource;

import com.miu.moviecatalogservice.model.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/catalog")
public class MoviecatelogServiceResource {
    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public WebClient.Builder webClient;
    @GetMapping("/{userId}")
    @CircuitBreaker(
            name = "MovieCatalog",
            fallbackMethod = "getAllAlbums"
    )
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        UserRating userRating = webClient.build()
                .get()
                .uri("http://RATING-DATA-SERVICE/ratings/user/foo")
                .retrieve()
                .bodyToMono(UserRating.class)
                .block();

       return userRating.getRatings().stream().map(rating -> {
//                    Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
          Album album =  webClient.build()
                   .get()
                   .uri("http://MOVIE-DETAIL-SERVICE/movies/albums/" + rating.getMovieId())
                   .retrieve()
                   .bodyToMono(Album.class)
                   .block();
                    return new CatalogItem(album.getTitle(), "", rating.getRating());
                }
        ).collect(Collectors.toList());
    }
    public List<CatalogItem> getAllAlbums(Exception e){

        return Stream.of(
                new CatalogItem( "test1","test",1),
                new CatalogItem( "test2","test2",2)
        ).collect(Collectors.toList());
    }
}
