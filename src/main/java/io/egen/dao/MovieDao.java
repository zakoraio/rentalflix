package io.egen.dao;

import java.util.List;

import io.egen.rentalflix.Movie;

public interface MovieDao {

	Movie findById(int id);

	void createMovie(Movie movie);
	
	Movie updateMovie(Movie movie);
	
	Movie deleteMovie(int id);
	
	List<Movie> findAllMovies();

	List<Movie> findMovieByName(String name);

}
