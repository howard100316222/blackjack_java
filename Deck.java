import java.util.*;
import java.util.Random;

class Deck
{
	ArrayList<Card> Cards;
	int n;

	public Deck(int n) {
		this.n = n;
		Cards = new ArrayList<Card>();
		String[] face = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		String[] suit = {"¶Â®ç", "¬õ¤ß", "¤è¶ô", "±öªá"};
		for(int i = 0; i < n; i++){
			for(int j = 0; j < 4; j++){
				for(int k = 0; k < 13; k++){
					Card temp = new Card(face[k], suit[j]);
					Cards.add(temp);
				}
			}
		}
	}
	public void shuffle(){
		Card temp;
		Random ran = new Random();
		int t1, t2;
		for(int i = 0; i < Cards.size(); i++){
			t1 = ran.nextInt(Cards.size());
			t2 = ran.nextInt(Cards.size());
			temp = Cards.get(t1);
			Cards.set(t1,Cards.get(t2));
			Cards.set(t2,temp);
		}
	}
	public Card deal(){
		Card temp = Cards.get(Cards.size() - 1);
		Cards.remove(Cards.size() - 1);
		return temp;
	}
	public void reset(){
		String[] face = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		String[] suit = {"¶Â®ç", "¬õ¤ß", "¤è¶ô", "±öªá"};
		for(int i = 0; i < n; i++){
			for(int j = 0; j < 4; j++){
				for(int k = 0; k < 13; k++){
					Card temp = new Card(face[k], suit[j]);
					Cards.add(temp);
				}
			}
		}
		shuffle();
		System.out.println("­«·s¸É¥RµP + ¬~µP!");
	}
}