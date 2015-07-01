import java.util.*;
import java.io.*;
import java.util.Scanner;

class Game
{
	ArrayList<Player> players;
	Scanner s = new Scanner(System.in);
	Dealer dealer;
	String m; 
	String deckCount; 
	String bet;
	String opt; 
	String opt2;
	boolean isError = false, isFinish = false, isOver = false, isFirst = true;
	int exit = 0;
	Deck deck;
	
	public Game(int playerCount) {
		players = new ArrayList<Player>();
		try{
			System.out.println("�п�J�Ҧ����a���w�X : ");
			m = s.next();
			if(Integer.parseInt(m) < 1){
				System.out.println("�w�X���j��0!");
				isError = true;
			}
			else{
				for (int i = 0; i < playerCount; i++) {
					System.out.println("�п�J���a" + (i + 1) + "�W�� : ");
					players.add(new Player(s.next(), Integer.parseInt(m)));
				}
			}
			if(!isError){
				System.out.println("�X�ƵP : ");
				deckCount = s.next();
				if(Integer.parseInt(deckCount) >= 1){
					deck = new Deck(Integer.parseInt(deckCount));
				}
				else{
					System.out.println("�P���j�󵥩�1��!");
					isError = true;
				}
			}
		}
		catch(Exception e){
			System.out.println("error : " + e);
			isError = true;
		}
		dealer = new Dealer("���a");
	}
	public void firstround(){
		isFinish = false;
		try{
			while(!isFirst){
				exit = 0;
				System.out.println("�п�J 1)�~��C�� -1)���} : ");
				option2 = s.next();
				switch(option2){
					case "1":
						break;
					case "-1":
						exit = 1;
						return;
					default:
						exit = -1;
						System.out.println("�L���ﶵ!");
						break;
				}
				if(exit == 0){
					break;
				}
			}
			for(int i = 0; i < players.size(); i++){
				System.out.println("���a" + players.get(i).name + "�ФU�` : ");
				bet = s.next();
				if(Integer.parseInt(bet) < 1){
					System.out.println("�U�`���B���j��0!");
					i--;
				}
				else if(Integer.parseInt(bet) > players.get(i).money){
					System.out.println("�w�X����!");
					i--;
				}
				else{
					players.get(i).bet = Integer.parseInt(bet);
				}
			}
		}
		catch(Exception e){
			System.out.println("error : " + e);
			isError = true;
		}
		if(!isError){
			if(isFirst){
				deck.shuffle();	
				System.out.println("�~�P!");
				isFirst = false;
			}
			for(int i = 0; i < 2; i++){
				for(int j = 0; j < players.size(); j++){
					players.get(j).handle(deck.deal());
					if(deck.cards.size() == 0){
						deck.reset();
						for(int k = 0; k < players.size(); k++){
							for(int t = 0; t < players.get(k).cards.size(); t++){
								for(int x = 0; x < deck.cards.size(); x++){
									if(deck.cards.get(x).suit == players.get(k).cards.get(t).suit && deck.cards.get(x).face == players.get(k).cards.get(t).face){
										deck.cards.remove(x);
									}
								}
							}
						}
						for(int k = 0; k < dealer.cards.size(); k++){
							deck.cards.remove(dealer.cards.get(k));
						}
					}
				}
				dealer.handle(deck.deal());
				if(deck.cards.size() == 0){
					deck.reset();
					for(int k = 0; k < players.size(); k++){
						for(int t = 0; t < players.get(k).cards.size(); t++){
							for(int x = 0; x < deck.cards.size(); x++){
								if(deck.cards.get(x).suit == players.get(k).cards.get(t).suit && deck.cards.get(x).face == players.get(k).cards.get(t).face){
									deck.cards.remove(x);
								}
							}
						}
					}
					for(int k = 0; k < dealer.cards.size(); k++){
						deck.cards.remove(dealer.cards.get(k));
					}
				}
			}
			System.out.println("�o�P!\n");
			System.out.print("\t���a :\t");
			for(int j = 0; j < dealer.cards.size(); j++){
				if(j == 0){
					System.out.print("***\t");
				}
				else{
					System.out.println(dealer.cards.get(j).suit + dealer.cards.get(j).face);
				}
			}
			System.out.println();
			for(int i = 0; i < players.size(); i++){
				System.out.print(players.get(i).name + " :\t");
				for(int j = 0; j < players.get(i).cards.size(); j++){
					System.out.print(players.get(i).cards.get(j).suit + players.get(i).cards.get(j).face + "\t");
				}
				System.out.println("�w�X : " + players.get(i).money);
			}
		}
		dealer.points();
		if(dealer.count == 21){
			System.out.println("���a�G�P!");
			for(int j = 0; j < dealer.cards.size(); j++){
				System.out.print(dealer.cards.get(j).suit + dealer.cards.get(j).face + "\t");
			}
			System.out.println("");
			System.out.println("���ablackjack!");
			for(int i = 0; i < players.size(); i++){
				players.get(i).points();
				System.out.println("���a" + players.get(i).name + "�`�I�� : " + players.get(i).count);
				if(players.get(i).count == 21){
					System.out.println("���a" + players.get(i).name + " blackjack!");
					System.out.println("���a" + players.get(i).name + "�w�X���� " + "�Ѿl" + players.get(i).money + "�I");
				}
				else{
					players.get(i).money -= players.get(i).bet;
					System.out.println("���a" + players.get(i).name + "�w�X-" + players.get(i).bet + "�Ѿl" + players.get(i).money + "�I");
					if(players.get(i).money == 0){
						System.out.println("���a" + players.get(i).name + "��F!");
						players.remove(i);
					}
				}
			}
			isFinish = true;
			if(players.size() == 0){
				isOver = true;
			}
			dealer.cards.clear();
			for(int i = 0; i < players.size(); i++){
				players.get(i).cards.clear();
			}
		}
	}
	public void battle(){
		if(!isOver && !isFinish){
			boolean exit = false;
			System.out.println("���a�^�X");
			System.out.println("===============");
			try{
				for(int a = 0; a < players.size(); a++){
					System.out.println("���a" + players.get(a).name + "�п�� 1)hit 2)stay : ");
					option = s.next();
					switch(opt){
						case "1":
							Card temp = deck.deal();
							System.out.println(temp.suit + temp.face);
							players.get(a).handle(temp);
							if(deck.cards.size() == 0){
								deck.reset();
								for(int i = 0; i < players.size(); i++){
									for(int j = 0; j < players.get(j).cards.size(); j++){
										for(int x = 0; x < deck.cards.size(); x++){
											if(deck.cards.get(x).suit == players.get(i).cards.get(j).suit && deck.cards.get(x).face == players.get(i).cards.get(j).face){
												deck.cards.remove(x);
											}
										}
									}
								}
								for(int i = 0; i < dealer.cards.size(); i++){
									deck.cards.remove(dealer.cards.get(i));
								}
							}
							break;
						case "2":
							exit = true;
							break;
						default:
							System.out.println("�L���ﶵ!");
							break;
					}
					players.get(a).points();
					if(!exit && players.get(a).count <= 21){
						a--;
					}
					else if(players.get(a).count > 21){
						System.out.println("���a" + players.get(a).name + "�z�P!");
					}
					else if(exit){
						exit = false;
					}
				}
			}
			catch(Exception e){
				System.out.println("error : " + e);
				isError = true;
			}
			System.out.println("���a�^�X");
			System.out.println("===============");
			dealer.points();
			System.out.println("���a�G�P!");
			for(int j = 0; j < dealer.cards.size(); j++){
				System.out.print(dealer.cards.get(j).suit + dealer.cards.get(j).face + "\t");
			}
			System.out.println("");
			while(dealer.count < 17){
				System.out.println("���a�I�Ƥp��17�A�~��n�P!");
				Card temp = deck.deal();
				System.out.println(temp.suit + temp.face);
				dealer.handle(temp);
				if(deck.cards.size() == 0){
					deck.reset();
					for(int k = 0; k < players.size(); k++){
						for(int t = 0; t < players.get(k).cards.size(); t++){
							for(int x = 0; x < deck.cards.size(); x++){
								if(deck.cards.get(x).suit == players.get(k).cards.get(t).suit && deck.cards.get(x).face == players.get(k).cards.get(t).face){
									deck.cards.remove(x);
								}
							}
						}
					}
					for(int k = 0; k < dealer.cards.size(); k++){
						deck.cards.remove(dealer.cards.get(k));
					}
				}
				dealer.points();
				if(dealer.count > 21){
					System.out.println("���a�z�P!");
				}
			}
			System.out.println("���a���a��M");
			System.out.println("===============");
			System.out.println("���a�`�I�� : " + dealer.count);
			if(dealer.count > 21){
				System.out.println("���a�z�P!");
				for(int i = 0; i < players.size(); i++){
					System.out.println("���a" + players.get(i).name + "�`�I�� : " + players.get(i).count);
					if(players.get(i).count <= 21){
						if(players.get(i).count == 21){
							System.out.println("���a" + players.get(i).name + " blackjack!");
						}
						players.get(i).money += players.get(i).bet;
						System.out.println("���a" + players.get(i).name + "�w�X+" + players.get(i).bet + "�Ѿl" + players.get(i).money + "�I");
					}
					else{
						System.out.println("���a" + players.get(i).name + "�z�P!");
						players.get(i).money -= players.get(i).bet;
						System.out.println("���a" + players.get(i).name + "�w�X-" + players.get(i).bet + "�Ѿl" + players.get(i).money + "�I");
						if(players.get(i).money == 0){
							System.out.println("���a" + players.get(i).name + "��F!");
							players.remove(i);
							i--;
						}
					}
				}
			}
			else{
				if(dealer.count == 21){
					System.out.println("���ablackjack!");
				}
				for(int i = 0; i < players.size(); i++){
					System.out.println("���a" + players.get(i).name + "�`�I�� : " + players.get(i).count);
					if(players.get(i).count <= 21 && players.get(i).count > dealer.count){
						if(players.get(i).count == 21){
							System.out.println("���a" + players.get(i).name + " blackjack!");
						}
						players.get(i).money += players.get(i).bet;
						System.out.println("���a" + players.get(i).name + "�w�X+" + players.get(i).bet + "�Ѿl" + players.get(i).money + "�I");
					}
					else if(players.get(i).count > 21 || players.get(i).count < dealer.count){
						if(players.get(i).count > 21){
							System.out.println("���a" + players.get(i).name + "�z�P!");
						}
						players.get(i).money -= players.get(i).bet;
						System.out.println("���a" + players.get(i).name + "�w�X-" + players.get(i).bet + "�Ѿl" + players.get(i).money + "�I");
						if(players.get(i).money == 0){
							System.out.println("���a" + players.get(i).name + "��F!");
							players.remove(i);
							i--;
						}
					}
					else{
						if(players.get(i).count == 21){
							System.out.println("���a" + players.get(i).name + " blackjack!");
						}
						System.out.println("���a" + players.get(i).name + "�w�X���� " + "�Ѿl" + players.get(i).money + "�I");
					}
				}
			}
			dealer.cards.clear();
			for(int i = 0; i < players.size(); i++){
				players.get(i).cards.clear();
			}
			if(players.size() == 0){
				isOver = true;
			}
		}
	}
}