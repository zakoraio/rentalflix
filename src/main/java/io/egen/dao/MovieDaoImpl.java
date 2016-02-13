package io.egen.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import io.egen.rentalflix.Movie;

@Repository("movieDao")
public class MovieDaoImpl extends AbstractDao<Integer, Movie> implements MovieDao {

	public Movie findById(int id) {
		return getByKey(id);
	}

	@Override
	public Movie updateMovie(Movie entity) {
		Movie movie = getByKey(entity.getId());
		if (null != movie) {
			movie.setLanguage(entity.getLanguage());
			movie.setTitle(entity.getTitle());
			movie.setYear(entity.getYear());
			update(movie);
			return movie;
		}

		return null;
	}

	public void createMovie(Movie movie) {
		persist(movie);
	}

	public Movie deleteMovie(int id) {
		Movie movie = getByKey(id);
		if(null!=movie){
			delete(movie);
			return movie;
		}
		return null;
		
	}

	@SuppressWarnings("unchecked")
	public List<Movie> findAllMovies() {
		Criteria criteria = createEntityCriteria();
		return (List<Movie>) criteria.list();
	}

	public List<Movie> findMovieByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.like("title", name, MatchMode.ANYWHERE).ignoreCase());
		List<Movie> movieList = (List<Movie>) criteria.list();
		return movieList;
	}
}
