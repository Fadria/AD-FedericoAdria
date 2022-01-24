package paquete;

import java.util.Set;

public class Movie {
	private String id;
	private String name;
	private int runtimeInMinutes;
	private int budgetInMillions;
	private int boxOfficeRevenueInMillions;
	private int academyAwardNominations;
	private int academyAwardWins;
	private int rottenTomatoesScore;
	private Set<Dialog> dialogs;
	private Set<Book> books;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRuntimeInMinutes() {
		return runtimeInMinutes;
	}
	public void setRuntimeInMinutes(int runtimeInMinutes) {
		this.runtimeInMinutes = runtimeInMinutes;
	}
	public int getBudgetInMillions() {
		return budgetInMillions;
	}
	public void setBudgetInMillions(int budgetInMillions) {
		this.budgetInMillions = budgetInMillions;
	}
	public int getBoxOfficeRevenueInMillions() {
		return boxOfficeRevenueInMillions;
	}
	public void setBoxOfficeRevenueInMillions(int boxOfficeRevenueInMillions) {
		this.boxOfficeRevenueInMillions = boxOfficeRevenueInMillions;
	}
	public int getAcademyAwardNominations() {
		return academyAwardNominations;
	}
	public void setAcademyAwardNominations(int academyAwardNominations) {
		this.academyAwardNominations = academyAwardNominations;
	}
	public int getAcademyAwardWins() {
		return academyAwardWins;
	}
	public void setAcademyAwardWins(int academyAwardWins) {
		this.academyAwardWins = academyAwardWins;
	}
	public int getRottenTomatoesScore() {
		return rottenTomatoesScore;
	}
	public void setRottenTomatoesScore(int rottenTomatoesScore) {
		this.rottenTomatoesScore = rottenTomatoesScore;
	}
	public Set<Dialog> getDialogs() {
		return dialogs;
	}
	public void setDialogs(Set<Dialog> dialogs) {
		this.dialogs = dialogs;
	}
	public Set<Book> getBooks() {
		return books;
	}
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	public Movie(String id, String name, int runtimeInMinutes, int budgetInMillions, int boxOfficeRevenueInMillions,
			int academyAwardNominations, int academyAwardWins, int rottenTomatoesScore, Set<Dialog> dialogs,
			Set<Book> books) {
		super();
		this.id = id;
		this.name = name;
		this.runtimeInMinutes = runtimeInMinutes;
		this.budgetInMillions = budgetInMillions;
		this.boxOfficeRevenueInMillions = boxOfficeRevenueInMillions;
		this.academyAwardNominations = academyAwardNominations;
		this.academyAwardWins = academyAwardWins;
		this.rottenTomatoesScore = rottenTomatoesScore;
		this.dialogs = dialogs;
		this.books = books;
	}
	
}
