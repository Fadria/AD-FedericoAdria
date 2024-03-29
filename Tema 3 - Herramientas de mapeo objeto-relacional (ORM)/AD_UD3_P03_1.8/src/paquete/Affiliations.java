package paquete;
// Generated 29 nov. 2021 16:39:36 by Hibernate Tools 5.4.32.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Affiliations generated by hbm2java
 */
public class Affiliations implements java.io.Serializable {

	private int id;
	private String affiliation;
	private Set characterses = new HashSet(0);

	public Affiliations() {
	}

	public Affiliations(int id) {
		this.id = id;
	}

	public Affiliations(int id, String affiliation, Set characterses) {
		this.id = id;
		this.affiliation = affiliation;
		this.characterses = characterses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAffiliation() {
		return this.affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public Set getCharacterses() {
		return this.characterses;
	}

	public void setCharacterses(Set characterses) {
		this.characterses = characterses;
	}

}
