import java.util.*;

class Player extends Partitioner
{
	ArrayList<Card> Cards = new ArrayList<Card>();
	ArrayList<Integer> Ace = new ArrayList<Integer>();
	int money, bet, count;

	public Player(String pName, int money){
		super(pName);
		this.money = money;
	}
	public void handle(Card newOne){
		Cards.add(newOne);
	}
	
	public void points(){
		count = 0;
		for(int i = 0; i < Cards.size(); i++){
			if(Cards.get(i).face == "J" || Cards.get(i).face == "Q" || Cards.get(i).face == "K"){
				count += 10;
			}
			else if(Cards.get(i).face == "A"){
				if(count + 11 > 21){
					count += 1;
					Ace.add(1);
				}
				else{
					count += 11;
					Ace.add(11);
				}
			}
			else{
				count += Integer.parseInt(Cards.get(i).face);
			}
		}
		if(count > 21){
			for(int j = 0; j < Ace.size(); j++){
				if(Ace.get(j) == 11){
					count -= 10;
				}
				if(count <= 21){
					break;
				}
			}
		}
		Ace.clear();
	}
}