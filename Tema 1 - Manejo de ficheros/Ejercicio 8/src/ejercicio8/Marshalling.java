package ejercicio8;

public class Marshalling 
{
	public static void main(String[] args)
	{
		String[] titlesHouse1 = {"King in the North", "Lord of Winterfell", "Warden of the North", "King of the Trident"};
		House house1 = new House(362, "House Stark of Winterfell", "The North", "A running grey direwolf, on an ice-white field",
				"Winter is Coming", titlesHouse1, "Scattered (formerly Winterfell)");
		
		String[] titlesHouse2 = {"King of the Rock (formerly) Lord of Casterly Rock", "Shield of Lannisport", "Warden of the West"};
		House house2 = new House(229, "House Lannister of Casterly Rock", "The Westerlands", "A gold lion, on a crimson field(Gules, a lion or)",
				"Hear Me Roar!", titlesHouse2, "Casterly Rock");
		
		
		
	}
}
