package paquete;

import java.util.Set;

public class Book {
	private int id;
	private String title;
	private Set<Movie> movies;
	private Set<Chapter> chapters;
	public Book(int id, String title, Set<Movie> movies, Set<Chapter> chapters) {
		super();
		this.id = id;
		this.title = title;
		this.movies = movies;
		this.chapters = chapters;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Set<Movie> getMovies() {
		return movies;
	}
	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	public Set<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}
	
	
}
