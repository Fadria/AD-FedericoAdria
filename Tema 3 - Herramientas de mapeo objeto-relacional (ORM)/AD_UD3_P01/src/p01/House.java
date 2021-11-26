package p01;
// Generated 26 nov. 2021 16:10:20 by Hibernate Tools 5.5.7.Final

import java.util.HashSet;
import java.util.Set;

/**
 * House generated by hbm2java
 */
public class House implements java.io.Serializable {

	private int id;
	private Person person;
	private String name;
	private Set persons = new HashSet(0);

	public House() {
	}

	public House(int id, Person person, String name) {
		this.id = id;
		this.person = person;
		this.name = name;
	}

	public House(int id, Person person, String name, Set persons) {
		this.id = id;
		this.person = person;
		this.name = name;
		this.persons = persons;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getPersons() {
		return this.persons;
	}

	public void setPersons(Set persons) {
		this.persons = persons;
	}

}
