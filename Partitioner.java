import java.util.*;

class Partitioner
{
	String name;
	boolean isDealer;
	ArrayList<Card> cards;
	
	public Partitioner(String pName) {
		this.name = pName;
		this.isDealer = false;
		cards = new ArrayList<Card>();
	}
	public void setName(String newName) {
		this.name = newName;
	}
}