package ejercicio8;

import java.util.Arrays;

//@XmlRootElement
public class House 
{
	private int id;
	private String name;
	private String region;
	private String coatOfArms;
	private String words;
	private String[] titles;
	private String seats;
	
	public House(int id, String name, String region, String coatOfArms, String words, String[] titles, 
			String seats) 
	{
		this.id = id;
		this.name = name;
		this.region = region;
		this.coatOfArms = coatOfArms;
		this.words = words;
		this.titles = titles;
		this.seats = seats;
	}

	//@XmlElement(name="id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//@XmlElement(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//@XmlElement(name="region")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	//@XmlElement(name="coatOfArms")
	public String getCoatOfArms() {
		return coatOfArms;
	}

	public void setCoatOfArms(String coatOfArms) {
		this.coatOfArms = coatOfArms;
	}

	//@XmlElement(name="words")
	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	//@XmlElementWrapper @XmlElement(name="titles")
	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	//@XmlTransient
	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	@Override
	public String toString() {
		return "House [id=" + id + ", name=" + name + ", region=" + region + ", coatOfArms=" + coatOfArms + ", words="
				+ words + ", titles=" + Arrays.toString(titles) + ", seats=" + seats + "]";
	}	
	
}
