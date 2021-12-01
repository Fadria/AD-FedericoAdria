package paquete;
// Generated 29 nov. 2021 16:39:36 by Hibernate Tools 5.4.32.Final

/**
 * Deaths generated by hbm2java
 */
public class Deaths implements java.io.Serializable {

	private Integer id;
	private Characters charactersByIdKiller;
	private Characters charactersByIdCharacter;
	private Films films;

	public Deaths() {
	}

	public Deaths(Characters charactersByIdKiller, Characters charactersByIdCharacter, Films films) {
		this.charactersByIdKiller = charactersByIdKiller;
		this.charactersByIdCharacter = charactersByIdCharacter;
		this.films = films;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Characters getCharactersByIdKiller() {
		return this.charactersByIdKiller;
	}

	public void setCharactersByIdKiller(Characters charactersByIdKiller) {
		this.charactersByIdKiller = charactersByIdKiller;
	}

	public Characters getCharactersByIdCharacter() {
		return this.charactersByIdCharacter;
	}

	public void setCharactersByIdCharacter(Characters charactersByIdCharacter) {
		this.charactersByIdCharacter = charactersByIdCharacter;
	}

	public Films getFilms() {
		return this.films;
	}

	public void setFilms(Films films) {
		this.films = films;
	}

}