package io.egen.rentalflix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import io.egen.config.HibernateConfiguration;

/**
 * JUnit test cases for MovieService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class,classes={ 
		HibernateConfiguration.class})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional

public class MovieServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	MovieService movieService;

	List<Movie> movies;
	
	@Before
	public void setUpData(){
		movies = new ArrayList<Movie>();
		loadMovies(movies);
		
		for(Movie movie:movies){
			movieService.create(movie);
		}
	}
	
	
	@After
	public void tearDownData(){
		movieService = null;
		movies = null;
	}
	@Test
	public void testFindAllReturningAllMovies() {

		assertEquals(movieService.findAll().size(),movies.size());

	}
	
	
	@Test
	public void testFindAllMovieEquals(){
		List<Movie> foundMovies = movieService.findAll();
		for(Movie movie:movies){
			assertEquals(true,foundMovies.contains(movie));
		}
		
	}
	
	
	@Test
	public void testFindByNameForMovieNotPresent() {

		assertNull(movieService.findByName("Inception"));

	}
	
	@Test
	public void testFindByNameForMoviePresent() {

		
		List<Movie> tempList = new ArrayList<Movie>();
		
		Movie movie = new Movie();
		movie.setId(1);
		movie.setLanguage("English");
		movie.setTitle("Crouching Tiger and Hidden Dragon");
		movie.setYear("2000");
		
		tempList.add(movie);
		assertEquals(tempList,movieService.findByName("Crouching Tiger and Hidden Dragon"));

	}
	
	@Test
	public void testFindByNameForMoviePresentCaseInsensitive() {

		List<Movie> tempList = new ArrayList<Movie>();
		
		Movie movie = new Movie();
		movie.setId(1);
		movie.setLanguage("English");
		movie.setTitle("Crouching Tiger and Hidden Dragon");
		movie.setYear("2000");
		
		tempList.add(movie);
		assertEquals(tempList,movieService.findByName("CROUCHING TIGER AND HIDDEN DRAGON"));
	}
	
	@Test
	public void testUpdateorMoviePresent() {
		
		List<Movie> tempList = new ArrayList<Movie>();
		Movie movie = new Movie();
		movie.setId(1);
		movie.setLanguage("Mandarin");
		movie.setTitle("Crouching Tiger and Hidden Dragon");
		movie.setYear("2000");
		
		tempList.add(movie);
		
		assertEquals("English",movieService.findByName("CROUCHING TIGER AND HIDDEN DRAGON").get(0).getLanguage());
		
		assertEquals(movie,movieService.update(movie));
		
		assertEquals("Mandarin",movieService.findByName("CROUCHING TIGER AND HIDDEN DRAGON").get(0).getLanguage());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateForMovieNotPresent() {
		
		Movie movie = new Movie();
		movie.setId(7);
		movie.setLanguage("English");
		movie.setTitle("Crouching Tiger and Hidden Dragon");
		movie.setYear("2000");
		
		movieService.update(movie);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteForMovieNotPresent() {
		
		movieService.delete(7);
		
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteForMoviePresent() {
		assertEquals("Crouching Tiger and Hidden Dragon",movieService.findByName("CROUCHING TIGER AND HIDDEN DRAGON").get(0).getTitle());
		
		Movie movie = new Movie();
		movie.setId(1);
		movie.setLanguage("English");
		movie.setTitle("Crouching Tiger and Hidden Dragon");
		movie.setYear("2000");
		
		assertEquals(movie,movieService.delete(1));
		
		movieService.delete(1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRentMovieNotPresent() {
		
		movieService.rentMovie(7, "Saurabh Rai");
	}
	
	
	@Test
	public void testRentMoviePresentAndNotAlreadyRented() {
		
		assertEquals(true,movieService.rentMovie(1, "Saurabh Rai"));
	}
	
	@Test
	public void testRentMoviePresentAndAlreadyRented() {
		
		movieService.rentMovie(1, "Saurabh Rai");
		
		assertEquals(false,movieService.rentMovie(1, "Saurabh Rai"));
	}
	

	private void loadMovies(List<Movie> movies) {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = this.getClass().getResourceAsStream("/movies.properties");

			prop.load(input);

			Movie movie = null;
			for (int i = 0; i < Integer.parseInt(prop.getProperty("numItems")); i++) {
				movie = new Movie();
				movie.setId(Integer.parseInt(prop.getProperty("movieId" + i)));
				movie.setLanguage((prop.getProperty("movieLang" + i)));
				movie.setTitle((prop.getProperty("movieTitle" + i)));
				movie.setYear(prop.getProperty("movieYear" + i));
				movies.add(movie);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
