package paquete;

import java.util.Set;

public class Realm {
	private int id;
	private String name;
	private int population;
	private int area;
	private Set<Character> characters;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public Set<Character> getCharacters() {
		return characters;
	}
	public void setCharacters(Set<Character> characters) {
		this.characters = characters;
	}
	public Realm(int id, String name, int population, int area, Set<Character> characters) {
		super();
		this.id = id;
		this.name = name;
		this.population = population;
		this.area = area;
		this.characters = characters;
	}
	
}
