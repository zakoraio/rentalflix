package io.egen.rentalflix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.egen.dao.MovieDao;
import io.egen.dao.RentalDao;

/**
 * Service implementing IFlix interface You can use any Java collection type to
 * store movies
 */

@Service("movieService")
public class MovieService implements IFlix {

	@Autowired
	MovieDao movieDao;

	@Autowired
	RentalDao rentalDao;

	@Override
	public List<Movie> findAll() {

		return movieDao.findAllMovies();
	}

	@Override
	public List<Movie> findByName(String name) {

		List<Movie> movies = movieDao.findMovieByName(name);

		if (movies.size() == 0) {
			return null;
		}

		return movies;
	}

	@Override
	public Movie create(Movie movie) {

		movieDao.createMovie(movie);
		return movie;

	}

	@Override
	public Movie update(Movie movie) throws IllegalArgumentException {

		Movie updatedMovie = movieDao.updateMovie(movie);
		if (null != updatedMovie) {
			return movie;
		}

		throw new IllegalArgumentException();
	}

	@Override
	public Movie delete(int id) {

		Movie movie = movieDao.deleteMovie(id);
		if (null != movie) {
			return movie;
		}

		throw new IllegalArgumentException("The movie to be updated was not present in the database");
	}

	@Override
	public boolean rentMovie(int movieId, String user) {

		Movie movie = movieDao.findById(movieId);

		if (null == movie) {
			throw new IllegalArgumentException();
		}

		RentalRecord rRecord = rentalDao.findRentalRecordByMovieID(movieId);

		if (rRecord == null) {
			RentalRecord rentalRecord = new RentalRecord();
			rentalRecord.setMovieId(movieId);
			rentalRecord.setUsername(user);
			rentalDao.createRentalRecord(rentalRecord);
			return true;
		}
		
		return false;
	}

}
