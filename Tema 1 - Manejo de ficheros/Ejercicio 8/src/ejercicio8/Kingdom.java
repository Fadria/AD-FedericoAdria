package ejercicio8;

import java.util.ArrayList;

//@XmlRootElement
public class Kingdom 
{
	private ArrayList<House> houses;
	private String name;
	private String king;
	private String location;
	
	public Kingdom(ArrayList<House> houses, String name, String king, String location) 
	{
		this.houses = houses;
		this.name = name;
		this.king = king;
		this.location = location;
	}

	//@XmlElement(name="houses")
	public ArrayList<House> getHouses() {
		return houses;
	}

	public void setHouses(ArrayList<House> houses) {
		this.houses = houses;
	}

	//@XmlElement(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//@XmlElement(name="king")
	public String getKing() {
		return king;
	}

	public void setKing(String king) {
		this.king = king;
	}

	//@XmlElement(name="location")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Kingdom [houses=" + houses + ", name=" + name + ", king=" + king + ", location=" + location + "]";
	}	
	
}
