package paquete;

public class Character {
	private String id;
	private String name;
	private String wikiUrl;
	private String race;
	private String birth;
	private String gender;
	private String death;
	private String hair;
	private String height;
	private Realm realm;
	private Character spouse;
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
	public String getWikiUrl() {
		return wikiUrl;
	}
	public void setWikiUrl(String wikiUrl) {
		this.wikiUrl = wikiUrl;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDeath() {
		return death;
	}
	public void setDeath(String death) {
		this.death = death;
	}
	public String getHair() {
		return hair;
	}
	public void setHair(String hair) {
		this.hair = hair;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public Realm getRealm() {
		return realm;
	}
	public void setRealm(Realm realm) {
		this.realm = realm;
	}
	public Character getSpouse() {
		return spouse;
	}
	public void setSpouse(Character spouse) {
		this.spouse = spouse;
	}
	public Character(String id, String name, String wikiUrl, String race, String birth, String gender, String death,
			String hair, String height, Realm realm, Character spouse) {
		super();
		this.id = id;
		this.name = name;
		this.wikiUrl = wikiUrl;
		this.race = race;
		this.birth = birth;
		this.gender = gender;
		this.death = death;
		this.hair = hair;
		this.height = height;
		this.realm = realm;
		this.spouse = spouse;
	}	
	
}
